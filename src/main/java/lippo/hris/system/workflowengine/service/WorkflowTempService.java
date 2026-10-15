package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowTempService {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    public void addWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = new WorkflowTemp();
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());
        workflowTemp.setActive(true);
        workflowTempRepository.save(workflowTemp);
    }

    public void modifyWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(workflowTempRequest.getId()).get();
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());

        if(workflowTempRequest.getActive() != null){
            workflowTemp.setActive(workflowTempRequest.getActive());
        }
        workflowTempRepository.save(workflowTemp);
    }

    public Page<WorkflowTemp> getTemplate(String codeSearch, String nameSearch, Boolean activeSearch, Pageable pageable){
        return workflowTempRepository.getTemplate(codeSearch, nameSearch, activeSearch, pageable);
    }

    public List<WorkflowTemp> getTemplateList(){
        return workflowTempRepository.findAllByActiveTrue();
    }

    public WorkflowTemp getTemplateDetail(Long id){
        return workflowTempRepository.findById(id).get();
    }
}
