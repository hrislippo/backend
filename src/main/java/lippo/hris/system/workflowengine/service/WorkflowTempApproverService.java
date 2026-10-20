package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprover;
import lippo.hris.system.workflowengine.repository.WorkflowApproverTempRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempApproverRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.WorkflowTempApproverRequest;
import lippo.hris.system.workflowengine.request.WorkflowTempApproverRequestDt;
import lippo.hris.system.workflowengine.response.WorkflowTempApproverResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WorkflowTempApproverService {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    @Autowired
    WorkflowApproverTempRepository workflowApproverTempRepository;

    @Autowired
    WorkflowTempApproverRepository workflowTempApproverRepository;

    public void modifyWorkflowTemplateApprover(WorkflowTempApproverRequest workflowTempApproverRequest){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(workflowTempApproverRequest.getTemplateId()).get();
        List<WorkflowTempApprover> workflowTempApproverList = workflowTempApproverRepository.findByWorkflowTemp(workflowTemp);
        for(WorkflowTempApprover workflowTempApprover : workflowTempApproverList){
            workflowTempApproverRepository.delete(workflowTempApprover);
        }

        for(WorkflowTempApproverRequestDt workflowTempApproverRequestDt : workflowTempApproverRequest.getTemplateApproverList()){
            WorkflowTempApprover workflowTempApprover = new WorkflowTempApprover();
            workflowTempApprover.setWorkflowTemp(workflowTemp);
            workflowTempApprover.setWorkflowApproverTemp(workflowApproverTempRepository.findById(workflowTempApproverRequestDt.getApproverTemplateId()).get());
            workflowTempApprover.setLevel(workflowTempApproverRequestDt.getLevel());
            workflowTempApprover.setSequence(workflowTempApproverRequestDt.getSequence());
            workflowTempApproverRepository.save(workflowTempApprover);
        }
    }

    public List<WorkflowTempApproverResponse> getWorkflowTemplateApprover(Long tempId){
        List<WorkflowTempApproverResponse> workflowTempApproverResponseList = new ArrayList<>();
        Integer maxLevel = workflowTempApproverRepository.findMaxLevel(tempId);
        if(maxLevel == null){
            return new ArrayList<>();
        }

        for(int i = 1; i <= maxLevel; i++){
            WorkflowTempApproverResponse workflowTempApproverResponse = new WorkflowTempApproverResponse();
            List<WorkflowApproverTemp> workflowApproverTempList = workflowApproverTempRepository.findByTemplateAndLevel(tempId, i);
            workflowTempApproverResponse.setSequence(workflowApproverTempList);
            workflowTempApproverResponseList.add(workflowTempApproverResponse);
        }
        return workflowTempApproverResponseList;
    }
}
