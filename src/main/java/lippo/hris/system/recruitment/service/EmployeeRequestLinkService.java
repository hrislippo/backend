package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.EmployeeRequestResult;
import lippo.hris.system.recruitment.entity.EmployeeRequestResultLink;
import lippo.hris.system.recruitment.repository.EmployeeRequestResultLinkRepository;
import lippo.hris.system.recruitment.repository.EmployeeRequestResultRepository;
import lippo.hris.system.recruitment.request.EmployeeRequestLinkReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@Transactional
public class EmployeeRequestLinkService {

    @Autowired
    EmployeeRequestResultRepository employeeRequestResultRepository;

    @Autowired
    EmployeeRequestResultLinkRepository employeeRequestResultLinkRepository;

    public String generateLink(EmployeeRequestLinkReq employeeRequestLinkReq) {
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(employeeRequestLinkReq.getResultId()).get();
        EmployeeRequestResultLink employeeRequestResultLink = employeeRequestResultLinkRepository.findByEmployeeRequestResult(employeeRequestResult);
        if(employeeRequestResultLink == null){
            employeeRequestResultLink = new EmployeeRequestResultLink();
            employeeRequestResultLink.setEmployeeRequestResult(employeeRequestResult);
        }

        if(employeeRequestResultLink.getToken() == null){
            employeeRequestResultLink.setToken(generateToken());
        }
        employeeRequestResultLink.setStartDate(employeeRequestLinkReq.getStartDate());
        employeeRequestResultLink.setEndDate(employeeRequestLinkReq.getEndDate());
        employeeRequestResultLinkRepository.save(employeeRequestResultLink);

        return employeeRequestResultLink.getToken();
    }

    public EmployeeRequestResultLink getLink(Long id) {
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(id).get();
        return employeeRequestResultLinkRepository.findByEmployeeRequestResult(employeeRequestResult);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }
}
