package lippo.hris.system.recruitment.service;

import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.recruitment.entity.RecruitmentActivity;
import lippo.hris.system.recruitment.enumeration.RecruitmentGroupActivity;
import lippo.hris.system.recruitment.repository.RecruitmentActivityRepository;
import lippo.hris.system.recruitment.request.ActivityEmailReq;
import lippo.hris.system.recruitment.request.ActivityInterviewReq;
import lippo.hris.system.recruitment.request.ActivityReq;
import lippo.hris.system.recruitment.response.RecruitmentActivityResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RecruitmentActivityService {

    @Autowired
    RecruitmentActivityRepository recruitmentActivityRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    public void addActivity(ActivityReq activityReq) {
        RecruitmentActivity recruitmentActivity = new RecruitmentActivity();
        recruitmentActivity.setCode(activityReq.getCode());
        recruitmentActivity.setName(activityReq.getName());
        recruitmentActivity.setGroup(activityReq.getGroup());
        recruitmentActivity.setFlagInterview(activityReq.getInterview());
        recruitmentActivity.setFlagEmail(activityReq.getEmail());

        if(activityReq.getEmailTemplateId() != null) {
            EmailTemplate emailTemplate = emailTemplateRepository.findById(activityReq.getEmailTemplateId()).get();
            recruitmentActivity.setEmailTemplate(emailTemplate);
        }
        recruitmentActivityRepository.save(recruitmentActivity);
    }

    public void modifyActivity(ActivityReq activityReq) {
        RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityReq.getId()).get();
        recruitmentActivity.setCode(activityReq.getCode());
        recruitmentActivity.setName(activityReq.getName());
        recruitmentActivity.setGroup(activityReq.getGroup());
        recruitmentActivity.setFlagInterview(activityReq.getInterview());
        recruitmentActivity.setFlagEmail(activityReq.getEmail());

        if(activityReq.getEmailTemplateId() != null) {
            EmailTemplate emailTemplate = emailTemplateRepository.findById(activityReq.getEmailTemplateId()).orElse(null);
            recruitmentActivity.setEmailTemplate(emailTemplate);
        }else{
            recruitmentActivity.setEmailTemplate(null);
        }
        recruitmentActivityRepository.save(recruitmentActivity);
    }

    public void modifyActivityInterview(ActivityInterviewReq activityInterviewReq) {
        RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityInterviewReq.getId()).get();
        recruitmentActivity.setFlagInterview(activityInterviewReq.getInterview());
        recruitmentActivityRepository.save(recruitmentActivity);
    }

    public void modifyActivityEmail(ActivityEmailReq activityEmailReq) {
        RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityEmailReq.getId()).get();
        recruitmentActivity.setFlagEmail(activityEmailReq.getEmail());
        recruitmentActivityRepository.save(recruitmentActivity);
    }

    public List<String> getGroupActivity() {
        List<String> groupActivity = new ArrayList<>();
        groupActivity.addAll(Arrays.stream(RecruitmentGroupActivity.values())
                .sorted(Comparator.comparing(RecruitmentGroupActivity::getOrder))
                .map(RecruitmentGroupActivity::getLabel)
                .toList());
        return groupActivity;
    }

    public Page<RecruitmentActivityResp> getActivity(String group, String code, String name,
                                                     Pageable pageable) {
        return recruitmentActivityRepository.getActivity(group, code, name, pageable);
    }

    public List<RecruitmentActivity> getActivityList(){
        return recruitmentActivityRepository.findAll();
    }

    public RecruitmentActivity getActivityDetail(Long id) {
        return recruitmentActivityRepository.findById(id).get();
    }

    public void deleteActivity(Long id) {
        recruitmentActivityRepository.deleteById(id);
    }
}
