package lippo.hris.system.personnel.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnel.response.PersonnelDetailResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional
public class PersonnelService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    public PersonnelDetailResp getEmployeeDetail(String empNIK){
        Object employeeData = proIntClient.getEmployeeDetail(empNIK).getData();
        PersonnelDetailResp employee = objectMapper.convertValue(employeeData, new TypeReference<>(){});

        if(employee.getEmpPhoto() != null){
            employee.setBase64EmployeePhoto(Base64.getEncoder().encodeToString(employee.getEmpPhoto()));
            employee.setEmpPhoto(null);
        }
        return employee;
    }
}
