package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.workflowengine.entity.WorkflowTransApprover;
import lippo.hris.system.workflowengine.repository.WorkflowTransApproverRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowTransValidation {

    @Autowired
    WorkflowTransApproverRepository workflowTransApproverRepository;

    public void validApprover(String username, Long approverId){
        WorkflowTransApprover workflowTransApprover = workflowTransApproverRepository.findById(approverId).get();
        if(!workflowTransApprover.getApprover().getUsername().equals(username)){
            throw new BadRequestException("Approver Invalid");
        }
    }

    public void reasonRequired(String action, String description){
        if(action.equals("REJECTED") && (description == null || description.isEmpty())){
            throw new BadRequestException("Reason Required");
        }
    }
}
