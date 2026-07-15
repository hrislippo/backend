package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.UnauthorizedException;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.repository.EmployeeRequestCandidateActivityRepository;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import lippo.hris.system.recruitment.request.ScheduleEmployeeReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmployeeRequestValidation {

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    public void employeeRequestActionRequired(String result){
        if(result == null || result.trim().isEmpty()) {
            throw new BadRequestException("Result Action Required");
        }
    }

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
        if(employeeRequestReq.getStartDate() == null){
            throw new BadRequestException("Start Date is Required");
        }
        if(employeeRequestReq.getExpDate() == null){
            throw new BadRequestException("Exp Date is Required");
        }
        if(employeeRequestReq.getRequestNumber() == null){
            throw new BadRequestException("Request Amount is Required");
        }
        if(employeeRequestReq.getId() != null && employeeRequestReq.getPic().stream().filter(e -> e != null).toList().isEmpty()){
            throw new BadRequestException("PICs are Required");
        }
        if(employeeRequestReq.getHiringName() == null || employeeRequestReq.getHiringName().trim().isEmpty()) {
            throw new BadRequestException("Hiring Manager Name is Required");
        }
        if(employeeRequestReq.getHiringPositionName() == null || employeeRequestReq.getHiringPositionName().trim().isEmpty()) {
            throw new BadRequestException("Hiring Manager Position Name is Required");
        }
        if(employeeRequestReq.getReportTo() == null || employeeRequestReq.getReportTo().trim().isEmpty()) {
            throw new BadRequestException("Reporting To is Required");
        }
        if(employeeRequestReq.getEmploymentType() == null) {
            throw new BadRequestException("Employment Type is Required");
        }
        if(employeeRequestReq.getRequisitionType() == null || employeeRequestReq.getRequisitionType().trim().isEmpty()) {
            throw new BadRequestException("Requisition Type is Required");
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

    public void eligibleRecruitmentHead(List<String> roles){
        if(!roles.contains("ROLE_TA_HEAD")){
            throw new UnauthorizedException("Not Eligible to Apply This Action (Recruitment Head)");
        }
    }

    public void requiredEmailParam(String emailBody){
        if(emailBody.contains("[") || emailBody.contains("]")){
            throw new BadRequestException("Required Input Email Parameter");
        }
    }

    public void employeeRequestScheduleRequired(ScheduleEmployeeReq scheduleEmployeeReq){
        if(scheduleEmployeeReq.getScheduleTime() == null){
            throw new BadRequestException("Schedule Time is Required");
        }

        EmployeeRequestCandidateActivity employeeRequestCandidateActivity =
                employeeRequestCandidateActivityRepository.findById(scheduleEmployeeReq.getId()).get();
        if(employeeRequestCandidateActivity.getRecruitmentActivity().getFlagInterview()){
            if(scheduleEmployeeReq.getInterviewerName() == null || scheduleEmployeeReq.getInterviewerName().trim().isEmpty()){
                throw new BadRequestException("Interviewer Name is Required");
            }
            if(scheduleEmployeeReq.getInterviewerPosition() == null || scheduleEmployeeReq.getInterviewerPosition().trim().isEmpty()){
                throw new BadRequestException("Interviewer Position is Required");
            }

            if(scheduleEmployeeReq.getInterviewType().equalsIgnoreCase("offline")){
                if(scheduleEmployeeReq.getVenueId() == null){
                    throw new BadRequestException("Venue is Required (OFFLINE)");
                }
            } else if(scheduleEmployeeReq.getInterviewType().equalsIgnoreCase("online")){
                if(scheduleEmployeeReq.getLinkInterview() == null || scheduleEmployeeReq.getLinkInterview().trim().isEmpty()){
                    throw new BadRequestException("Link Interview is Required (ONLINE)");
                }
            }
        }
    }

    public void employeeRequestReasonRequired(EmployeeRequestReq employeeRequestReq){
        if(employeeRequestReq.getReason() == null || employeeRequestReq.getReason().trim().length() < 50){
            throw new BadRequestException("Reason is Required (50 characters long)");
        }
    }

    public void employeeRequestResumeTypeRequired(EmployeeRequestReq employeeRequestReq){
        if(employeeRequestReq.getResumeType() == null || employeeRequestReq.getResumeType().trim().isEmpty()){
            throw new BadRequestException("Resume Type is Required");
        }
    }
}
