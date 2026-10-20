package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowApproverTemp;
import lippo.hris.system.workflowengine.entity.WorkflowApproverTempDt;
import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import lippo.hris.system.workflowengine.repository.WorkflowApproverTempDtRepository;
import lippo.hris.system.workflowengine.repository.WorkflowApproverTempRepository;
import lippo.hris.system.workflowengine.request.TempApproveeRequestDt;
import lippo.hris.system.workflowengine.request.WorkflowApproverTempRequest;
import lippo.hris.system.workflowengine.request.WorkflowApproverTempRequestDt;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lippo.hris.system.workflowengine.response.WorkflowApproverTempDtResponse;
import lippo.hris.system.workflowengine.response.WorkflowApproverTempResponse;
import lippo.hris.system.workflowengine.response.WorkflowTempDtResponse;
import lippo.hris.system.workflowengine.response.WorkflowTempResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WorkflowApproverTempService {

    @Autowired
    WorkflowApproverTempRepository workflowApproverTempRepository;

    @Autowired
    WorkflowApproverTempDtRepository workflowApproverTempDtRepository;

    public void addWorkflowApproverTemplate(WorkflowApproverTempRequest workflowApproverTempRequest){
        WorkflowApproverTemp workflowApproverTemp = new WorkflowApproverTemp();
        workflowApproverTemp.setCode(workflowApproverTempRequest.getTempCode());
        workflowApproverTemp.setName(workflowApproverTempRequest.getTempName());
        workflowApproverTemp.setActive(true);
        workflowApproverTemp.setFlagCompany(workflowApproverTempRequest.getFlagCompany());
        workflowApproverTemp.setFlagOrganization(workflowApproverTempRequest.getFlagOrganization());
        workflowApproverTemp.setFlagJobLevel(workflowApproverTempRequest.getFlagJobLevel());
        workflowApproverTemp.setFlagJobTitle(workflowApproverTempRequest.getFlagJobTitle());
        workflowApproverTemp.setFlagPosition(workflowApproverTempRequest.getFlagPosition());
        workflowApproverTemp.setFlagLocation(workflowApproverTempRequest.getFlagLocation());
        workflowApproverTemp.setFlagEmployee(workflowApproverTempRequest.getFlagEmployee());
        workflowApproverTemp = workflowApproverTempRepository.save(workflowApproverTemp);

        for(WorkflowApproverTempRequestDt workflowApproverTempRequestDt : workflowApproverTempRequest.getDetailList()){
            WorkflowApproverTempDt workflowApproverTempDt = new WorkflowApproverTempDt();
            workflowApproverTempDt.setWorkflowApproverTemp(workflowApproverTemp);
            workflowApproverTempDt.setCompanyCode(workflowApproverTempRequestDt.getCompanyCode());
            workflowApproverTempDt.setCompanyName(workflowApproverTempRequestDt.getCompanyName());
            workflowApproverTempDt.setOrganizationCode(workflowApproverTempRequestDt.getOrganizationCode());
            workflowApproverTempDt.setOrganizationName(workflowApproverTempRequestDt.getOrganizationName());
            workflowApproverTempDt.setJobLevelCode(workflowApproverTempRequestDt.getJobLevelCode());
            workflowApproverTempDt.setJobLevelName(workflowApproverTempRequestDt.getJobLevelName());
            workflowApproverTempDt.setJobTitleCode(workflowApproverTempRequestDt.getJobTitleCode());
            workflowApproverTempDt.setJobTitleName(workflowApproverTempRequestDt.getJobTitleName());
            workflowApproverTempDt.setLocationCode(workflowApproverTempRequestDt.getLocationCode());
            workflowApproverTempDt.setLocationName(workflowApproverTempRequestDt.getLocationName());
            workflowApproverTempDt.setPositionCode(workflowApproverTempRequestDt.getPositionCode());
            workflowApproverTempDt.setPositionName(workflowApproverTempRequestDt.getPositionName());
            workflowApproverTempDt.setEmployeeNik(workflowApproverTempRequestDt.getEmployeeNik());
            workflowApproverTempDt.setEmployeeName(workflowApproverTempRequestDt.getEmployeeName());
            workflowApproverTempDtRepository.save(workflowApproverTempDt);
        }
    }

    public void modifyWorkflowApproverTemplate(WorkflowApproverTempRequest workflowApproverTempRequest){
        WorkflowApproverTemp workflowApproverTemp = workflowApproverTempRepository.findById(workflowApproverTempRequest.getId()).get();
        workflowApproverTemp.setCode(workflowApproverTempRequest.getTempCode());
        workflowApproverTemp.setName(workflowApproverTempRequest.getTempName());
        workflowApproverTemp.setFlagCompany(workflowApproverTempRequest.getFlagCompany());
        workflowApproverTemp.setFlagOrganization(workflowApproverTempRequest.getFlagOrganization());
        workflowApproverTemp.setFlagJobLevel(workflowApproverTempRequest.getFlagJobLevel());
        workflowApproverTemp.setFlagJobTitle(workflowApproverTempRequest.getFlagJobTitle());
        workflowApproverTemp.setFlagLocation(workflowApproverTempRequest.getFlagLocation());
        workflowApproverTemp.setFlagPosition(workflowApproverTempRequest.getFlagPosition());
        workflowApproverTemp.setFlagEmployee(workflowApproverTempRequest.getFlagEmployee());

        if(workflowApproverTempRequest.getActive() != null){
            workflowApproverTemp.setActive(workflowApproverTempRequest.getActive());
        }

        if(workflowApproverTempRequest.getDetailList() != null){
            List<WorkflowApproverTempDt> tempApproveeList = workflowApproverTempDtRepository.findByWorkflowApproverTemp(workflowApproverTemp);
            List<WorkflowApproverTempRequestDt> newDetail = workflowApproverTempRequest.getDetailList();
            List<WorkflowApproverTempRequestDt> existingDetail = new ArrayList<>();
            for(WorkflowApproverTempDt detail : tempApproveeList){
                WorkflowApproverTempRequestDt detailRequest = new WorkflowApproverTempRequestDt();
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

            List<WorkflowApproverTempRequestDt> addDetail = new ArrayList<>(newDetail);
            addDetail.removeAll(existingDetail);
            List<WorkflowApproverTempRequestDt> removeDetail = new ArrayList<>(existingDetail);
            removeDetail.removeAll(newDetail);

            for(WorkflowApproverTempRequestDt detail : addDetail){
                WorkflowApproverTempDt workflowApproverTempDt = new WorkflowApproverTempDt();
                workflowApproverTempDt.setWorkflowApproverTemp(workflowApproverTemp);
                workflowApproverTempDt.setCompanyCode(detail.getCompanyCode());
                workflowApproverTempDt.setCompanyName(detail.getCompanyName());
                workflowApproverTempDt.setOrganizationCode(detail.getOrganizationCode());
                workflowApproverTempDt.setOrganizationName(detail.getOrganizationName());
                workflowApproverTempDt.setJobLevelCode(detail.getJobLevelCode());
                workflowApproverTempDt.setJobLevelName(detail.getJobLevelName());
                workflowApproverTempDt.setJobTitleCode(detail.getJobTitleCode());
                workflowApproverTempDt.setJobTitleName(detail.getJobTitleName());
                workflowApproverTempDt.setPositionCode(detail.getPositionCode());
                workflowApproverTempDt.setPositionName(detail.getPositionName());
                workflowApproverTempDt.setLocationCode(detail.getLocationCode());
                workflowApproverTempDt.setLocationName(detail.getLocationName());
                workflowApproverTempDt.setEmployeeNik(detail.getEmployeeNik());
                workflowApproverTempDt.setEmployeeName(detail.getEmployeeName());
                workflowApproverTempDtRepository.save(workflowApproverTempDt);
            }

            for(WorkflowApproverTempRequestDt detail : removeDetail){
                WorkflowApproverTempDt workflowApproverTempDt = workflowApproverTempDtRepository.findByWorkflowApproverTempAndCriteria(workflowApproverTemp.getId(),
                        detail.getCompanyCode(), detail.getOrganizationCode(), detail.getJobLevelCode(), detail.getJobTitleCode(), detail.getLocationCode(),
                        detail.getPositionCode(), detail.getEmployeeNik());
                workflowApproverTempDtRepository.delete(workflowApproverTempDt);
            }
        }

        workflowApproverTempRepository.save(workflowApproverTemp);
    }

    public Page<WorkflowApproverTemp> getApproverTemplate(String codeSearch, String nameSearch, Boolean activeSearch, Pageable pageable){
        return workflowApproverTempRepository.getApproverTemplate(codeSearch, nameSearch, activeSearch, pageable);
    }

    public List<WorkflowApproverTemp> getApproverTemplateList(){
        return workflowApproverTempRepository.findAll();
    }

    public WorkflowApproverTempResponse getApproverTemplateDetail(Long id){
        WorkflowApproverTemp workflowApproverTemp = workflowApproverTempRepository.findById(id).get();
        List<WorkflowApproverTempDt> workflowApproverTempDtList = workflowApproverTempDtRepository.findByWorkflowApproverTemp(workflowApproverTemp);
        List<WorkflowApproverTempDtResponse> workflowApproverTempDtResponseList = new ArrayList<>();

        WorkflowApproverTempResponse workflowApproverTempResponse = new WorkflowApproverTempResponse();
        workflowApproverTempResponse.setTempCode(workflowApproverTemp.getCode());
        workflowApproverTempResponse.setTempName(workflowApproverTemp.getName());
        workflowApproverTempResponse.setFlagCompany(workflowApproverTemp.getFlagCompany());
        workflowApproverTempResponse.setFlagOrganization(workflowApproverTemp.getFlagOrganization());
        workflowApproverTempResponse.setFlagJobLevel(workflowApproverTemp.getFlagJobLevel());
        workflowApproverTempResponse.setFlagJobTitle(workflowApproverTemp.getFlagJobTitle());
        workflowApproverTempResponse.setFlagPosition(workflowApproverTemp.getFlagPosition());
        workflowApproverTempResponse.setFlagLocation(workflowApproverTemp.getFlagLocation());
        workflowApproverTempResponse.setFlagEmployee(workflowApproverTemp.getFlagEmployee());

        for(WorkflowApproverTempDt workflowApproverTempDt : workflowApproverTempDtList){
            WorkflowApproverTempDtResponse workflowApproverTempDtResponse = new WorkflowApproverTempDtResponse();
            workflowApproverTempDtResponse.setCompCode(workflowApproverTempDt.getCompanyCode());
            workflowApproverTempDtResponse.setCompName(workflowApproverTempDt.getCompanyName());
            workflowApproverTempDtResponse.setOrgCode(workflowApproverTempDt.getOrganizationCode());
            workflowApproverTempDtResponse.setOrgName(workflowApproverTempDt.getOrganizationName());
            workflowApproverTempDtResponse.setJobLvlCode(workflowApproverTempDt.getJobLevelCode());
            workflowApproverTempDtResponse.setJobLvlName(workflowApproverTempDt.getJobLevelName());
            workflowApproverTempDtResponse.setJobTtlCode(workflowApproverTempDt.getJobTitleCode());
            workflowApproverTempDtResponse.setJobTtlName(workflowApproverTempDt.getJobTitleName());
            workflowApproverTempDtResponse.setLocCode(workflowApproverTempDt.getLocationCode());
            workflowApproverTempDtResponse.setLocName(workflowApproverTempDt.getLocationName());
            workflowApproverTempDtResponse.setPosCode(workflowApproverTempDt.getPositionCode());
            workflowApproverTempDtResponse.setPosName(workflowApproverTempDt.getPositionName());
            workflowApproverTempDtResponse.setEmpNik(workflowApproverTempDt.getEmployeeNik());
            workflowApproverTempDtResponse.setEmpName(workflowApproverTempDt.getEmployeeName());
            workflowApproverTempDtResponseList.add(workflowApproverTempDtResponse);
        }
        workflowApproverTempResponse.setDetailList(workflowApproverTempDtResponseList);
        return workflowApproverTempResponse;
    }
}
