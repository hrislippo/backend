package lippo.hris.system.personnelmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelStructureNIKResp;
import lippo.hris.system.personnelmanagement.response.PersonnelStructureResp;
import lippo.hris.system.timemanagement.response.EmployeeResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class PersonnelStructureService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    public Page<PersonnelStructureNIKResp> getEmployee(String name, String position, Pageable pageable){
        Object employeeData = proIntClient.getEmployeePosition(name, position, pageable).getData();
        Page<PersonnelStructureNIKResp> employees = objectMapper.convertValue(employeeData, new TypeReference<>(){});
        return employees;
    }

    public List<PersonnelStructureResp> getEmployeeStructure(String empNIK){
        Object employeeData = proIntClient.getEmployeeStructure(empNIK).getData();
        List<PersonnelStructureResp> employees = objectMapper.convertValue(employeeData, new TypeReference<>(){});

        for(PersonnelStructureResp employee : employees){
            if(employee.getEmployeePhoto() != null){
                employee.setBase64EmployeePhoto(Base64.getEncoder().encodeToString(employee.getEmployeePhoto()));
                employee.setEmployeePhoto(null);
            }
        }
        return employees;
    }
}
