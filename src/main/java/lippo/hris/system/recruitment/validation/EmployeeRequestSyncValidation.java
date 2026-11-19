package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.EmployeeRequestEmployeeDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestPersonalDataReq;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRequestSyncValidation {

    public void inputRequired(EmployeeRequestEmployeeDataReq employeeRequestEmployeeDataReq){
        if(employeeRequestEmployeeDataReq.getStartDate() == null){
            throw new BadRequestException("Start Date is required");
        } if(employeeRequestEmployeeDataReq.getSignDate() == null){
            throw new BadRequestException("Sign Date is required");
        } if(employeeRequestEmployeeDataReq.getJoinDate() == null){
            throw new BadRequestException("Join Date is required");
        } if(employeeRequestEmployeeDataReq.getCompCode() == null){
            throw new BadRequestException("Company is required");
        } if(employeeRequestEmployeeDataReq.getOrgCode() == null){
            throw new BadRequestException("Organization is required");
        } if(employeeRequestEmployeeDataReq.getJobLvlCode() == null){
            throw new BadRequestException("Job Level is required");
        } if(employeeRequestEmployeeDataReq.getJobTtlCode() == null){
            throw new BadRequestException("Job Title is required");
        } if(employeeRequestEmployeeDataReq.getLocCode() == null){
            throw new BadRequestException("Location is required");
        } if(employeeRequestEmployeeDataReq.getPosCode() == null){
            throw new BadRequestException("Position is required");
        } if(employeeRequestEmployeeDataReq.getCostCenterCode() == null){
            throw new BadRequestException("Cost Center is required");
        } if(employeeRequestEmployeeDataReq.getEmploymentType() == null){
            throw new BadRequestException("Employment Type is required");
        } if(employeeRequestEmployeeDataReq.getLocRecruitCode() == null){
            throw new BadRequestException("Location Recruit is required");
        }
    }

    public void inputRequired(EmployeeRequestPersonalDataReq employeeRequestPersonalDataReq){
        if(employeeRequestPersonalDataReq.getName() == null || employeeRequestPersonalDataReq.getName().isEmpty()){
            throw new BadRequestException("Name is required");
        } if(employeeRequestPersonalDataReq.getKtpNumber() == null || employeeRequestPersonalDataReq.getKtpNumber().isEmpty()){
            throw new BadRequestException("KTP Number is required");
        } if(employeeRequestPersonalDataReq.getBirthDate() == null){
            throw new BadRequestException("Birth Date is required");
        } if(employeeRequestPersonalDataReq.getGender() == null){
            throw new BadRequestException("Gender is required");
        } if(employeeRequestPersonalDataReq.getMaritalStatus() == null){
            throw new BadRequestException("Marital Status is required");
        } if(employeeRequestPersonalDataReq.getReligion() == null){
            throw new BadRequestException("Religion is required");
        } if(employeeRequestPersonalDataReq.getAddress() == null || employeeRequestPersonalDataReq.getAddress().isEmpty()){
            throw new BadRequestException("Address is required");
        } if(employeeRequestPersonalDataReq.getPhoneNumber() == null || employeeRequestPersonalDataReq.getPhoneNumber().isEmpty()){
            throw new BadRequestException("Phone Number is required");
        } if(employeeRequestPersonalDataReq.getEmail() == null || employeeRequestPersonalDataReq.getEmail().isEmpty()){
            throw new BadRequestException("Email is required");
        }
    }
}
