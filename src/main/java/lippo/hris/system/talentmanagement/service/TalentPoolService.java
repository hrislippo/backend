package lippo.hris.system.talentmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.repository.TalentPoolReadinessRepository;
import lippo.hris.system.talentmanagement.repository.TalentPoolRepository;
import lippo.hris.system.talentmanagement.request.TalentPoolDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolEmployeeReq;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import lippo.hris.system.talentmanagement.response.PositionResp;
import lippo.hris.system.talentmanagement.response.TalentPoolDetailResp;
import lippo.hris.system.talentmanagement.response.TalentPoolEmployeeResp;
import lippo.hris.system.talentmanagement.response.TalentPoolResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    TalentPoolReadinessRepository talentPoolReadinessRepository;

    public void saveTalentPool(TalentPoolReq talentPoolReq) {
        for(String employeeNIK : talentPoolReq.getEmployeeNIK()) {
            TalentPool talentPool = new TalentPool();
            talentPool.setPositionCode(talentPoolReq.getPositionCode());
            talentPool.setPositionName(talentPoolReq.getPositionName());
            talentPool.setEmployeeNIK(employeeNIK);
            talentPoolRepository.save(talentPool);
        }
    }

    public void modifyTalentPool(TalentPoolDetailReq request) {
        List<TalentPool> talentPools = talentPoolRepository.findByPositionCode(request.getPositionCode());
        List<String> added = new ArrayList<>(request.getEmployeeList().stream().map(TalentPoolEmployeeReq::getEmployeeNIK).toList());
        added.removeAll(talentPools.stream().map(TalentPool::getEmployeeNIK).toList());
        List<String> removed = new ArrayList<>(talentPools.stream().map(TalentPool::getEmployeeNIK).toList());
        removed.removeAll(request.getEmployeeList().stream().map(TalentPoolEmployeeReq::getEmployeeNIK).toList());
        List<String> modified = new ArrayList<>(talentPools.stream().map(TalentPool::getEmployeeNIK).toList());
        modified.removeAll(removed);

        for(String addNIK : added){
            TalentPoolEmployeeReq employee = request.getEmployeeList().stream().filter(e -> e.getEmployeeNIK().equals(addNIK)).toList().getFirst();
            TalentPool talentPool = new TalentPool();
            talentPool.setPositionCode(request.getPositionCode());
            talentPool.setPositionName(request.getPositionName());
            talentPool.setEmployeeNIK(employee.getEmployeeNIK());
            talentPool.setPerformance(employee.getPerformance());
            talentPool.setPotential(employee.getPotential());
            talentPool.setReadiness(employee.getReadiness() == null ? null : talentPoolReadinessRepository.findByName(employee.getReadiness()));
            talentPoolRepository.save(talentPool);
        }

        for(String removeNIK : removed){
            talentPoolRepository.deleteByPositionCodeAndEmployeeNIK(request.getPositionCode(), removeNIK);
        }

        for(String modifyNIK : modified){
            TalentPoolEmployeeReq employee = request.getEmployeeList().stream().filter(e -> e.getEmployeeNIK().equals(modifyNIK)).toList().getFirst();
            TalentPool talentPool = talentPools.stream().filter(e -> e.getEmployeeNIK().equals(modifyNIK)).toList().getFirst();
            talentPool.setPerformance(employee.getPerformance());
            talentPool.setPotential(employee.getPotential());
            talentPool.setReadiness(employee.getReadiness() == null ? null : talentPoolReadinessRepository.findByName(employee.getReadiness()));
        }
    }

    public Page<TalentPoolResp> getAllTalentPool(String positionCode, String positionName, Pageable pageable) {
        return talentPoolRepository.findAllByPosition(positionCode, positionName, pageable);
    }

    public TalentPoolDetailResp getTalentPoolDetail(String positionCode){
        List<TalentPool> talentPoolList = talentPoolRepository.findByPositionCode(positionCode);
        List<TalentPoolEmployeeResp> detailEmployee = new ArrayList<>();
        for(TalentPool talentPool : talentPoolList){
            TalentPoolEmployeeResp talentPoolEmployeeResp = new TalentPoolEmployeeResp();
            talentPoolEmployeeResp.setEmployeeNIK(talentPool.getEmployeeNIK());
            talentPoolEmployeeResp.setPerformance(talentPool.getPerformance());
            talentPoolEmployeeResp.setPotential(talentPool.getPotential());
            talentPoolEmployeeResp.setReadiness(talentPool.getReadiness() == null ? null : talentPool.getReadiness().getName());
            detailEmployee.add(talentPoolEmployeeResp);
        }

        TalentPoolDetailResp detail = new TalentPoolDetailResp();
        detail.setPositionCode(talentPoolList.get(0).getPositionCode());
        detail.setPositionName(talentPoolList.get(0).getPositionName());
        detail.setTalentPoolEmployee(detailEmployee);
        return detail;
    }

    public List<PositionResp> getAllActivePosition(){
        Object positionData = proIntClient.getActivePosition().getData();
        List<PositionResp> positions = objectMapper.convertValue(positionData, new TypeReference<>(){});
        return positions;
    }
 }
