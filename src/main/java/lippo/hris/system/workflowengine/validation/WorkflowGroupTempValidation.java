package lippo.hris.system.workflowengine.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import lippo.hris.system.workflowengine.repository.WorkflowDocTypeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempRepository;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class WorkflowGroupTempValidation {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

    @Autowired
    WorkflowGroupTempRepository workflowGroupTempRepository;

    public void codeNameRequiredAndExists(@RequestBody GroupTempRequest groupTempRequest) {
        if(groupTempRequest.getGroupTempCode() == null) {
            throw new BadRequestException("Code is required");
        }
        if(groupTempRequest.getGroupTempName() == null) {
            throw new BadRequestException("Name is required");
        }

        if(groupTempRequest.getId() == null && workflowGroupTempRepository.findByCode(groupTempRequest.getGroupTempCode()) != null) {
            throw new ConflictException("Code already exists");
        }
        if(groupTempRequest.getId() == null && workflowGroupTempRepository.findByName(groupTempRequest.getGroupTempName()) != null) {
            throw new ConflictException("Name already exists");
        }
    }

    public void documentTypeRequired(@RequestBody GroupTempRequest groupTempRequest) {
        if(groupTempRequest.getDocTypeCode() == null) {
            throw new BadRequestException("Document Type is required");
        }
    }

    public void duplicateWorkflowGroup(GroupTempRequest groupTempRequest) {
        WorkflowDocType workflowDocType = workflowDocTypeRepository.findByCode(groupTempRequest.getDocTypeCode());
        WorkflowGroupTemp workflowGroupTemp = workflowGroupTempRepository.findByDocTypeAndCriteria(workflowDocType.getId(),
                groupTempRequest.getFlagCompany(), groupTempRequest.getFlagOrganization(), groupTempRequest.getFlagJobLevel(),
                groupTempRequest.getFlagJobTitle(), groupTempRequest.getFlagLocation(), groupTempRequest.getFlagPosition(),
                groupTempRequest.getFlagEmployee(), groupTempRequest.getId());
        if(workflowGroupTemp != null) {
            throw new BadRequestException("Workflow group already exists by same criteria");
        }
    }
}
