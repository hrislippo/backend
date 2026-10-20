package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.WorkflowApproverTempRequest;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lippo.hris.system.workflowengine.service.WorkflowApproverTempService;
import lippo.hris.system.workflowengine.validation.WorkflowApproverTempValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowApproverTempController {

    @Autowired
    WorkflowApproverTempValidation workflowApproverTempValidation;

    @Autowired
    WorkflowApproverTempService workflowApproverTempService;

    @PostMapping("/approvertemp")
    public ApiResponse addApproverTemplate(@RequestBody WorkflowApproverTempRequest workflowApproverTempRequest) {
        workflowApproverTempValidation.codeNameRequiredAndExists(workflowApproverTempRequest);
        workflowApproverTempService.addWorkflowApproverTemplate(workflowApproverTempRequest);
        return ApiResponse.ok(null, "Add Workflow Approver Template Successfully");
    }

    @PutMapping("/approvertemp")
    public ApiResponse modifyApproverTemplate(@RequestBody WorkflowApproverTempRequest workflowApproverTempRequest) {
        workflowApproverTempValidation.codeNameRequiredAndExists(workflowApproverTempRequest);
        workflowApproverTempService.modifyWorkflowApproverTemplate(workflowApproverTempRequest);
        return ApiResponse.ok(null, "Modify Workflow Approver Template Successfully");
    }

    @GetMapping("/approvertemp")
    public ApiResponse getApproverTemplate(@RequestParam(value = "codeSearch", required = false) String codeSearch,
                                   @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                   @RequestParam(value = "activeSearch", required = false) Boolean activeSearch,
                                   Pageable pageable) {
        return ApiResponse.ok(workflowApproverTempService.getApproverTemplate(codeSearch, nameSearch, activeSearch, pageable),
                "Get Workflow Approver Template Successfully");
    }

    @GetMapping("/approvertemp-detail")
    public ApiResponse getApproverTemplateDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(workflowApproverTempService.getApproverTemplateDetail(id), "Get Workflow Approver Template Detail Successfully");
    }

    @GetMapping("/approvertemp-list")
    public ApiResponse getApproverTemplateList() {
        return ApiResponse.ok(workflowApproverTempService.getApproverTemplateList(), "Get Workflow Approver Template List Successfully");
    }
}
