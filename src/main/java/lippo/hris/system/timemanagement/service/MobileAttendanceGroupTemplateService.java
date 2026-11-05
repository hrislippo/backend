package lippo.hris.system.timemanagement.service;

import lippo.hris.system.organization.repository.HRBPRepository;
import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplate;
import lippo.hris.system.timemanagement.entity.MobileAttendanceGroupTemplateDt;
import lippo.hris.system.timemanagement.entity.MobileAttendanceRequest;
import lippo.hris.system.timemanagement.repository.MobileAttendanceGroupTemplateDtRepository;
import lippo.hris.system.timemanagement.repository.MobileAttendanceGroupTemplateRepository;
import lippo.hris.system.timemanagement.request.MobileAttendanceGroupTemplateDtReq;
import lippo.hris.system.timemanagement.request.MobileAttendanceGroupTemplateReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import lippo.hris.system.timemanagement.response.MobileAttendanceGroupTempDtResp;
import lippo.hris.system.timemanagement.response.MobileAttendanceGroupTempResp;
import lippo.hris.system.timemanagement.response.MobileAttendanceGroupTemplateDtResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MobileAttendanceGroupTemplateService {

    @Autowired
    MobileAttendanceGroupTemplateRepository mobileAttendanceGroupTemplateRepository;

    @Autowired
    MobileAttendanceGroupTemplateDtRepository mobileAttendanceGroupTemplateDtRepository;

    @Autowired
    HRBPRepository hrbpRepository;

    public void addMobileAttendanceGroupTemplate(MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq){
        MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate = new MobileAttendanceGroupTemplate();
        mobileAttendanceGroupTemplate.setCode(mobileAttendanceGroupTemplateReq.getCode());
        mobileAttendanceGroupTemplate.setName(mobileAttendanceGroupTemplateReq.getName());
        mobileAttendanceGroupTemplate.setHrbp(hrbpRepository.findById(mobileAttendanceGroupTemplateReq.getHrbpId()).get());
        mobileAttendanceGroupTemplate = mobileAttendanceGroupTemplateRepository.save(mobileAttendanceGroupTemplate);

        for(MobileAttendanceGroupTemplateDtReq mobileAttendanceGroupTemplateDtReq : mobileAttendanceGroupTemplateReq.getDetailTemplates()){
            MobileAttendanceGroupTemplateDt mobileAttendanceGroupTemplateDt = new MobileAttendanceGroupTemplateDt();
            mobileAttendanceGroupTemplateDt.setMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplate);
            mobileAttendanceGroupTemplateDt.setTemplateCode(mobileAttendanceGroupTemplateDtReq.getTemplateCode());
            mobileAttendanceGroupTemplateDt.setTemplateName(mobileAttendanceGroupTemplateDtReq.getTemplateName());
            mobileAttendanceGroupTemplateDtRepository.save(mobileAttendanceGroupTemplateDt);
        }
    }

    public void modifyMobileAttendanceGroupTemplate(MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq){
        MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate = mobileAttendanceGroupTemplateRepository.findById(mobileAttendanceGroupTemplateReq.getId()).get();
        mobileAttendanceGroupTemplate.setCode(mobileAttendanceGroupTemplateReq.getCode());
        mobileAttendanceGroupTemplate.setName(mobileAttendanceGroupTemplateReq.getName());
        mobileAttendanceGroupTemplate.setHrbp(hrbpRepository.findById(mobileAttendanceGroupTemplateReq.getHrbpId()).get());
        mobileAttendanceGroupTemplate = mobileAttendanceGroupTemplateRepository.save(mobileAttendanceGroupTemplate);

        List<MobileAttendanceGroupTemplateDt> mobileAttendanceGroupTemplateDtList = mobileAttendanceGroupTemplateDtRepository.findByMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplate);
        List<MobileAttendanceGroupTemplateDtReq> existingMobileAttendanceDetail = new ArrayList<>();
        for(MobileAttendanceGroupTemplateDt mobileAttendanceGroupTemplateDt : mobileAttendanceGroupTemplateDtList){
            MobileAttendanceGroupTemplateDtReq mobileAttendanceGroupTemplateDtReq = new MobileAttendanceGroupTemplateDtReq();
            mobileAttendanceGroupTemplateDtReq.setTemplateCode(mobileAttendanceGroupTemplateDt.getTemplateCode());
            mobileAttendanceGroupTemplateDtReq.setTemplateName(mobileAttendanceGroupTemplateDt.getTemplateName());
            existingMobileAttendanceDetail.add(mobileAttendanceGroupTemplateDtReq);
        }

        List<MobileAttendanceGroupTemplateDtReq> added = new ArrayList<>(mobileAttendanceGroupTemplateReq.getDetailTemplates());
        added.removeAll(existingMobileAttendanceDetail);
        List<MobileAttendanceGroupTemplateDtReq> removed = new ArrayList<>(existingMobileAttendanceDetail);
        removed.removeAll(mobileAttendanceGroupTemplateReq.getDetailTemplates());

        for(MobileAttendanceGroupTemplateDtReq add : added){
            MobileAttendanceGroupTemplateDt mobileAttendanceGroupTemplateDt = new MobileAttendanceGroupTemplateDt();
            mobileAttendanceGroupTemplateDt.setMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplate);
            mobileAttendanceGroupTemplateDt.setTemplateCode(add.getTemplateCode());
            mobileAttendanceGroupTemplateDt.setTemplateName(add.getTemplateName());
            mobileAttendanceGroupTemplateDtRepository.save(mobileAttendanceGroupTemplateDt);
        }

        for(MobileAttendanceGroupTemplateDtReq remove : removed){
            MobileAttendanceGroupTemplateDt mobileAttendanceGroupTemplateDt =
                    mobileAttendanceGroupTemplateDtRepository.findByMobileAttendanceGroupTemplateAndTemplateCode(mobileAttendanceGroupTemplate, remove.getTemplateCode());
            mobileAttendanceGroupTemplateDtRepository.delete(mobileAttendanceGroupTemplateDt);
        }
    }

    public Page<MobileAttendanceGroupTempResp> getMobileAttendanceGroupTemplate(String codeSearch, String nameSearch, String buSearch, String hrbpSearch, Pageable pageable){
        List<MobileAttendanceGroupTempResp> mobileAttendanceGroupTempRespList = mobileAttendanceGroupTemplateRepository.getMobileAttendanceGroupTemplate
                (codeSearch, nameSearch, buSearch, hrbpSearch);
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), mobileAttendanceGroupTempRespList.size());
        if (start > mobileAttendanceGroupTempRespList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, mobileAttendanceGroupTempRespList.size());
        }
        List<MobileAttendanceGroupTempResp> pageContent = mobileAttendanceGroupTempRespList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, mobileAttendanceGroupTempRespList.size());
    }

    public MobileAttendanceGroupTempDtResp getMobileAttendanceGroupTemplateDetail(Long id){
        MobileAttendanceGroupTempDtResp mobileAttendanceGroupTempDtResp = new MobileAttendanceGroupTempDtResp();
        MobileAttendanceGroupTemplate mobileAttendanceGroupTemplate = mobileAttendanceGroupTemplateRepository.findById(id).get();
        mobileAttendanceGroupTempDtResp.setMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplate);

        List<MobileAttendanceGroupTemplateDtResp> mobileAttendanceGroupTemplateDtRespList = new ArrayList<>();
        List<MobileAttendanceGroupTemplateDt> mobileAttendanceGroupTemplateDtList = mobileAttendanceGroupTemplateDtRepository.findByMobileAttendanceGroupTemplate(mobileAttendanceGroupTemplate);
        for(MobileAttendanceGroupTemplateDt mobileAttendanceGroupTemplateDt : mobileAttendanceGroupTemplateDtList){
            MobileAttendanceGroupTemplateDtResp mobileAttendanceGroupTemplateDtResp = new MobileAttendanceGroupTemplateDtResp();
            mobileAttendanceGroupTemplateDtResp.setTemplateCode(mobileAttendanceGroupTemplateDt.getTemplateCode());
            mobileAttendanceGroupTemplateDtResp.setTemplateName(mobileAttendanceGroupTemplateDt.getTemplateName());
            mobileAttendanceGroupTemplateDtRespList.add(mobileAttendanceGroupTemplateDtResp);
        }
        mobileAttendanceGroupTempDtResp.setTemplateList(mobileAttendanceGroupTemplateDtRespList);
        return mobileAttendanceGroupTempDtResp;
    }
}
