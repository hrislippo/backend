package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import lippo.hris.system.workflowengine.service.WorkflowGroupTempService;
import lippo.hris.system.workflowengine.validation.WorkflowGroupTempValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
        workflowGroupTempValidation.duplicateWorkflowGroup(groupTempRequest);
        workflowGroupTempService.addGroupTemplate(groupTempRequest);
        return ApiResponse.ok(null, "Add Workflow Group Template Successfully");
    }

    @PutMapping("/grouptemp")
    public ApiResponse modifyGroupTemplate(@RequestBody GroupTempRequest groupTempRequest) {
        workflowGroupTempValidation.codeNameRequiredAndExists(groupTempRequest);
        workflowGroupTempValidation.documentTypeRequired(groupTempRequest);
        workflowGroupTempValidation.duplicateWorkflowGroup(groupTempRequest);
        workflowGroupTempService.modifyGroupTemplate(groupTempRequest);
        return ApiResponse.ok(null, "Modify Workflow Group Template Successfully");
    }

    @GetMapping("/grouptemp")
    public ApiResponse getGroupTemplate(@RequestParam(value = "codeSearch", required = false) String codeSearch,
                                        @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                        @RequestParam(value = "docTypeSearch", required = false) String docTypeSearch,
                                        @RequestParam(value = "activeSearch", required = false) Boolean activeSearch,
                                        Pageable pageable) {
        return ApiResponse.ok(workflowGroupTempService.getGroupTemplate(codeSearch, nameSearch, docTypeSearch, activeSearch, pageable),
                "Get Workflow Group Template Successfully");
    }

    @GetMapping("/grouptemp-detail")
    public ApiResponse getGroupTemplateDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(workflowGroupTempService.getGroupTemplateDetail(id),
                "Get Workflow Group Template Detail Successfully");
    }
}
