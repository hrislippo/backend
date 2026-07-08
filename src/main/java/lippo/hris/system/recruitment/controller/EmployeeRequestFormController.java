package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.*;
import lippo.hris.system.recruitment.service.EmployeeRequestFormService;
import lippo.hris.system.recruitment.validation.EmployeeRequestValidation;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment")
public class EmployeeRequestFormController {

    @Autowired
    EmployeeRequestFormService employeeRequestFormService;

    @Autowired
    EmployeeRequestValidation employeeRequestValidation;

    @PostMapping("/erf")
    public ApiResponse addEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        employeeRequestValidation.employeeRequestRequired(employeeRequestReq);
        employeeRequestValidation.checkEmployeeRequestValue(employeeRequestReq);
        employeeRequestFormService.addEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Saved");
    }

    @PostMapping("/erf-email")
    public ApiResponse emailEmployeeRequest(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestParam(value = "emailTo") List<String> emailTo,
                                            @RequestParam(value = "emailCc", required = false) List<String> emailCc,
                                            @RequestParam(value = "emailBcc", required = false) List<String> emailBcc,
                                            @RequestParam(value = "emailSubject") String emailSubject,
                                            @RequestParam(value = "emailBody") String emailBody,
                                            Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        employeeRequestValidation.requiredEmailParam(emailBody);
        employeeRequestFormService.emailEmployeeRequest(id, emailTo, emailCc, emailBcc, emailSubject, emailBody, file, customUserDetails.getUsername());
        return ApiResponse.ok(null, "Email Successfully");
    }

    @PostMapping("/erf-action")
    public ApiResponse actionEmployeeRequest(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @RequestParam("id") Long id,
                                             @RequestParam("result") String result,
                                             @RequestParam(value = "notes", required = false) String notes) {
        employeeRequestValidation.employeeRequestActionRequired(result);
        return ApiResponse.ok(employeeRequestFormService.actionEmployeeRequest(id, file, result, notes), "Employee Request Progressed");
    }

    @PostMapping("/erf-revert")
    public ApiResponse revertEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        employeeRequestFormService.revertEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Reverted");
    }

    @PostMapping("/erf-add-interview")
    public ApiResponse addInterviewEmployeeRequest(@RequestBody AddInterviewReq addInterviewReq) {
        employeeRequestFormService.addInterview(addInterviewReq);
        return ApiResponse.ok(null, "Interview Added Successfully");
    }

    @PostMapping("/erf-process")
    public ApiResponse processEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        employeeRequestValidation.employeeRequestRequired(employeeRequestReq);
        employeeRequestValidation.checkEmployeeRequestValue(employeeRequestReq);
        employeeRequestFormService.modifyEmployeeRequest(employeeRequestReq);
        employeeRequestFormService.processEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Processed");
    }

    @PutMapping("/erf")
    public ApiResponse modifyEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq,
                                             Authentication authentication) {
        employeeRequestValidation.employeeRequestRequired(employeeRequestReq);
        employeeRequestValidation.checkEmployeeRequestValue(employeeRequestReq);
        employeeRequestFormService.modifyEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Modified");
    }

    @PutMapping("/erf-schedule")
    public ApiResponse scheduleEmployeeRequest(@RequestBody ScheduleEmployeeReq scheduleEmployeeReq) {
        employeeRequestValidation.employeeRequestScheduleRequired(scheduleEmployeeReq);
        employeeRequestFormService.scheduleEmployeeRequest(scheduleEmployeeReq);
        return ApiResponse.ok(null, "Employee Request Progressed");
    }

    @PutMapping("/erf-schedule-mass")
    public ApiResponse scheduleEmployeeRequestMass(@RequestBody ScheduleMassEmployeeReq scheduleMassEmployeeReq) {
        employeeRequestFormService.scheduleEmployeeRequest(scheduleMassEmployeeReq);
        return ApiResponse.ok(null, "Activity Scheduled");
    }

    @PutMapping("/erf-interview")
    public ApiResponse interviewEmployeeRequest(@RequestBody EmployeeRequestInterviewReq employeeRequestInterviewReq) {
        employeeRequestFormService.interviewEmployeeRequest(employeeRequestInterviewReq);
        return ApiResponse.ok(null, "Employee Request Progressed");
    }

    @PutMapping("/erf-cancel")
    public ApiResponse cancelEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq,
                                             Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        employeeRequestValidation.eligibleRecruitmentHead(customUserDetails.getRoles());
        employeeRequestFormService.cancelEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Cancelled");
    }

    @PutMapping("/erf-hold")
    public ApiResponse holdEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        employeeRequestFormService.holdEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Held");
    }

    @PutMapping("/erf-resume")
    public ApiResponse resumeEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        employeeRequestFormService.resumeEmployeeRequest(employeeRequestReq);
        return ApiResponse.ok(null, "Employee Request Resumed");
    }

    @GetMapping("/erf")
    public ApiResponse getEmployeeRequest(@RequestParam(value = "code", required = false) String code,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "buName", required = false) String buName,
                                          @RequestParam(value = "hrbpName", required = false) String hrbpName,
                                          @RequestParam(value = "pic", required = false) String pic,
                                          Pageable pageable, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequest(code, name, buName, hrbpName, customUserDetails.getUsername(), pic, pageable),
                "Get Employee Request Successfully");
    }

    @GetMapping("/erf-candidate")
    public ApiResponse getEmployeeRequestCandidate(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestCandidate(id), "Get Employee Request Candidate Successfully");
    }

    @GetMapping("/erf-kanban")
    public ApiResponse getEmployeeRequestKanban(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestKanban(id), "Get Employee Request Kanban Successfully");
    }

    @GetMapping("/erf-kanban-detail")
    public ApiResponse getEmployeeRequestKanbanDetail(@RequestParam(value = "id") Long id,
                                                      @RequestParam(value = "stage") String stage) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestKanbanDetail(id, stage), "Get Employee Request Kanban Detail Successfully");
    }

    @GetMapping("/erf-activity-detail")
    public ApiResponse getEmployeeRequestActivityDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestActivityDetail(id), "Get Employee Request Activity Detail Successfully");
    }

    @GetMapping("/erf-email-detail")
    public ApiResponse getEmployeeRequestEmailDetail(@RequestParam(value = "id") Long id,
                                                     Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestEmailDetail(id, customUserDetails.getUsername()), "Get Employee Request Email Detail Successfully");
    }

    @GetMapping("/erf-detail")
    public ApiResponse getEmployeeRequestDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestDetail(id), "Get Employee Request Detail Successfully");
    }

    @GetMapping("/erf-activity-schedule")
    public ApiResponse getEmployeeRequestActivitySchedule(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestActivitySchedule(id), "Get Employee Request Activity Schedule");
    }

    @GetMapping("/erf-result")
    public ApiResponse getEmployeeRequestResult(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestResult(id), "Get Employee Request Result");
    }

    @GetMapping("/erf-schedule-detail")
    public ApiResponse getEmployeeRequestSchedule(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(employeeRequestFormService.getEmployeeRequestSchedule(id), "Get Employee Request Schedule Successfully");
    }

    @GetMapping("/requisition-type")
    public ApiResponse getRequisitionType(){
        return ApiResponse.ok(employeeRequestFormService.getRequisitionType(), "Get Requisition Type Successfully");
    }

    @DeleteMapping("/erf")
    public ApiResponse deleteEmployeeRequest(@RequestParam("id") Long id) {
        employeeRequestFormService.deleteEmployeeRequest(id);
        return ApiResponse.ok(null, "Employee Request Deleted");
    }
}
