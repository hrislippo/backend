package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.repository.EmployeeRequestCandidateActivityRepository;
import lippo.hris.system.recruitment.response.FileResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    public FileResp getFileActivity(Long id){
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();

        FileResp fileResp = new FileResp();
        fileResp.setFileName(employeeRequestCandidateActivity.getFileName());
        fileResp.setContentType(employeeRequestCandidateActivity.getFileType());
        fileResp.setData(employeeRequestCandidateActivity.getAttachment());
        return fileResp;
    }
}
