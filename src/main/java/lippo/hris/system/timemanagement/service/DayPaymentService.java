package lippo.hris.system.timemanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelStructureResp;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.repository.DayPaymentRequestRepository;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import lippo.hris.system.timemanagement.response.TMDayPayment;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    ObjectMapper objectMapper;

    public void addDayPayment(TMDPRightsReq tmDPRightsReq, String username){
        tmDPRightsReq.setNikEmpCreate(username);
        Object result = proIntClient.addDayPayment(tmDPRightsReq).getData();
        Map<?, ?> rawMap = (Map<?, ?>) result;
        Map<String, Long> mapResult = rawMap.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> String.valueOf(entry.getKey()),
                        entry -> ((Number) entry.getValue()).longValue()
                ));

        for(String nikEmp : tmDPRightsReq.getNikEmp()){
            DayPaymentRequest dayPaymentRequest = new DayPaymentRequest();
            dayPaymentRequest.setEmployee(nikEmp);
            dayPaymentRequest.setCount(tmDPRightsReq.getDpCount());
            dayPaymentRequest.setDate(tmDPRightsReq.getDpDate());
            dayPaymentRequest.setExpiredDate(tmDPRightsReq.getDpExpiredDate());
            dayPaymentRequest.setDescription(tmDPRightsReq.getDescription());
            dayPaymentRequest.setDpRightsId(mapResult.get(nikEmp));
            dayPaymentRequestRepository.save(dayPaymentRequest);
        }
    }

    public Page<DayPaymentResp> getDayPayment(String empNIK, LocalDate startDate, LocalDate expiryDate, Pageable pageable){
        return dayPaymentRequestRepository.getDayPayment(empNIK, startDate, expiryDate, pageable);
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
