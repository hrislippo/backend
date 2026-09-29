package lippo.hris.system.talentmanagement.service;

import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequest;
import lippo.hris.system.talentmanagement.entity.TalentPoolRequestDetail;
import lippo.hris.system.talentmanagement.repository.TalentPoolReadinessRepository;
import lippo.hris.system.talentmanagement.repository.TalentPoolRepository;
import lippo.hris.system.talentmanagement.repository.TalentPoolRequestDetailRepository;
import lippo.hris.system.talentmanagement.repository.TalentPoolRequestRepository;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestReq;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestDetailResp;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestEmployeeResp;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestNineBoxResp;
import lippo.hris.system.talentmanagement.response.TalentPoolRequestResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TalentPoolRequestService {

    @Autowired
    TalentPoolRequestRepository talentPoolRequestRepository;

    @Autowired
    TalentPoolRepository talentPoolRepository;

    @Autowired
    TalentPoolReadinessRepository talentPoolReadinessRepository;

    @Autowired
    TalentPoolRequestDetailRepository talentPoolRequestDetailRepository;

    public void saveTalentPoolRequest(TalentPoolRequestReq talentPoolRequestReq) {
        TalentPoolRequest talentPoolRequest = new TalentPoolRequest();
        talentPoolRequest.setPositionName(talentPoolRequestReq.getPositionName());
        talentPoolRequest.setPositionCode(talentPoolRequestReq.getPositionCode());
        talentPoolRequest.setPilarName(talentPoolRequestReq.getPilarName());
        talentPoolRequest.setBuName(talentPoolRequestReq.getBuName());
        talentPoolRequest = talentPoolRequestRepository.save(talentPoolRequest);

        for(TalentPoolRequestDetailReq talent : talentPoolRequestReq.getTalents()){
            TalentPoolRequestDetail talentPoolRequestDetail = new TalentPoolRequestDetail();
            talentPoolRequestDetail.setHeader(talentPoolRequest);
            talentPoolRequestDetail.setTalent(talent.getTalentId() == null ? null : talentPoolRepository.findById(talent.getTalentId()).get());
            talentPoolRequestDetail.setReadiness(talent.getReadiness() == null ? null : talentPoolReadinessRepository.findById(talent.getReadiness()).get());
            talentPoolRequestDetailRepository.save(talentPoolRequestDetail);
        }
    }

    public void modifyTalentPoolRequest(TalentPoolRequestReq request) {
        TalentPoolRequest talentPoolRequest = talentPoolRequestRepository.findByPositionCode(request.getPositionCode());
        List<TalentPoolRequestDetail> talentPoolRequestDetailList = talentPoolRequestDetailRepository.findByHeader(talentPoolRequest);
        List<TalentPool> talentPoolList = talentPoolRequestDetailList.stream().map(TalentPoolRequestDetail::getTalent).toList();

        List<Long> added = new ArrayList<>(request.getTalents().stream().map(TalentPoolRequestDetailReq::getTalentId).toList());
        added.removeAll(talentPoolList.stream().map(TalentPool::getId).toList());
        List<Long> removed = new ArrayList<>(talentPoolList.stream().map(TalentPool::getId).toList());
        removed.removeAll(request.getTalents().stream().map(TalentPoolRequestDetailReq::getTalentId).toList());
        List<Long> modified = new ArrayList<>(talentPoolList.stream().map(TalentPool::getId).toList());
        modified.removeAll(removed);

        for(Long addTalent : added){
            TalentPoolRequestDetailReq talentPoolRequestDetailReq = request.getTalents().stream().filter(e -> Objects.equals(e.getTalentId(), addTalent)).findFirst().get();
            TalentPoolRequestDetail talentPoolRequestDetail = new TalentPoolRequestDetail();
            talentPoolRequestDetail.setHeader(talentPoolRequest);
            talentPoolRequestDetail.setTalent(talentPoolRepository.findById(addTalent).get());
            talentPoolRequestDetail.setReadiness(talentPoolRequestDetailReq.getReadiness() == null ? null : talentPoolReadinessRepository.findById(talentPoolRequestDetailReq.getReadiness()).get());
            talentPoolRequestDetailRepository.save(talentPoolRequestDetail);
        }

        for(Long removeTalent : removed){
            talentPoolRequestDetailRepository.deleteByHeaderAndTalent(talentPoolRequest, talentPoolRepository.findById(removeTalent).get());
        }

        for(Long modifyTalent : modified){
            TalentPoolRequestDetailReq talentPoolRequestDetailReq = request.getTalents().stream().filter(e -> Objects.equals(e.getTalentId(), modifyTalent)).findFirst().get();
            TalentPoolRequestDetail talentPoolRequestDetail = talentPoolRequestDetailRepository.findByHeaderAndTalent(talentPoolRequest, talentPoolRepository.findById(modifyTalent).get());
            talentPoolRequestDetail.setReadiness(talentPoolRequestDetailReq.getReadiness() == null ? null : talentPoolReadinessRepository.findById(talentPoolRequestDetailReq.getReadiness()).get());
            talentPoolRequestDetailRepository.save(talentPoolRequestDetail);
        }
    }

    public Page<TalentPoolRequestResp> findTalentPoolRequests(String posName, String pilarName, String buName, Pageable pageable) {
        return talentPoolRequestRepository.findTalentPoolRequest(posName, pilarName, buName, pageable);
    }

    public TalentPoolRequestDetailResp findTalentPoolRequestDetail(String posCode) {
        TalentPoolRequest talentPoolRequest = talentPoolRequestRepository.findByPositionCode(posCode);
        List<TalentPoolRequestDetail> talentPoolRequestDetails = talentPoolRequestDetailRepository.findByHeader(talentPoolRequest);

        TalentPoolRequestDetailResp talentPoolRequestDetailResp = new TalentPoolRequestDetailResp();
        talentPoolRequestDetailResp.setPositionName(talentPoolRequest.getPositionName());
        talentPoolRequestDetailResp.setPilarName(talentPoolRequest.getPilarName());
        talentPoolRequestDetailResp.setBuName(talentPoolRequest.getBuName());
        List<TalentPoolRequestEmployeeResp> talentPoolRequestEmployeeResps = new ArrayList<>();

        for(TalentPoolRequestDetail talent : talentPoolRequestDetails){
            TalentPoolRequestEmployeeResp talentPoolRequestEmployeeResp = new TalentPoolRequestEmployeeResp();
            talentPoolRequestEmployeeResp.setTalentId(talent.getTalent().getId());
            talentPoolRequestEmployeeResp.setReadiness(talent.getReadiness() == null ? null : talent.getReadiness().getId());
            talentPoolRequestEmployeeResps.add(talentPoolRequestEmployeeResp);
        }
        talentPoolRequestDetailResp.setTalents(talentPoolRequestEmployeeResps);
        return talentPoolRequestDetailResp;
    }

    public List<TalentPoolRequestNineBoxResp> findTalentPoolRequestNineBox(String posCode) {
        return talentPoolRequestRepository.findTalentPoolRequestNineBox(posCode);
    }
}
