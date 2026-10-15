package lippo.hris.system.workflowengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.workflowengine.request.TempApproveeRequest;
import lippo.hris.system.workflowengine.service.WorkflowTempApproveeService;
import lippo.hris.system.workflowengine.validation.WorkflowTempApproveeValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflow")
public class WorkflowTempApproveeController {

    @Autowired
    WorkflowTempApproveeService workflowTempApproveeService;

    @Autowired
    WorkflowTempApproveeValidation workflowTempApproveeValidation;

    @PutMapping("/tempapprovee")
    public ApiResponse modifyTemplateApprovee(@RequestBody TempApproveeRequest tempApproveeRequest) {
        workflowTempApproveeValidation.templateRequired(tempApproveeRequest);
        workflowTempApproveeValidation.detailRequired(tempApproveeRequest);
        workflowTempApproveeService.modifyTemplateApprovee(tempApproveeRequest);
        return ApiResponse.ok(null, "Modify Approvee Template Successfully");
    }
}
