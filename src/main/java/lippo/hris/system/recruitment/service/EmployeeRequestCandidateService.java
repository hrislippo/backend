package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.recruitment.entity.*;
import lippo.hris.system.recruitment.enumeration.EmployeeRequestFormActivityStatus;
import lippo.hris.system.recruitment.repository.*;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeRequestCandidateService {

    @Autowired
    EmployeeRequestCandidateRepository employeeRequestCandidateRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    RecruitmentActivityTemplateRepository recruitmentActivityTemplateRepository;

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    public void modifyEmployeeRequestCandidate(EmployeeRequestReq employeeRequestReq,
                                               EmployeeRequestForm employeeRequestForm){
        List<EmployeeRequestCandidate> employeeRequestCandidate = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);
        List<Candidate> savedEmployeeRequestCandidate = employeeRequestCandidate.stream().map(EmployeeRequestCandidate::getCandidate).toList();
        List<Long> savedCandidate = savedEmployeeRequestCandidate.stream().map(Candidate::getId).toList();

        List<Long> added = new ArrayList<>(employeeRequestReq.getCandidate());
        added.removeAll(savedCandidate);
        List<Long> removed = new ArrayList<>(savedCandidate);
        removed.removeAll(employeeRequestReq.getCandidate());

        save(employeeRequestForm, added);
        delete(employeeRequestForm, removed);
    }

    public void save(EmployeeRequestForm employeeRequestForm, List<Long> added){
        for(Long candidateId : added){
            Candidate candidate = candidateRepository.findById(candidateId).get();
            EmployeeRequestCandidate employeeRequestCandidate = new EmployeeRequestCandidate();
            employeeRequestCandidate.setEmployeeRequestForm(employeeRequestForm);
            employeeRequestCandidate.setCandidate(candidate);
            employeeRequestCandidate = employeeRequestCandidateRepository.save(employeeRequestCandidate);

            List<RecruitmentActivityTemplate> recruitmentActivityTemplateList =
                    recruitmentActivityTemplateRepository.findByTemplate(employeeRequestForm.getRecruitmentTemplate());
            List<RecruitmentActivity> recruitmentActivityList = recruitmentActivityTemplateList.stream().map(RecruitmentActivityTemplate::getActivity).toList();

            for(RecruitmentActivity recruitmentActivity : recruitmentActivityList){
                EmployeeRequestCandidateActivity employeeRequestCandidateActivity = new EmployeeRequestCandidateActivity();
                employeeRequestCandidateActivity.setEmployeeRequestCandidate(employeeRequestCandidate);
                employeeRequestCandidateActivity.setRecruitmentActivity(recruitmentActivity);
                employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.NOT_STARTED.toString());
                employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
            }
        }
    }

    public void delete(EmployeeRequestForm employeeRequestForm, List<Long> deleted){
        for(Long candidateId : deleted){
            Candidate candidate = candidateRepository.findById(candidateId).get();
            EmployeeRequestCandidate employeeRequestCandidate = employeeRequestCandidateRepository.findByEmployeeRequestFormAndCandidate(employeeRequestForm,candidate);
            List<EmployeeRequestCandidateActivity> employeeRequestCandidateActivities = employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidate(employeeRequestCandidate);
            for(EmployeeRequestCandidateActivity employeeRequestCandidateActivity : employeeRequestCandidateActivities){
                employeeRequestCandidateActivityRepository.delete(employeeRequestCandidateActivity);
            }
            employeeRequestCandidateRepository.delete(employeeRequestCandidate);
        }
    }
}
