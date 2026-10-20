package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.WorkflowTempApproverRequest;
import lippo.hris.system.workflowengine.request.WorkflowTempApproverRequestDt;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowTempApproverValidation {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    public void approverTemplateRequired(WorkflowTempApproverRequest workflowTempApproverRequest) {
        for(WorkflowTempApproverRequestDt workflowTempApproverRequestDt : workflowTempApproverRequest.getTemplateApproverList()){
            if(workflowTempApproverRequestDt.getApproverTemplateId() == null){
                throw new BadRequestException("Approver Template is required");
            }
        }
    }
}
