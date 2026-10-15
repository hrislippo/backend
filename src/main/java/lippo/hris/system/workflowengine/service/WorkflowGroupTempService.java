package lippo.hris.system.workflowengine.service;

import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTempDt;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.repository.WorkflowDocTypeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempDtRepository;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import lippo.hris.system.workflowengine.response.GroupTempResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class WorkflowGroupTempService {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

    @Autowired
    WorkflowGroupTempDtRepository workflowGroupTempDtRepository;

    @Autowired
    WorkflowGroupTempRepository workflowGroupTempRepository;

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    public void addGroupTemplate(GroupTempRequest groupTempRequest) {
        WorkflowDocType workflowDocType = workflowDocTypeRepository.findByCode(groupTempRequest.getDocTypeCode());

        WorkflowGroupTemp workflowGroupTemp = new WorkflowGroupTemp();
        workflowGroupTemp.setCode(groupTempRequest.getGroupTempCode());
        workflowGroupTemp.setName(groupTempRequest.getGroupTempName());
        workflowGroupTemp.setWorkflowDocType(workflowDocType);

        WorkflowGroupTemp latestWorkflowGroupTemp = workflowGroupTempRepository.latestByDocType(workflowDocType.getId());
        workflowGroupTemp.setSequence(latestWorkflowGroupTemp == null ? 1 : latestWorkflowGroupTemp.getSequence() + 1);
        workflowGroupTemp.setActive(true);

        workflowGroupTemp.setFlagCompany(groupTempRequest.getFlagCompany());
        workflowGroupTemp.setFlagOrganization(groupTempRequest.getFlagOrganization());
        workflowGroupTemp.setFlagJobLevel(groupTempRequest.getFlagJobLevel());
        workflowGroupTemp.setFlagJobTitle(groupTempRequest.getFlagJobTitle());
        workflowGroupTemp.setFlagLocation(groupTempRequest.getFlagLocation());
        workflowGroupTemp.setFlagPosition(groupTempRequest.getFlagPosition());
        workflowGroupTemp.setFlagEmployee(groupTempRequest.getFlagEmployee());

        workflowGroupTemp = workflowGroupTempRepository.save(workflowGroupTemp);

        for(Long tempId : groupTempRequest.getTemplateList()){
            WorkflowGroupTempDt workflowGroupTempDt = new WorkflowGroupTempDt();
            workflowGroupTempDt.setWorkflowGroupTemp(workflowGroupTemp);
            workflowGroupTempDt.setWorkflowTemp(workflowTempRepository.findById(tempId).get());
            workflowGroupTempDtRepository.save(workflowGroupTempDt);
        }
    }

    public void modifyGroupTemplate(GroupTempRequest groupTempRequest) {
        WorkflowGroupTemp workflowGroupTemp = workflowGroupTempRepository.findById(groupTempRequest.getId()).get();

        if(groupTempRequest.getGroupTempCode() != null){
            workflowGroupTemp.setCode(groupTempRequest.getGroupTempCode());
        }
        if(groupTempRequest.getGroupTempName() != null){
            workflowGroupTemp.setName(groupTempRequest.getGroupTempName());
        }
        if(groupTempRequest.getFlagCompany() != null){
            workflowGroupTemp.setFlagCompany(groupTempRequest.getFlagCompany());
        }
        if(groupTempRequest.getFlagOrganization() != null){
            workflowGroupTemp.setFlagOrganization(groupTempRequest.getFlagOrganization());
        }
        if(groupTempRequest.getFlagJobLevel() != null){
            workflowGroupTemp.setFlagJobLevel(groupTempRequest.getFlagJobLevel());
        }
        if(groupTempRequest.getFlagJobTitle() != null){
            workflowGroupTemp.setFlagJobTitle(groupTempRequest.getFlagJobTitle());
        }
        if(groupTempRequest.getFlagLocation() != null){
            workflowGroupTemp.setFlagLocation(groupTempRequest.getFlagLocation());
        }
        if(groupTempRequest.getFlagPosition() != null){
            workflowGroupTemp.setFlagPosition(groupTempRequest.getFlagPosition());
        }
        if(groupTempRequest.getFlagEmployee() != null){
            workflowGroupTemp.setFlagEmployee(groupTempRequest.getFlagEmployee());
        }

        if(groupTempRequest.getDocTypeCode() != null){
            WorkflowDocType workflowDocType = workflowDocTypeRepository.findByCode(groupTempRequest.getDocTypeCode());
            workflowGroupTemp.setWorkflowDocType(workflowDocType);
        }

        if(groupTempRequest.getSequence() != null && !workflowGroupTemp.getSequence().equals(groupTempRequest.getSequence())){
            WorkflowGroupTemp detailWorkflowGroupTemp = workflowGroupTempRepository.findByDocTypeAndSequence(workflowGroupTemp.getWorkflowDocType().getId(), groupTempRequest.getSequence());
            if(detailWorkflowGroupTemp == null){
                throw new ConflictException("Sequence is already max");
            }
            detailWorkflowGroupTemp.setSequence(workflowGroupTemp.getSequence());
            workflowGroupTempRepository.save(detailWorkflowGroupTemp);

            workflowGroupTemp.setSequence(groupTempRequest.getSequence());
        }

        if(groupTempRequest.getActive() != null){
            workflowGroupTemp.setActive(groupTempRequest.getActive());
        }

        if(groupTempRequest.getTemplateList() != null){
            List<WorkflowGroupTempDt> existingDetail = workflowGroupTempDtRepository.findByWorkflowGroupTemp(workflowGroupTemp.getId());
            List<WorkflowTemp> existingTemplate = existingDetail.stream().map(WorkflowGroupTempDt::getWorkflowTemp).toList();

            List<Long> added = new ArrayList<>(groupTempRequest.getTemplateList());
            added.removeAll(existingTemplate.stream().map(WorkflowTemp::getId).toList());
            List<Long> removed = new ArrayList<>(existingTemplate.stream().map(WorkflowTemp::getId).toList());
            removed.removeAll(groupTempRequest.getTemplateList());

            for(Long addedId : added){
                WorkflowGroupTempDt workflowGroupTempDt = new WorkflowGroupTempDt();
                workflowGroupTempDt.setWorkflowGroupTemp(workflowGroupTemp);
                workflowGroupTempDt.setWorkflowTemp(workflowTempRepository.findById(addedId).get());
                workflowGroupTempDtRepository.save(workflowGroupTempDt);
            }

            for(Long removedId : removed){
                WorkflowTemp workflowTemp = workflowTempRepository.findById(removedId).get();
                WorkflowGroupTempDt workflowGroupTempDt = workflowGroupTempDtRepository.findByWorkflowGroupTempAndWorkflowTemp(workflowGroupTemp, workflowTemp);
                workflowGroupTempDtRepository.delete(workflowGroupTempDt);
            }
        }
    }

    public Page<WorkflowGroupTemp> getGroupTemplate(String codeSearch, String nameSearch, String docTypeSearch,
                                                    Boolean activeSearch, Pageable pageable) {
        return workflowGroupTempRepository.getGroupTemplate(codeSearch, nameSearch, docTypeSearch, activeSearch, pageable);
    }

    public GroupTempResponse getGroupTemplateDetail(Long id){
        WorkflowGroupTemp workflowGroupTemp = workflowGroupTempRepository.findById(id).get();
        GroupTempResponse groupTempResponse = new GroupTempResponse();
        groupTempResponse.setCode(workflowGroupTemp.getCode());
        groupTempResponse.setName(workflowGroupTemp.getName());
        groupTempResponse.setWorkflowDocType(workflowGroupTemp.getWorkflowDocType());

        List<WorkflowTemp> workflowTempList = workflowTempRepository.getWorkflowTempList(workflowGroupTemp.getId());
        groupTempResponse.setWorkflowTempList(workflowTempList == null ? new ArrayList<>() : workflowTempList);
        return groupTempResponse;
    }
}
