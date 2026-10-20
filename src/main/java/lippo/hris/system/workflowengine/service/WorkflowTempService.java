package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import lippo.hris.system.workflowengine.repository.WorkflowTempApproveeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.TempApproveeRequestDt;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lippo.hris.system.workflowengine.response.WorkflowTempDtResponse;
import lippo.hris.system.workflowengine.response.WorkflowTempResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WorkflowTempService {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    @Autowired
    WorkflowTempApproveeRepository workflowTempApproveeRepository;

    public void addWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = new WorkflowTemp();
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());
        workflowTemp.setActive(true);
        workflowTemp.setFlagCompany(workflowTempRequest.getFlagCompany());
        workflowTemp.setFlagOrganization(workflowTempRequest.getFlagOrganization());
        workflowTemp.setFlagJobLevel(workflowTempRequest.getFlagJobLevel());
        workflowTemp.setFlagJobTitle(workflowTempRequest.getFlagJobTitle());
        workflowTemp.setFlagPosition(workflowTempRequest.getFlagPosition());
        workflowTemp.setFlagLocation(workflowTempRequest.getFlagLocation());
        workflowTemp.setFlagEmployee(workflowTempRequest.getFlagEmployee());
        workflowTemp = workflowTempRepository.save(workflowTemp);

        for(TempApproveeRequestDt tempApproveeRequestDt : workflowTempRequest.getDetailList()){
            WorkflowTempApprovee workflowTempApprovee = new WorkflowTempApprovee();
            workflowTempApprovee.setWorkflowTemp(workflowTemp);
            workflowTempApprovee.setCompanyCode(tempApproveeRequestDt.getCompanyCode());
            workflowTempApprovee.setCompanyName(tempApproveeRequestDt.getCompanyName());
            workflowTempApprovee.setOrganizationCode(tempApproveeRequestDt.getOrganizationCode());
            workflowTempApprovee.setOrganizationName(tempApproveeRequestDt.getOrganizationName());
            workflowTempApprovee.setJobLevelCode(tempApproveeRequestDt.getJobLevelCode());
            workflowTempApprovee.setJobLevelName(tempApproveeRequestDt.getJobLevelName());
            workflowTempApprovee.setJobTitleCode(tempApproveeRequestDt.getJobTitleCode());
            workflowTempApprovee.setJobTitleName(tempApproveeRequestDt.getJobTitleName());
            workflowTempApprovee.setLocationCode(tempApproveeRequestDt.getLocationCode());
            workflowTempApprovee.setLocationName(tempApproveeRequestDt.getLocationName());
            workflowTempApprovee.setPositionCode(tempApproveeRequestDt.getPositionCode());
            workflowTempApprovee.setPositionName(tempApproveeRequestDt.getPositionName());
            workflowTempApprovee.setEmployeeNik(tempApproveeRequestDt.getEmployeeNik());
            workflowTempApprovee.setEmployeeName(tempApproveeRequestDt.getEmployeeName());
            workflowTempApproveeRepository.save(workflowTempApprovee);
        }
    }

    public void modifyWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(workflowTempRequest.getId()).get();
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());
        workflowTemp.setFlagCompany(workflowTempRequest.getFlagCompany());
        workflowTemp.setFlagOrganization(workflowTempRequest.getFlagOrganization());
        workflowTemp.setFlagJobLevel(workflowTempRequest.getFlagJobLevel());
        workflowTemp.setFlagJobTitle(workflowTempRequest.getFlagJobTitle());
        workflowTemp.setFlagLocation(workflowTempRequest.getFlagLocation());
        workflowTemp.setFlagPosition(workflowTempRequest.getFlagPosition());
        workflowTemp.setFlagEmployee(workflowTempRequest.getFlagEmployee());

        if(workflowTempRequest.getActive() != null){
            workflowTemp.setActive(workflowTempRequest.getActive());
        }

        if(workflowTempRequest.getDetailList() != null){
            List<WorkflowTempApprovee> tempApproveeList = workflowTempApproveeRepository.findByWorkflowTemp(workflowTemp);
            List<TempApproveeRequestDt> newDetail = workflowTempRequest.getDetailList();
            List<TempApproveeRequestDt> existingDetail = new ArrayList<>();
            for(WorkflowTempApprovee detail : tempApproveeList){
                TempApproveeRequestDt detailRequest = new TempApproveeRequestDt();
                detailRequest.setOrganizationCode(detail.getOrganizationCode());
                detailRequest.setOrganizationName(detail.getOrganizationName());
                detailRequest.setJobLevelCode(detail.getJobLevelCode());
                detailRequest.setJobLevelName(detail.getJobLevelName());
                detailRequest.setCompanyCode(detail.getCompanyCode());
                detailRequest.setCompanyName(detail.getCompanyName());
                detailRequest.setLocationCode(detail.getLocationCode());
                detailRequest.setLocationName(detail.getLocationName());
                detailRequest.setPositionCode(detail.getPositionCode());
                detailRequest.setPositionName(detail.getPositionName());
                detailRequest.setJobTitleCode(detail.getJobTitleCode());
                detailRequest.setJobTitleName(detail.getJobTitleName());
                detailRequest.setEmployeeNik(detail.getEmployeeNik());
                detailRequest.setEmployeeName(detail.getEmployeeName());
                existingDetail.add(detailRequest);
            }

            List<TempApproveeRequestDt> addDetail = new ArrayList<>(newDetail);
            addDetail.removeAll(existingDetail);
            List<TempApproveeRequestDt> removeDetail = new ArrayList<>(existingDetail);
            removeDetail.removeAll(newDetail);

            for(TempApproveeRequestDt detail : addDetail){
                WorkflowTempApprovee workflowTempApprovee = new WorkflowTempApprovee();
                workflowTempApprovee.setWorkflowTemp(workflowTemp);
                workflowTempApprovee.setCompanyCode(detail.getCompanyCode());
                workflowTempApprovee.setCompanyName(detail.getCompanyName());
                workflowTempApprovee.setOrganizationCode(detail.getOrganizationCode());
                workflowTempApprovee.setOrganizationName(detail.getOrganizationName());
                workflowTempApprovee.setJobLevelCode(detail.getJobLevelCode());
                workflowTempApprovee.setJobLevelName(detail.getJobLevelName());
                workflowTempApprovee.setJobTitleCode(detail.getJobTitleCode());
                workflowTempApprovee.setJobTitleName(detail.getJobTitleName());
                workflowTempApprovee.setPositionCode(detail.getPositionCode());
                workflowTempApprovee.setPositionName(detail.getPositionName());
                workflowTempApprovee.setLocationCode(detail.getLocationCode());
                workflowTempApprovee.setLocationName(detail.getLocationName());
                workflowTempApprovee.setEmployeeNik(detail.getEmployeeNik());
                workflowTempApprovee.setEmployeeName(detail.getEmployeeName());
                workflowTempApproveeRepository.save(workflowTempApprovee);
            }

            for(TempApproveeRequestDt detail : removeDetail){
                WorkflowTempApprovee workflowTempApprovee = workflowTempApproveeRepository.findByWorkflowTempAndCriteria(workflowTemp.getId(),
                        detail.getCompanyCode(), detail.getOrganizationCode(), detail.getJobLevelCode(), detail.getJobTitleCode(), detail.getLocationCode(),
                        detail.getPositionCode(), detail.getEmployeeNik());
                workflowTempApproveeRepository.delete(workflowTempApprovee);
            }
        }

        workflowTempRepository.save(workflowTemp);
    }

    public Page<WorkflowTemp> getTemplate(String codeSearch, String nameSearch, Boolean activeSearch, Pageable pageable){
        return workflowTempRepository.getTemplate(codeSearch, nameSearch, activeSearch, pageable);
    }

    public List<WorkflowTemp> getTemplateList(Boolean flagCompany, Boolean flagOrganization, Boolean flagJobLevel, Boolean flagJobTitle,
                                              Boolean flagLocation, Boolean flagPosition, Boolean flagEmployee){
        return workflowTempRepository.findByCriteriaAndActiveTrue(flagCompany, flagOrganization, flagJobLevel, flagJobTitle, flagLocation, flagPosition, flagEmployee);
    }

    public WorkflowTempResponse getTemplateDetail(Long id){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(id).get();
        List<WorkflowTempApprovee> workflowTempApproveeList = workflowTempApproveeRepository.findByWorkflowTemp(workflowTemp);
        List<WorkflowTempDtResponse> workflowTempDtResponseList = new ArrayList<>();

        WorkflowTempResponse workflowTempResponse = new WorkflowTempResponse();
        workflowTempResponse.setTempCode(workflowTemp.getCode());
        workflowTempResponse.setTempName(workflowTemp.getName());
        workflowTempResponse.setFlagCompany(workflowTemp.getFlagCompany());
        workflowTempResponse.setFlagOrganization(workflowTemp.getFlagOrganization());
        workflowTempResponse.setFlagJobLevel(workflowTemp.getFlagJobLevel());
        workflowTempResponse.setFlagJobTitle(workflowTemp.getFlagJobTitle());
        workflowTempResponse.setFlagPosition(workflowTemp.getFlagPosition());
        workflowTempResponse.setFlagLocation(workflowTemp.getFlagLocation());
        workflowTempResponse.setFlagEmployee(workflowTemp.getFlagEmployee());

        for(WorkflowTempApprovee workflowTempApprovee : workflowTempApproveeList){
            WorkflowTempDtResponse workflowTempDtResponse = new WorkflowTempDtResponse();
            workflowTempDtResponse.setCompCode(workflowTempApprovee.getCompanyCode());
            workflowTempDtResponse.setCompName(workflowTempApprovee.getCompanyName());
            workflowTempDtResponse.setOrgCode(workflowTempApprovee.getOrganizationCode());
            workflowTempDtResponse.setOrgName(workflowTempApprovee.getOrganizationName());
            workflowTempDtResponse.setJobLvlCode(workflowTempApprovee.getJobLevelCode());
            workflowTempDtResponse.setJobLvlName(workflowTempApprovee.getJobLevelName());
            workflowTempDtResponse.setJobTtlCode(workflowTempApprovee.getJobTitleCode());
            workflowTempDtResponse.setJobTtlName(workflowTempApprovee.getJobTitleName());
            workflowTempDtResponse.setLocCode(workflowTempApprovee.getLocationCode());
            workflowTempDtResponse.setLocName(workflowTempApprovee.getLocationName());
            workflowTempDtResponse.setPosCode(workflowTempApprovee.getPositionCode());
            workflowTempDtResponse.setPosName(workflowTempApprovee.getPositionName());
            workflowTempDtResponse.setEmpNik(workflowTempApprovee.getEmployeeNik());
            workflowTempDtResponse.setEmpName(workflowTempApprovee.getEmployeeName());
            workflowTempDtResponseList.add(workflowTempDtResponse);
        }
        workflowTempResponse.setDetailList(workflowTempDtResponseList);
        return workflowTempResponse;
    }
}
