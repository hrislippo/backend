package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import lippo.hris.system.workflowengine.repository.WorkflowDocTypeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempRepository;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WorkflowGroupTempService {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

    @Autowired
    WorkflowGroupTempRepository workflowGroupTempRepository;

    public void addGroupTemplate(GroupTempRequest groupTempRequest) {
        WorkflowDocType workflowDocType = workflowDocTypeRepository.findByCode(groupTempRequest.getDocTypeCode());

        WorkflowGroupTemp workflowGroupTemp = new WorkflowGroupTemp();
        workflowGroupTemp.setCode(groupTempRequest.getGroupTempCode());
        workflowGroupTemp.setName(groupTempRequest.getGroupTempName());
        workflowGroupTemp.setWorkflowDocType(workflowDocType);

        WorkflowGroupTemp latestWorkflowGroupTemp = workflowGroupTempRepository.latestByDocType(workflowDocType.getId());
        workflowGroupTemp.setSequence(latestWorkflowGroupTemp == null ? 1 : latestWorkflowGroupTemp.getSequence() + 1);
        workflowGroupTemp.setActive(true);
        workflowGroupTempRepository.save(workflowGroupTemp);
    }
}
