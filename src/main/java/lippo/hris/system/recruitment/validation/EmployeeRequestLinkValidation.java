package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.recruitment.entity.EmployeeRequestResultLink;
import lippo.hris.system.recruitment.repository.EmployeeRequestResultLinkRepository;
import lippo.hris.system.recruitment.request.EmployeeRequestEmployeeDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestLinkReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeRequestLinkValidation {

    @Autowired
    EmployeeRequestResultLinkRepository employeeRequestResultLinkRepository;

    public void inputRequired(EmployeeRequestLinkReq employeeRequestLinkReq){
        if(employeeRequestLinkReq.getStartDate() == null){
            throw new BadRequestException("Start Date is required");
        } if(employeeRequestLinkReq.getEndDate() == null){
            throw new BadRequestException("End Date is required");
        }
    }

    public void existingLink(String token){
        EmployeeRequestResultLink employeeRequestResultLink = employeeRequestResultLinkRepository.findByToken(token);
        if(LocalDate.now().isBefore(employeeRequestResultLink.getStartDate()) || LocalDate.now().isAfter(employeeRequestResultLink.getEndDate())){
            throw new ConflictException("Link has not been started / Link has already expired");
        }
    }
}
