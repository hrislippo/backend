package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.service.WorkflowDocTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowDocTypeController {

    @Autowired
    WorkflowDocTypeService workflowDocTypeService;

    @GetMapping("/doctype")
    public ApiResponse getDocumentType() {
        return ApiResponse.ok(workflowDocTypeService.getAllWorkflowDocType(), "Get Workflow Document Type Successfully");
    }
}
