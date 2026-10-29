package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.service.WorkflowStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowStatusController {

    @Autowired
    WorkflowStatusService workflowStatusService;

    @GetMapping("/status")
    public ApiResponse getStatus() {
        return ApiResponse.ok(workflowStatusService.getAllWorkflowStatus(), "Get Workflow Status Successfully");
    }
}
