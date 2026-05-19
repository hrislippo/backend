package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.recruitment.repository.EmployeeRequestCandidateActivityRepository;
import lippo.hris.system.recruitment.response.FileResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    @Autowired
    CandidateRepository candidateRepository;

    public String getFileActivity(Long id){
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();
        return "https://drive.google.com/file/d/"
                + employeeRequestCandidateActivity.getFileId()
                + "/preview";
    }

    public String getFileCV(Long id){
        Candidate candidate = candidateRepository.findById(id).get();
        return "https://drive.google.com/file/d/"
                + candidate.getFileId()
                + "/preview";
    }

    public String downloadCV(Long id){
        Candidate candidate = candidateRepository.findById(id).get();
        return "https://drive.google.com/uc?export=download&id="
                + candidate.getFileId();
    }
}
