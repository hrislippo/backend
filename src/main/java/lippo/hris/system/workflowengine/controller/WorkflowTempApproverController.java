package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.WorkflowTempApproverRequest;
import lippo.hris.system.workflowengine.service.WorkflowTempApproverService;
import lippo.hris.system.workflowengine.validation.WorkflowTempApproverValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowTempApproverController {

    @Autowired
    WorkflowTempApproverValidation workflowTempApproverValidation;

    @Autowired
    WorkflowTempApproverService workflowTempApproverService;

    @PutMapping("/tempapprover")
    public ApiResponse modifyTemplateApprover(@RequestBody WorkflowTempApproverRequest workflowTempApproverRequest) {
        workflowTempApproverValidation.approverTemplateRequired(workflowTempApproverRequest);
        workflowTempApproverService.modifyWorkflowTemplateApprover(workflowTempApproverRequest);
        return ApiResponse.ok(null, "Modify Workflow Template Approver Successfully");
    }

    @GetMapping("/tempapprover")
    public ApiResponse getTemplateApprover(@RequestParam("tempId") Long tempId) {
        return ApiResponse.ok(workflowTempApproverService.getWorkflowTemplateApprover(tempId), "Get Workflow Template Approver Successfully");
    }
}
