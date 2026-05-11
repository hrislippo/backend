package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeRequestValidation {

    public void employeeRequestRequired(EmployeeRequestReq employeeRequestReq) {
        if(employeeRequestReq.getName() == null || employeeRequestReq.getName().trim().isEmpty()) {
            throw new BadRequestException("Name is Required");
        }
        if(employeeRequestReq.getBusinessUnitId() == null){
            throw new BadRequestException("Business Unit is Required");
        }
        if(employeeRequestReq.getHrbpId() == null){
            throw new BadRequestException("HRBP is Required");
        }
        if(employeeRequestReq.getTemplate() == null){
            throw new BadRequestException("Template Activity is Required");
        }
        if(employeeRequestReq.getTemplateLevel() == null){
            throw new BadRequestException("Template Level is Required");
        }
        if(employeeRequestReq.getExpDate() == null){
            throw new BadRequestException("Exp Date is Required");
        }
        if(employeeRequestReq.getRequestNumber() == null){
            throw new BadRequestException("Request Amount is Required");
        }
        if(employeeRequestReq.getPic().stream().filter(e -> e != null).toList().isEmpty()){
            throw new BadRequestException("PICs are Required");
        }
    }

    public void checkEmployeeRequestValue(EmployeeRequestReq employeeRequestReq) {
        if(employeeRequestReq.getExpDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Expiry Date must be Future Date");
        }
        if(employeeRequestReq.getRequestNumber() < 1){
            throw new BadRequestException("Recruitment Number must be greater than 0");
        }
    }
}
