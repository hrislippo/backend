package lippo.hris.system.talentmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelDetailResp;
import lippo.hris.system.personnelmanagement.response.PersonnelStructureNIKResp;
import lippo.hris.system.talentmanagement.entity.*;
import lippo.hris.system.talentmanagement.repository.*;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import lippo.hris.system.talentmanagement.response.PositionResp;
import lippo.hris.system.talentmanagement.response.TalentPoolProfileResp;
import lippo.hris.system.talentmanagement.response.TalentPoolResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
public class TalentPoolService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TalentPoolRepository talentPoolRepository;

    @Autowired
    TalentPoolLogRepository talentPoolLogRepository;

    @Autowired
    TalentPoolPerformanceRepository talentPoolPerformanceRepository;

    @Autowired
    TalentPoolPotentialRepository talentPoolPotentialRepository;

    public void saveTalentPool(List<TalentPoolReq> talentPoolReq) {
        for(TalentPoolReq talentPoolReqItem : talentPoolReq) {
            TalentPool talentPool = new TalentPool();
            talentPool.setEmployeeNIK(talentPoolReqItem.getEmployeeNIK());
            talentPool.setEmployeeName(talentPoolReqItem.getEmployeeName());
            talentPool.setPositionName(talentPoolReqItem.getPositionName());
            talentPool.setOrganizationName(talentPoolReqItem.getOrganizationName());
            talentPool.setLocationName(talentPoolReqItem.getLocationName());
            talentPool.setCompanyName(talentPoolReqItem.getCompanyName());
            talentPool.setPotential(talentPoolReqItem.getPotential() == null ? null : talentPoolPotentialRepository.findById(talentPoolReqItem.getPotential()).orElse(null));
            talentPool.setPerformance(talentPoolReqItem.getPerformance() == null ? null : talentPoolPerformanceRepository.findById(talentPoolReqItem.getPerformance()).orElse(null));
            talentPoolRepository.save(talentPool);
        }
    }

    public void modifyTalentPool(TalentPoolReq talentPoolReq) {
        TalentPool talentPool = talentPoolRepository.findByEmployeeNIK(talentPoolReq.getEmployeeNIK());
        TalentPoolPotential talentPoolPotential = talentPoolReq.getPotential() == null ? null : talentPoolPotentialRepository.findById(talentPoolReq.getPotential()).orElse(null);
        TalentPoolPerformance talentPoolPerformance = talentPoolReq.getPerformance() == null ? null : talentPoolPerformanceRepository.findById(talentPoolReq.getPerformance()).orElse(null);

        TalentPoolLog talentPoolLog = new TalentPoolLog();
        talentPoolLog.setTalentPool(talentPool);
        talentPoolLog.setOldPotential(talentPool.getPotential());
        talentPoolLog.setOldPerformance(talentPool.getPerformance());
        talentPoolLog.setNewPotential(talentPoolPotential);
        talentPoolLog.setNewPerformance(talentPoolPerformance);
        talentPoolLog.setReason(talentPoolReq.getReason());
        talentPoolLogRepository.save(talentPoolLog);

        talentPool.setPotential(talentPoolPotential);
        talentPool.setPerformance(talentPoolPerformance);
        talentPoolRepository.save(talentPool);
    }

    public List<TalentPool> getAllTalentPool() {
        return talentPoolRepository.findAll();
    }

    public Page<TalentPoolResp> getAllTalentPool(String employeeNIK, String employeeName, Pageable pageable) {
        return talentPoolRepository.findAllByNIKAndName(employeeNIK, employeeName, pageable);
    }

    public TalentPoolProfileResp getTalentProfile(String empNIK){
        Object employeeData = proIntClient.getEmployeeDetail(empNIK).getData();

        if(employeeData == null){
            TalentPool talentPool = talentPoolRepository.findByEmployeeNIK(empNIK);
            TalentPoolProfileResp talentPoolProfileResp = new TalentPoolProfileResp();
            talentPoolProfileResp.setEmpNIK(talentPool.getEmployeeNIK());
            talentPoolProfileResp.setEmpName(talentPool.getEmployeeName());
            talentPoolProfileResp.setPosName(talentPool.getPositionName());
            talentPoolProfileResp.setOrgName(talentPool.getOrganizationName());
            talentPoolProfileResp.setLocationName(talentPool.getLocationName());
            talentPoolProfileResp.setCompName(talentPool.getCompanyName());
            return talentPoolProfileResp;
        }

        TalentPoolProfileResp employee = objectMapper.convertValue(employeeData, new TypeReference<>(){});

        if(employee.getEmpPhoto() != null){
            employee.setBase64EmployeePhoto(Base64.getEncoder().encodeToString(employee.getEmpPhoto()));
            employee.setEmpPhoto(null);
        }
        return employee;
    }

    public TalentPool getTalentPoolDetail(String employeeNIK){
        return talentPoolRepository.findByEmployeeNIK(employeeNIK);
    }

    public List<PositionResp> getAllActivePosition(){
        Object positionData = proIntClient.getActivePosition().getData();
        List<PositionResp> positions = objectMapper.convertValue(positionData, new TypeReference<>(){});
        return positions;
    }

    public List<PersonnelStructureNIKResp> getAllActiveEmployee(String posCode){
        Object employeeData = proIntClient.getActiveEmployee(posCode).getData();
        List<PersonnelStructureNIKResp> employees = objectMapper.convertValue(employeeData, new TypeReference<>(){});
        return employees;
    }
 }
