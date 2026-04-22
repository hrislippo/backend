package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.RecruitmentActivity;
import lippo.hris.system.recruitment.entity.RecruitmentActivityTemplate;
import lippo.hris.system.recruitment.entity.RecruitmentTemplate;
import lippo.hris.system.recruitment.repository.RecruitmentActivityRepository;
import lippo.hris.system.recruitment.repository.RecruitmentActivityTemplateRepository;
import lippo.hris.system.recruitment.repository.RecruitmentTemplateRepository;
import lippo.hris.system.recruitment.request.TemplateReq;
import lippo.hris.system.recruitment.response.RecruitmentTemplateActivityResp;
import lippo.hris.system.recruitment.response.RecruitmentTemplateResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecruitmentTemplateService {

    @Autowired
    RecruitmentTemplateRepository recruitmentTemplateRepository;

    @Autowired
    RecruitmentActivityRepository recruitmentActivityRepository;

    @Autowired
    RecruitmentActivityTemplateRepository recruitmentActivityTemplateRepository;

    public void addTemplate(TemplateReq templateReq) {RecruitmentTemplate recruitmentTemplate = new RecruitmentTemplate();
        recruitmentTemplate.setName(templateReq.getName());
        recruitmentTemplate.setCode(templateReq.getCode());
        recruitmentTemplate = recruitmentTemplateRepository.save(recruitmentTemplate);

        Integer order = 0;
        for(Long activityId : templateReq.getActivityIds()){
            order++;
            RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityId).get();
            RecruitmentActivityTemplate recruitmentActivityTemplate = new RecruitmentActivityTemplate();
            recruitmentActivityTemplate.setTemplate(recruitmentTemplate);
            recruitmentActivityTemplate.setActivity(recruitmentActivity);
            recruitmentActivityTemplate.setOrder(order);
            recruitmentActivityTemplateRepository.save(recruitmentActivityTemplate);
        }
    }

    public void modifyTemplate(TemplateReq templateReq) {
        RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(templateReq.getId()).get();
        recruitmentTemplate.setName(templateReq.getName());
        recruitmentTemplate.setCode(templateReq.getCode());
        recruitmentTemplate = recruitmentTemplateRepository.save(recruitmentTemplate);
        recruitmentActivityTemplateRepository.deleteAllByTemplate(recruitmentTemplate);

        Integer order = 0;
        for(Long activityId : templateReq.getActivityIds()){
            order++;
            RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityId).get();
            RecruitmentActivityTemplate recruitmentActivityTemplate = new RecruitmentActivityTemplate();
            recruitmentActivityTemplate.setTemplate(recruitmentTemplate);
            recruitmentActivityTemplate.setActivity(recruitmentActivity);
            recruitmentActivityTemplate.setOrder(order);
            recruitmentActivityTemplateRepository.save(recruitmentActivityTemplate);
        }
    }

    public Page<RecruitmentTemplateResp> getTemplate(String code, String name, Pageable pageable) {
        return recruitmentTemplateRepository.getTemplate(code, name, pageable);
    }

    public List<RecruitmentTemplate> getTemplateList(){
        return recruitmentTemplateRepository.findAll();
    }

    public RecruitmentTemplateActivityResp getTemplateDetail(Long id) {
        RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(id).get();

        RecruitmentTemplateActivityResp recruitmentTemplateActivityResp = new RecruitmentTemplateActivityResp();
        recruitmentTemplateActivityResp.setCode(recruitmentTemplate.getCode());
        recruitmentTemplateActivityResp.setName(recruitmentTemplate.getName());
        recruitmentTemplateActivityResp.setActivities(recruitmentActivityTemplateRepository.getActivitiesByTemplate(id));
        return recruitmentTemplateActivityResp;
    }

    public void deleteTemplate(Long id) {
        RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(id).get();
        recruitmentActivityTemplateRepository.deleteAllByTemplate(recruitmentTemplate);
        recruitmentTemplateRepository.delete(recruitmentTemplate);
    }
}
