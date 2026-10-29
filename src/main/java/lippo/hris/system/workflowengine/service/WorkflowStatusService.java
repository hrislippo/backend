package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowStatus;
import lippo.hris.system.workflowengine.repository.WorkflowStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowStatusService {

    @Autowired
    WorkflowStatusRepository workflowStatusRepository;

    public List<WorkflowStatus> getAllWorkflowStatus(){
        return workflowStatusRepository.findAll();
    }
}
