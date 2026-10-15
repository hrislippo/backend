package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lippo.hris.system.workflowengine.service.WorkflowTempService;
import lippo.hris.system.workflowengine.validation.WorkflowTempValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowTempController {

    @Autowired
    WorkflowTempValidation workflowTempValidation;

    @Autowired
    WorkflowTempService workflowTempService;

    @PostMapping("/temp")
    public ApiResponse addTemplate(@RequestBody WorkflowTempRequest workflowTempRequest) {
        workflowTempValidation.codeNameRequiredAndExists(workflowTempRequest);
        workflowTempService.addWorkflowTemplate(workflowTempRequest);
        return ApiResponse.ok(null, "Add Workflow Template Successfully");
    }

    @PutMapping("/temp")
    public ApiResponse modifyTemplate(@RequestBody WorkflowTempRequest workflowTempRequest) {
        workflowTempValidation.codeNameRequiredAndExists(workflowTempRequest);
        workflowTempService.modifyWorkflowTemplate(workflowTempRequest);
        return ApiResponse.ok(null, "Modify Workflow Template Successfully");
    }

    @GetMapping("/temp")
    public ApiResponse getTemplate(@RequestParam(value = "codeSearch", required = false) String codeSearch,
                                        @RequestParam(value = "nameSearch", required = false) String nameSearch,
                                        @RequestParam(value = "activeSearch", required = false) Boolean activeSearch,
                                        Pageable pageable) {
        return ApiResponse.ok(workflowTempService.getTemplate(codeSearch, nameSearch, activeSearch, pageable),
                "Get Workflow Template Successfully");
    }

    @GetMapping("/temp-list")
    public ApiResponse getTemplateList(){
        return ApiResponse.ok(workflowTempService.getTemplateList(), "Get Workflow Template List Successfully");
    }

    @GetMapping("/temp-detail")
    public ApiResponse getTemplateDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(workflowTempService.getTemplateDetail(id), "Get Workflow Template Detail Successfully");
    }
}
