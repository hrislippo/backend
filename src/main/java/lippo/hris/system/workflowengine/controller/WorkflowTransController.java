package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import lippo.hris.system.workflowengine.service.WorkflowTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowTransController {

    @Autowired
    WorkflowTransService workflowTransService;

    @GetMapping("/mytransaction")
    public ApiResponse getMyTransaction(Authentication authentication,
                                        @RequestParam(value = "docTypeCode", required = false) String docTypeCode,
                                        @RequestParam(value = "applyDate", required = false) LocalDate applyDate,
                                        @RequestParam(value = "transactionCode", required = false) String transactionCode,
                                        @RequestParam(value = "status", required = false) String status,
                                        Pageable pageable) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(workflowTransService.getMyTransaction(customUserDetails.getUsername(), docTypeCode, status, applyDate, transactionCode, pageable), "Get My Approval Transaction Successfully");
    }

    @GetMapping("/approvertransaction")
    public ApiResponse getApproverTransaction(Authentication authentication,
                                        @RequestParam(value = "docTypeCode", required = false) String docTypeCode,
                                        @RequestParam(value = "applyDate", required = false) LocalDate applyDate,
                                        @RequestParam(value = "transactionCode", required = false) String transactionCode,
                                        @RequestParam(value = "status", required = false) String status,
                                        Pageable pageable) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(workflowTransService.getApproverTransaction(customUserDetails.getUsername(), docTypeCode, status, applyDate, transactionCode, pageable), "Get Approver Transaction Successfully");
    }

    @GetMapping("/approver-detail")
    public ApiResponse getApproverDetail (@Param("id") Long id){
        return ApiResponse.ok(workflowTransService.getApproverDetail(id), "Get Workflow Approver Detail Successfully");
    }

    @GetMapping("/transaction-detail")
    public ApiResponse getTransactionDetail(@Param("id") Long id){
        return ApiResponse.ok(workflowTransService.getTransactionDetail(id), "Get Workflow Transaction Detail Successfully");
    }

    @GetMapping("/employee")
    public ApiResponse getEmployeeDetail(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(workflowTransService.getEmployeeDetail(customUserDetails.getUsername()),
                "Get Workflow Employee Detail Successfully");
    }
}
