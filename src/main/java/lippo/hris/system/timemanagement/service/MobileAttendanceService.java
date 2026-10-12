package lippo.hris.system.timemanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.service.TemplateAuthService;
import lippo.hris.system.exception.NotFoundException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelFileResp;
import lippo.hris.system.timemanagement.entity.MobileAttendanceRequest;
import lippo.hris.system.timemanagement.repository.MobileAttendanceRequestRepository;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrEmpReq;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import lippo.hris.system.timemanagement.response.EmployeeResp;
import lippo.hris.system.timemanagement.response.MobileAttendanceResp;
import lippo.hris.system.timemanagement.response.MobileTemplateResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MobileAttendanceService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MobileAttendanceRequestRepository mobileAttendanceRequestRepository;

    @Autowired
    TemplateAuthService templateAuthService;

    public void addMobileAttendance(MOTMAtdTempMbrReq motmAtdTempMbrReq, String username) {

        motmAtdTempMbrReq.setCreatedBy(username);
        Object employeeData = proIntClient.getEmployeeInfo(motmAtdTempMbrReq.getEmployee().stream().map(MOTMAtdTempMbrEmpReq::getEmployeeNIK).toList()).getData();
        List<EmployeeResp> employees = objectMapper.convertValue(employeeData, new TypeReference<List<EmployeeResp>>(){});
        List<String> missingNiks = employees.stream().filter(emp -> "Employee not found".equals(emp.getEmpName()))
                .map(EmployeeResp::getEmpNik).toList();
        if (!missingNiks.isEmpty()) {
            throw new NotFoundException("Employees not found: " + String.join(", ", missingNiks));
        }

        Object attendanceTemplate = proIntClient.getMOTMAtdTemplate(motmAtdTempMbrReq.getTempCode()).getData();
        if(attendanceTemplate == null){
            throw new NotFoundException("Attendance template not found");
        }

        for(MOTMAtdTempMbrEmpReq employee : motmAtdTempMbrReq.getEmployee()){
            MobileAttendanceRequest mobileAttendanceRequest = new MobileAttendanceRequest();
            mobileAttendanceRequest.setEmployee(employee.getEmployeeNIK());
            mobileAttendanceRequest.setEmployeeName(employee.getEmployeeName());
            mobileAttendanceRequest.setTemplateCode(motmAtdTempMbrReq.getTempCode());
            mobileAttendanceRequest.setStartDate(motmAtdTempMbrReq.getStartDate());
            mobileAttendanceRequest.setEndDate(motmAtdTempMbrReq.getEndDate());
            mobileAttendanceRequest.setDescription(motmAtdTempMbrReq.getDescription());

            if((motmAtdTempMbrReq.getStartDate() == null && motmAtdTempMbrReq.getEndDate() == null) ||
                    ((LocalDate.now().isAfter(motmAtdTempMbrReq.getStartDate()) &&
                    LocalDate.now().isBefore(motmAtdTempMbrReq.getEndDate())) ||
            LocalDate.now().isEqual(motmAtdTempMbrReq.getStartDate()) ||
            LocalDate.now().isEqual(motmAtdTempMbrReq.getEndDate()))){
                motmAtdTempMbrReq.setNik(employee.getEmployeeNIK());
                proIntClient.addMobileAttendanceTemplateMember(motmAtdTempMbrReq);

                mobileAttendanceRequest.setExist(true);
            }
            mobileAttendanceRequestRepository.save(mobileAttendanceRequest);
        }
    }

    public Page<MobileAttendanceResp> getMobileAttendance(String empNIK, String empName, String tempCode, LocalDate startDate, LocalDate endDate,
                                                          Pageable pageable, List<String> authorizations){
        List<String> authNIKList = templateAuthService.getAuthEmployees(authorizations);
        List<MobileAttendanceResp> mobileAttendanceList = mobileAttendanceRequestRepository.getMobileAttendance(empNIK, empName, tempCode, startDate, endDate);
        mobileAttendanceList = mobileAttendanceList.stream().filter(e -> authNIKList.contains(e.getEmpNIK())).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mobileAttendanceList.size());
        if (start > mobileAttendanceList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, mobileAttendanceList.size());
        }
        List<MobileAttendanceResp> pageContent = mobileAttendanceList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, mobileAttendanceList.size());
    }

    public MobileAttendanceRequest getMobileAttendanceDetail(Long id){
        return mobileAttendanceRequestRepository.findById(id).get();
    }

    public List<MobileTemplateResp> getAllMobileTemplate(){
        Object result = proIntClient.getMOTMAtdTemplateAll().getData();
        List<MobileTemplateResp> mobileTemplate = objectMapper.convertValue(result, new TypeReference<>(){});
        return mobileTemplate;
    }
}
