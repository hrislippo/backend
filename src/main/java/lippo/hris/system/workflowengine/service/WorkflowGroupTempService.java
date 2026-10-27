package lippo.hris.system.workflowengine.service;

import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.workflowengine.entity.WorkflowDocType;
import lippo.hris.system.workflowengine.entity.WorkflowGroupTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.repository.WorkflowDocTypeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.GroupTempRequest;
import lippo.hris.system.workflowengine.response.GroupTempResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WorkflowGroupTempService {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

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
        workflowGroupTempRepository.save(workflowGroupTemp);
    }

    public void modifyGroupTemplate(GroupTempRequest groupTempRequest) {
        WorkflowGroupTemp workflowGroupTemp = workflowGroupTempRepository.findById(groupTempRequest.getId()).get();
        workflowGroupTemp.setCode(groupTempRequest.getGroupTempCode());
        workflowGroupTemp.setName(groupTempRequest.getGroupTempName());

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
        workflowGroupTempRepository.save(workflowGroupTemp);
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
        groupTempResponse.setFlagCompany(workflowGroupTemp.getFlagCompany());
        groupTempResponse.setFlagOrganization(workflowGroupTemp.getFlagOrganization());
        groupTempResponse.setFlagJobLevel(workflowGroupTemp.getFlagJobLevel());
        groupTempResponse.setFlagJobTitle(workflowGroupTemp.getFlagJobTitle());
        groupTempResponse.setFlagLocation(workflowGroupTemp.getFlagLocation());
        groupTempResponse.setFlagPosition(workflowGroupTemp.getFlagPosition());
        groupTempResponse.setFlagEmployee(workflowGroupTemp.getFlagEmployee());

        List<WorkflowTemp> workflowTempList = workflowTempRepository.getWorkflowTempList(workflowGroupTemp.getId());
        groupTempResponse.setWorkflowTempList(workflowTempList);
        return groupTempResponse;
    }
}
