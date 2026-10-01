package lippo.hris.system.talentmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelStructureResp;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequest;
import lippo.hris.system.talentmanagement.repository.TalentPoolRequestRepository;
import lippo.hris.system.talentmanagement.response.TalentPoolStructureResp;
import lippo.hris.system.talentmanagement.response.TalentStructureResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class TalentPoolStructureService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TalentPoolRequestRepository talentPoolRequestRepository;

    public Page<TalentPoolStructureResp> getTalent(String name, String position, Pageable pageable){
        return talentPoolRequestRepository.findTalentPoolRequest(name, position, pageable);
    }

    public List<TalentStructureResp> getTalentStructure(String empNIK, String posName, Integer subordinateDepth, Integer superiorDepth){
        Object employeeData = proIntClient.getEmployeeStructure(empNIK, posName).getData();
        List<TalentStructureResp> talents = objectMapper.convertValue(employeeData, new TypeReference<>(){});
        talents = talents.stream().filter(e -> e.getHierarchyLevel() >= subordinateDepth
                && e.getHierarchyLevel() <= superiorDepth).toList();

        for(TalentStructureResp talent : talents){
            if(talent.getEmployeePhoto() != null){
                talent.setBase64EmployeePhoto(Base64.getEncoder().encodeToString(talent.getEmployeePhoto()));
                talent.setEmployeePhoto(null);
            }
            TalentPoolRequest talentPoolRequest = talentPoolRequestRepository.findByPositionCode(talent.getPositionCode());
            talent.setTalent(talentPoolRequest != null && talentPoolRequest.getTalent().getEmployeeNIK().equals(talent.getEmployeeNIK()));
        }
        return talents;
    }
}
