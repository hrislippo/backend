package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import lippo.hris.system.workflowengine.service.WorkflowGroupTempService;
import lippo.hris.system.workflowengine.validation.WorkflowGroupTempValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowGroupTempController {

    @Autowired
    WorkflowGroupTempValidation workflowGroupTempValidation;

    @Autowired
    WorkflowGroupTempService workflowGroupTempService;

    @PostMapping("/grouptemp")
    public ApiResponse addGroupTemplate(@RequestBody GroupTempRequest groupTempRequest) {
        workflowGroupTempValidation.codeNameRequiredAndExists(groupTempRequest);
        workflowGroupTempValidation.documentTypeRequired(groupTempRequest);
        workflowGroupTempService.addGroupTemplate(groupTempRequest);
        return ApiResponse.ok(null, "Add Workflow Group Template Successfully");
    }
}
