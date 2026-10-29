package lippo.hris.system.timemanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.service.TemplateAuthService;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.repository.DayPaymentRequestRepository;
import lippo.hris.system.timemanagement.request.TMDPRightsEmployeeReq;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import lippo.hris.system.timemanagement.response.TMDayPayment;
import lippo.hris.system.workflowengine.service.WorkflowTransService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class DayPaymentService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    DayPaymentRequestRepository dayPaymentRequestRepository;

    @Autowired
    TemplateAuthService templateAuthService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WorkflowTransService workflowTransService;

    public void addDayPayment(TMDPRightsReq tmDPRightsReq, String username){
        tmDPRightsReq.setNikEmpCreate(username);
        for(TMDPRightsEmployeeReq employeeReq : tmDPRightsReq.getEmployee()){
            DayPaymentRequest dayPaymentRequest = new DayPaymentRequest();
            dayPaymentRequest.setEmployee(employeeReq.getEmployeeNIK());
            dayPaymentRequest.setEmployeeName(employeeReq.getEmployeeName());
            dayPaymentRequest.setCount(tmDPRightsReq.getDpCount());
            dayPaymentRequest.setDate(tmDPRightsReq.getDpDate());
            dayPaymentRequest.setExpiredDate(tmDPRightsReq.getDpExpiredDate());
            dayPaymentRequest.setDescription(tmDPRightsReq.getDescription());
//            dayPaymentRequest.setDpRightsId(mapResult.get(employeeReq.getEmployeeNIK()));
            dayPaymentRequest = dayPaymentRequestRepository.save(dayPaymentRequest);
            workflowTransService.generateTransaction(dayPaymentRequest.getId(), "DAYPAY", username, tmDPRightsReq.getApprovalPositionCode());
        }


//        Object result = proIntClient.addDayPayment(tmDPRightsReq).getData();
//        Map<?, ?> rawMap = (Map<?, ?>) result;
//        Map<String, Long> mapResult = rawMap.entrySet()
//                .stream()
//                .collect(Collectors.toMap(
//                        entry -> String.valueOf(entry.getKey()),
//                        entry -> ((Number) entry.getValue()).longValue()
//                ));
    }

    public Page<DayPaymentResp> getDayPayment(String empNIK, String empName, LocalDate startDate, LocalDate expiryDate, Pageable pageable, List<String> authorizations){
        List<String> authNIKList = templateAuthService.getAuthEmployees(authorizations);
        List<DayPaymentResp> dayPaymentList = dayPaymentRequestRepository.getDayPayment(empNIK, empName, startDate, expiryDate);
        dayPaymentList = dayPaymentList.stream().filter(e -> authNIKList.contains(e.getEmpNIK())).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dayPaymentList.size());
        if (start > dayPaymentList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, dayPaymentList.size());
        }
        List<DayPaymentResp> pageContent = dayPaymentList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, dayPaymentList.size());
    }

    public DayPaymentRequest getDayPaymentDetail(Long id){
        return dayPaymentRequestRepository.findById(id).get();
    }

    public void deleteDayPayment(Long id){
        DayPaymentRequest dayPaymentRequest = dayPaymentRequestRepository.findById(id).get();

        if(dayPaymentRequest.getDpRightsId() == null){
            throw new ConflictException("Day Payment does not exist");
        }

        Object dayPaymentData = proIntClient.getTMDPRights(dayPaymentRequest.getDpRightsId().intValue()).getData();
        TMDayPayment dayPaymentResult = objectMapper.convertValue(dayPaymentData, new TypeReference<>(){});

        if(dayPaymentResult.getDPRealized() > 0){
            throw new ConflictException("Day payment has realized");
        }

        proIntClient.deleteTMDPRights(dayPaymentRequest.getDpRightsId().intValue());
        dayPaymentRequestRepository.deleteById(id);
    }
}
