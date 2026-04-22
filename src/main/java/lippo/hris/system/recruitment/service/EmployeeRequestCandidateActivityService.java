package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.*;
import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormActivityStatus;
import lippo.hris.system.recruitment.repository.*;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import lippo.hris.system.recruitment.response.RecruitmentActivityTemplateResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeRequestCandidateActivityService {

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    @Autowired
    EmployeeRequestCandidateRepository employeeRequestCandidateRepository;

    @Autowired
    RecruitmentActivityRepository recruitmentActivityRepository;

    @Autowired
    RecruitmentActivityTemplateRepository recruitmentActivityTemplateRepository;

    @Autowired
    RecruitmentTemplateRepository recruitmentTemplateRepository;

//    public void modifyEmployeeRequestCandidateActivity(EmployeeRequestReq employeeRequestReq,
//                                                       EmployeeRequestForm employeeRequestForm){
//        List<Long> activityIdListNew = new ArrayList<>();
//        List<Long> templateIdListNew = new ArrayList<>();
//        for(Long templateNew : employeeRequestReq.getTemplate()){
//            List<RecruitmentActivityTemplateResp> activityIdList = recruitmentActivityTemplateRepository.getActivitiesByTemplate(templateNew);
//            for(RecruitmentActivityTemplateResp activity : activityIdList){
//                activityIdListNew.add(activity.getId());
//                templateIdListNew.add(templateNew);
//            }
//        }
//
//        List<Long> activityIdListExisting = employeeRequestCandidateActivityRepository.findActivityIdByEmployeeRequest(employeeRequestForm.getId());
//        if(!activityIdListExisting.equals(activityIdListNew)){
//            List<EmployeeRequestCandidate> employeeRequestCandidateList = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);
//            for(EmployeeRequestCandidate employeeRequestCandidate : employeeRequestCandidateList){
//                List<EmployeeRequestCandidateActivity> employeeRequestCandidateActivityList = employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidate(employeeRequestCandidate);
//                employeeRequestCandidateActivityRepository.deleteAll(employeeRequestCandidateActivityList);
//
//                Integer order = 0;
//                for(Long activityId : activityIdListNew){
//                    order++;
//                    RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(templateIdListNew.get(activityIdListNew.indexOf(activityId))).get();
//                    RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(activityId).get();
//
//                    EmployeeRequestCandidateActivity employeeRequestCandidateActivity = new EmployeeRequestCandidateActivity();
//                    employeeRequestCandidateActivity.setEmployeeRequestCandidate(employeeRequestCandidate);
//                    employeeRequestCandidateActivity.setRecruitmentActivity(recruitmentActivity);
//                    employeeRequestCandidateActivity.setRecruitmentTemplate(recruitmentTemplate);
//                    employeeRequestCandidateActivity.setOrder(order);
//                    employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.NOT_STARTED.toString());
//                    employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
//                }
//            }
//        }
//    }

    public void modifyEmployeeRequestActivity(EmployeeRequestForm employeeRequestForm){
        List<EmployeeRequestCandidate> candidateList = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);
        List<RecruitmentActivityTemplate> recruitmentActivityTemplateList =
                recruitmentActivityTemplateRepository.findByTemplate(employeeRequestForm.getRecruitmentTemplate());
        List<RecruitmentActivity> recruitmentActivityList = recruitmentActivityTemplateList.stream().map(RecruitmentActivityTemplate::getActivity).toList();

        for(EmployeeRequestCandidate candidate : candidateList){
            employeeRequestCandidateActivityRepository.deleteAllByEmployeeRequestCandidate(candidate);
            for(RecruitmentActivity recruitmentActivity : recruitmentActivityList){
                EmployeeRequestCandidateActivity employeeRequestCandidateActivity = new EmployeeRequestCandidateActivity();
                employeeRequestCandidateActivity.setEmployeeRequestCandidate(candidate);
                employeeRequestCandidateActivity.setRecruitmentActivity(recruitmentActivity);
                employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.NOT_STARTED.toString());
                employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
            }
        }
    }
}
