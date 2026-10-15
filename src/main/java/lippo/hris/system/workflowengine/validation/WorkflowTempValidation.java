package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class WorkflowTempValidation {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    public void codeNameRequiredAndExists(WorkflowTempRequest workflowTempRequest) {
        if(workflowTempRequest.getTempCode() == null) {
            throw new BadRequestException("Code is required");
        }
        if(workflowTempRequest.getTempName() == null) {
            throw new BadRequestException("Name is required");
        }
        if(workflowTempRequest.getId() == null && workflowTempRepository.findByCode(workflowTempRequest.getTempCode()) != null) {
            throw new ConflictException("Code already exists");
        }
        if(workflowTempRequest.getId() == null && workflowTempRepository.findByName(workflowTempRequest.getTempName()) != null) {
            throw new ConflictException("Name already exists");
        }
    }
}
