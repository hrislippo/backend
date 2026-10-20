package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.repository.WorkflowApproverTempRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.WorkflowApproverTempRequest;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowApproverTempValidation {

    @Autowired
    WorkflowApproverTempRepository workflowApproverTempRepository;

    public void codeNameRequiredAndExists(WorkflowApproverTempRequest workflowApproverTempRequest) {
        if(workflowApproverTempRequest.getTempCode() == null) {
            throw new BadRequestException("Code is required");
        }
        if(workflowApproverTempRequest.getTempName() == null) {
            throw new BadRequestException("Name is required");
        }
        if(workflowApproverTempRequest.getId() == null && workflowApproverTempRepository.findByCode(workflowApproverTempRequest.getTempCode()) != null) {
            throw new ConflictException("Code already exists");
        }
        if(workflowApproverTempRequest.getId() == null && workflowApproverTempRepository.findByName(workflowApproverTempRequest.getTempName()) != null) {
            throw new ConflictException("Name already exists");
        }
    }
}
