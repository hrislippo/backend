package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.repository.WorkflowDocTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowDocTypeService {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

    public List<WorkflowDocType> getAllWorkflowDocType() {
        return workflowDocTypeRepository.findAll();
    }
}
