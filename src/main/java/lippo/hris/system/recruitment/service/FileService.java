package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateDocument;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.repository.CandidateDocumentRepository;
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
    CandidateDocumentRepository candidateDocumentRepository;

    @Autowired
    CandidateRepository candidateRepository;

    public FileResp getFileActivity(Long id){
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();

        FileResp fileResp = new FileResp();
        fileResp.setFileName(employeeRequestCandidateActivity.getFileName());
        fileResp.setContentType(employeeRequestCandidateActivity.getFileType());
        fileResp.setData(employeeRequestCandidateActivity.getAttachment());
        return fileResp;
    }

    public FileResp getFileCV(Long id){
        Candidate candidate = candidateRepository.findById(id).get();
        CandidateDocument candidateDocument = candidateDocumentRepository.findByCandidate(candidate);

        FileResp fileResp = new FileResp();
        fileResp.setFileName(candidateDocument.getDocumentName());
        fileResp.setContentType(candidateDocument.getDocumentType());
        fileResp.setData(candidateDocument.getDocument());
        return fileResp;
    }
}
