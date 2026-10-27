package lippo.hris.system.workflowengine.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.workflowengine.repository.WorkflowStatusRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTransRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowTransService {

    @Autowired
    WorkflowStatusRepository workflowStatusRepository;

    @Autowired
    WorkflowTransRepository workflowTransRepository;

    @Autowired
    WorkflowTransApproverService workflowTransApproverService;

    @Autowired
    WorkflowTempService workflowTempService;

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;
}
