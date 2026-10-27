package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprover;
import lippo.hris.system.workflowengine.repository.WorkflowGroupTempRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempApproveeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempApproverRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.TempApproveeRequestDt;
import lippo.hris.system.workflowengine.request.TempApproverRequestDt;
import lippo.hris.system.workflowengine.request.WorkflowTempRequest;
import lippo.hris.system.workflowengine.response.*;
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
    WorkflowGroupTempRepository workflowGroupTempRepository;

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    @Autowired
    WorkflowTempApproveeRepository workflowTempApproveeRepository;

    @Autowired
    WorkflowTempApproverRepository workflowTempApproverRepository;

    public void addWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = new WorkflowTemp();
        workflowTemp.setWorkflowGroupTemp(workflowGroupTempRepository.findById(workflowTempRequest.getGroupTempId()).get());
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());
        workflowTemp.setActive(true);
        workflowTemp = workflowTempRepository.save(workflowTemp);

        for(TempApproveeRequestDt tempApproveeRequestDt : workflowTempRequest.getApproveeList()){
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

        for(TempApproverRequestDt tempApproverRequestDt : workflowTempRequest.getApproverList()){
            WorkflowTempApprover workflowTempApprover = new WorkflowTempApprover();
            workflowTempApprover.setWorkflowTemp(workflowTemp);
            workflowTempApprover.setLevel(tempApproverRequestDt.getLevel());
            workflowTempApprover.setCompanyCode(tempApproverRequestDt.getCompanyCode());
            workflowTempApprover.setCompanyName(tempApproverRequestDt.getCompanyName());
            workflowTempApprover.setOrganizationCode(tempApproverRequestDt.getOrganizationCode());
            workflowTempApprover.setOrganizationName(tempApproverRequestDt.getOrganizationName());
            workflowTempApprover.setJobLevelCode(tempApproverRequestDt.getJobLevelCode());
            workflowTempApprover.setJobLevelName(tempApproverRequestDt.getJobLevelName());
            workflowTempApprover.setJobTitleCode(tempApproverRequestDt.getJobTitleCode());
            workflowTempApprover.setJobTitleName(tempApproverRequestDt.getJobTitleName());
            workflowTempApprover.setLocationCode(tempApproverRequestDt.getLocationCode());
            workflowTempApprover.setLocationName(tempApproverRequestDt.getLocationName());
            workflowTempApprover.setPositionCode(tempApproverRequestDt.getPositionCode());
            workflowTempApprover.setPositionName(tempApproverRequestDt.getPositionName());
            workflowTempApprover.setEmployeeNik(tempApproverRequestDt.getEmployeeNik());
            workflowTempApprover.setEmployeeName(tempApproverRequestDt.getEmployeeName());
            workflowTempApprover.setOrgChartCode(tempApproverRequestDt.getOrgChartCode());
            workflowTempApprover.setOrgChartName(tempApproverRequestDt.getOrgChartName());
            workflowTempApproverRepository.save(workflowTempApprover);
        }
    }

    public void modifyWorkflowTemplate(WorkflowTempRequest workflowTempRequest){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(workflowTempRequest.getId()).get();
        workflowTemp.setCode(workflowTempRequest.getTempCode());
        workflowTemp.setName(workflowTempRequest.getTempName());

        if(workflowTempRequest.getActive() != null){
            workflowTemp.setActive(workflowTempRequest.getActive());
        }
        workflowTemp = workflowTempRepository.save(workflowTemp);

        if(workflowTempRequest.getApproveeList() != null){
            List<WorkflowTempApprovee> existingWorkflowTempApproveeList = workflowTempApproveeRepository.findByWorkflowTemp(workflowTemp);
            List<TempApproveeRequestDt> existingApprovee = new ArrayList<>();
            for(WorkflowTempApprovee workflowTempApprovee : existingWorkflowTempApproveeList){
                TempApproveeRequestDt tempApproveeRequestDt = new TempApproveeRequestDt();
                tempApproveeRequestDt.setCompanyCode(workflowTempApprovee.getCompanyCode());
                tempApproveeRequestDt.setCompanyName(workflowTempApprovee.getCompanyName());
                tempApproveeRequestDt.setOrganizationCode(workflowTempApprovee.getOrganizationCode());
                tempApproveeRequestDt.setOrganizationName(workflowTempApprovee.getOrganizationName());
                tempApproveeRequestDt.setJobLevelCode(workflowTempApprovee.getJobLevelCode());
                tempApproveeRequestDt.setJobLevelName(workflowTempApprovee.getJobLevelName());
                tempApproveeRequestDt.setJobTitleCode(workflowTempApprovee.getJobTitleCode());
                tempApproveeRequestDt.setJobTitleName(workflowTempApprovee.getJobTitleName());
                tempApproveeRequestDt.setLocationCode(workflowTempApprovee.getLocationCode());
                tempApproveeRequestDt.setLocationName(workflowTempApprovee.getLocationName());
                tempApproveeRequestDt.setPositionCode(workflowTempApprovee.getPositionCode());
                tempApproveeRequestDt.setPositionName(workflowTempApprovee.getPositionName());
                tempApproveeRequestDt.setEmployeeNik(workflowTempApprovee.getEmployeeNik());
                tempApproveeRequestDt.setEmployeeName(workflowTempApprovee.getEmployeeName());
                existingApprovee.add(tempApproveeRequestDt);
            }
            List<TempApproveeRequestDt> newApprovee = workflowTempRequest.getApproveeList();

            List<TempApproveeRequestDt> addApproveeList = new ArrayList<>(newApprovee);
            addApproveeList.removeAll(existingApprovee);
            List<TempApproveeRequestDt> removeApproveeList = new ArrayList<>(existingApprovee);
            removeApproveeList.removeAll(newApprovee);

            for(TempApproveeRequestDt removeApproveeRequestDt : removeApproveeList){
                WorkflowTempApprovee workflowTempApprovee = workflowTempApproveeRepository.findByWorkflowTempAndCriteria(workflowTemp.getId(),
                        removeApproveeRequestDt.getCompanyCode(), removeApproveeRequestDt.getOrganizationCode(),
                        removeApproveeRequestDt.getJobLevelCode(), removeApproveeRequestDt.getJobTitleCode(), removeApproveeRequestDt.getLocationCode(),
                        removeApproveeRequestDt.getPositionCode(), removeApproveeRequestDt.getEmployeeNik());
                workflowTempApproveeRepository.delete(workflowTempApprovee);
            }

            for(TempApproveeRequestDt addApproveeRequestDt : addApproveeList){
                WorkflowTempApprovee workflowTempApprovee = new WorkflowTempApprovee();
                workflowTempApprovee.setWorkflowTemp(workflowTemp);
                workflowTempApprovee.setCompanyCode(addApproveeRequestDt.getCompanyCode());
                workflowTempApprovee.setCompanyName(addApproveeRequestDt.getCompanyName());
                workflowTempApprovee.setOrganizationCode(addApproveeRequestDt.getOrganizationCode());
                workflowTempApprovee.setOrganizationName(addApproveeRequestDt.getOrganizationName());
                workflowTempApprovee.setJobLevelCode(addApproveeRequestDt.getJobLevelCode());
                workflowTempApprovee.setJobLevelName(addApproveeRequestDt.getJobLevelName());
                workflowTempApprovee.setJobTitleCode(addApproveeRequestDt.getJobTitleCode());
                workflowTempApprovee.setJobTitleName(addApproveeRequestDt.getJobTitleName());
                workflowTempApprovee.setLocationCode(addApproveeRequestDt.getLocationCode());
                workflowTempApprovee.setLocationName(addApproveeRequestDt.getLocationName());
                workflowTempApprovee.setPositionCode(addApproveeRequestDt.getPositionCode());
                workflowTempApprovee.setPositionName(addApproveeRequestDt.getPositionName());
                workflowTempApprovee.setEmployeeNik(addApproveeRequestDt.getEmployeeNik());
                workflowTempApprovee.setEmployeeName(addApproveeRequestDt.getEmployeeName());
                workflowTempApproveeRepository.save(workflowTempApprovee);
            }
        }

        if(workflowTempRequest.getApproverList() != null){
            List<WorkflowTempApprover> existingWorkflowTempApproverList = workflowTempApproverRepository.findByWorkflowTemp(workflowTemp);
            List<TempApproverRequestDt> existingApprover = new ArrayList<>();
            for(WorkflowTempApprover workflowTempApprover : existingWorkflowTempApproverList){
                TempApproverRequestDt tempApproverRequestDt = new TempApproverRequestDt();
                tempApproverRequestDt.setLevel(workflowTempApprover.getLevel());
                tempApproverRequestDt.setCompanyCode(workflowTempApprover.getCompanyCode());
                tempApproverRequestDt.setCompanyName(workflowTempApprover.getCompanyName());
                tempApproverRequestDt.setOrganizationCode(workflowTempApprover.getOrganizationCode());
                tempApproverRequestDt.setOrganizationName(workflowTempApprover.getOrganizationName());
                tempApproverRequestDt.setJobLevelCode(workflowTempApprover.getJobLevelCode());
                tempApproverRequestDt.setJobLevelName(workflowTempApprover.getJobLevelName());
                tempApproverRequestDt.setJobTitleCode(workflowTempApprover.getJobTitleCode());
                tempApproverRequestDt.setJobTitleName(workflowTempApprover.getJobTitleName());
                tempApproverRequestDt.setLocationCode(workflowTempApprover.getLocationCode());
                tempApproverRequestDt.setLocationName(workflowTempApprover.getLocationName());
                tempApproverRequestDt.setPositionCode(workflowTempApprover.getPositionCode());
                tempApproverRequestDt.setPositionName(workflowTempApprover.getPositionName());
                tempApproverRequestDt.setEmployeeNik(workflowTempApprover.getEmployeeNik());
                tempApproverRequestDt.setEmployeeName(workflowTempApprover.getEmployeeName());
                tempApproverRequestDt.setOrgChartCode(workflowTempApprover.getOrgChartCode());
                tempApproverRequestDt.setOrgChartName(workflowTempApprover.getOrgChartName());
                existingApprover.add(tempApproverRequestDt);
            }
            List<TempApproverRequestDt> newApprover = workflowTempRequest.getApproverList();

            List<TempApproverRequestDt> addApproverList = new ArrayList<>(newApprover);
            addApproverList.removeAll(existingApprover);
            List<TempApproverRequestDt> removeApproverList = new ArrayList<>(existingApprover);
            removeApproverList.removeAll(newApprover);

            for(TempApproverRequestDt removeApproverRequestDt : removeApproverList){
                WorkflowTempApprover workflowTempApprover = workflowTempApproverRepository.findByWorkflowTempLevelAndCriteria(workflowTemp.getId(), removeApproverRequestDt.getLevel(),
                        removeApproverRequestDt.getCompanyCode(), removeApproverRequestDt.getOrganizationCode(),
                        removeApproverRequestDt.getJobLevelCode(), removeApproverRequestDt.getJobTitleCode(), removeApproverRequestDt.getLocationCode(),
                        removeApproverRequestDt.getPositionCode(), removeApproverRequestDt.getEmployeeNik(), removeApproverRequestDt.getOrgChartCode());
                workflowTempApproverRepository.delete(workflowTempApprover);
            }

            for(TempApproverRequestDt addApproverRequestDt : addApproverList){
                WorkflowTempApprover workflowTempApprover = new WorkflowTempApprover();
                workflowTempApprover.setWorkflowTemp(workflowTemp);
                workflowTempApprover.setLevel(addApproverRequestDt.getLevel());
                workflowTempApprover.setCompanyCode(addApproverRequestDt.getCompanyCode());
                workflowTempApprover.setCompanyName(addApproverRequestDt.getCompanyName());
                workflowTempApprover.setOrganizationCode(addApproverRequestDt.getOrganizationCode());
                workflowTempApprover.setOrganizationName(addApproverRequestDt.getOrganizationName());
                workflowTempApprover.setJobLevelCode(addApproverRequestDt.getJobLevelCode());
                workflowTempApprover.setJobLevelName(addApproverRequestDt.getJobLevelName());
                workflowTempApprover.setJobTitleCode(addApproverRequestDt.getJobTitleCode());
                workflowTempApprover.setJobTitleName(addApproverRequestDt.getJobTitleName());
                workflowTempApprover.setLocationCode(addApproverRequestDt.getLocationCode());
                workflowTempApprover.setLocationName(addApproverRequestDt.getLocationName());
                workflowTempApprover.setPositionCode(addApproverRequestDt.getPositionCode());
                workflowTempApprover.setPositionName(addApproverRequestDt.getPositionName());
                workflowTempApprover.setEmployeeNik(addApproverRequestDt.getEmployeeNik());
                workflowTempApprover.setEmployeeName(addApproverRequestDt.getEmployeeName());
                workflowTempApprover.setOrgChartCode(addApproverRequestDt.getOrgChartCode());
                workflowTempApprover.setOrgChartName(addApproverRequestDt.getOrgChartName());
                workflowTempApproverRepository.save(workflowTempApprover);
            }
        }
    }

    public Page<WorkflowTemp> getTemplate(Long groupTempId, String codeSearch, String nameSearch, Boolean activeSearch, Pageable pageable){
        return workflowTempRepository.getTemplate(groupTempId, codeSearch, nameSearch, activeSearch, pageable);
    }

    public List<WorkflowTemp> getTemplateList(Boolean flagCompany, Boolean flagOrganization, Boolean flagJobLevel, Boolean flagJobTitle,
                                              Boolean flagLocation, Boolean flagPosition, Boolean flagEmployee){
        return workflowTempRepository.findByCriteriaAndActiveTrue(flagCompany, flagOrganization, flagJobLevel, flagJobTitle, flagLocation, flagPosition, flagEmployee);
    }

    public WorkflowTempResponse getTemplateDetail(Long id){
        WorkflowTemp workflowTemp = workflowTempRepository.findById(id).get();
        List<WorkflowTempApprovee> workflowTempApproveeList = workflowTempApproveeRepository.findByWorkflowTemp(workflowTemp);

        WorkflowTempResponse workflowTempResponse = new WorkflowTempResponse();
        workflowTempResponse.setTempCode(workflowTemp.getCode());
        workflowTempResponse.setTempName(workflowTemp.getName());

        List<WorkflowTempCompResponse> companyApprovee = workflowTempApproveeList.stream().filter(e -> e.getCompanyCode() != null)
                .map(e -> new WorkflowTempCompResponse(e.getCompanyCode(), e.getCompanyName())).distinct().toList();
        List<WorkflowTempOrgResponse> organizationApprovee = workflowTempApproveeList.stream().filter(e -> e.getOrganizationCode() != null)
                .map(e -> new WorkflowTempOrgResponse(e.getOrganizationCode(), e.getOrganizationName())).distinct().toList();
        List<WorkflowTempJobLvlResponse> jobLvlApprovee = workflowTempApproveeList.stream().filter(e -> e.getJobLevelCode() != null)
                .map(e -> new WorkflowTempJobLvlResponse(e.getJobLevelCode(), e.getJobLevelName())).distinct().toList();
        List<WorkflowTempJobTtlResponse> jobTtlApprovee = workflowTempApproveeList.stream().filter(e -> e.getJobTitleCode() != null)
                .map(e -> new WorkflowTempJobTtlResponse(e.getJobTitleCode(), e.getJobTitleName())).distinct().toList();
        List<WorkflowTempLocResponse> locationApprovee = workflowTempApproveeList.stream().filter(e -> e.getLocationCode() != null)
                .map(e -> new WorkflowTempLocResponse(e.getLocationCode(), e.getLocationName())).distinct().toList();
        List<WorkflowTempPosResponse> positionApprovee = workflowTempApproveeList.stream().filter(e -> e.getPositionCode() != null)
                .map(e -> new WorkflowTempPosResponse(e.getPositionCode(), e.getPositionName())).distinct().toList();
        List<WorkflowTempEmpResponse> employeeApprovee = workflowTempApproveeList.stream().filter(e -> e.getEmployeeNik() != null)
                .map(e -> new WorkflowTempEmpResponse(e.getEmployeeNik(), e.getEmployeeName())).distinct().toList();

        WorkflowTempApproveeResponse workflowTempApproveeResponse = new WorkflowTempApproveeResponse();
        workflowTempApproveeResponse.setCompList(companyApprovee);
        workflowTempApproveeResponse.setOrgList(organizationApprovee);
        workflowTempApproveeResponse.setJobLvlList(jobLvlApprovee);
        workflowTempApproveeResponse.setJobTtlList(jobTtlApprovee);
        workflowTempApproveeResponse.setLocList(locationApprovee);
        workflowTempApproveeResponse.setPosList(positionApprovee);
        workflowTempApproveeResponse.setEmpList(employeeApprovee);
        workflowTempResponse.setApproveeList(workflowTempApproveeResponse);

        Integer maxLevel = workflowTempApproverRepository.findMaxLevel(workflowTemp.getId());
        List<WorkflowTempApproverResponse> approverList = new ArrayList();
        for(int i = 1; i <= maxLevel; i++){
            List<WorkflowTempApprover> workflowTempApproverList = workflowTempApproverRepository.findByLevelAndWorkflowTemp(i, workflowTemp);

            List<WorkflowTempCompResponse> companyApprover = workflowTempApproverList.stream().filter(e -> e.getCompanyCode() != null)
                    .map(e -> new WorkflowTempCompResponse(e.getCompanyCode(), e.getCompanyName())).distinct().toList();
            List<WorkflowTempOrgResponse> organizationApprover = workflowTempApproverList.stream().filter(e -> e.getOrganizationCode() != null)
                    .map(e -> new WorkflowTempOrgResponse(e.getOrganizationCode(), e.getOrganizationName())).distinct().toList();
            List<WorkflowTempJobLvlResponse> jobLvlApprover = workflowTempApproverList.stream().filter(e -> e.getJobLevelCode() != null)
                    .map(e -> new WorkflowTempJobLvlResponse(e.getJobLevelCode(), e.getJobLevelName())).distinct().toList();
            List<WorkflowTempJobTtlResponse> jobTtlApprover = workflowTempApproverList.stream().filter(e -> e.getJobTitleCode() != null)
                    .map(e -> new WorkflowTempJobTtlResponse(e.getJobTitleCode(), e.getJobTitleName())).distinct().toList();
            List<WorkflowTempLocResponse> locationApprover = workflowTempApproverList.stream().filter(e -> e.getLocationCode() != null)
                    .map(e -> new WorkflowTempLocResponse(e.getLocationCode(), e.getLocationName())).distinct().toList();
            List<WorkflowTempPosResponse> positionApprover = workflowTempApproverList.stream().filter(e -> e.getPositionCode() != null)
                    .map(e -> new WorkflowTempPosResponse(e.getPositionCode(), e.getPositionName())).distinct().toList();
            List<WorkflowTempEmpResponse> employeeApprover = workflowTempApproverList.stream().filter(e -> e.getEmployeeNik() != null)
                    .map(e -> new WorkflowTempEmpResponse(e.getEmployeeNik(), e.getEmployeeName())).distinct().toList();
            List<WorkflowTempOrgChartResponse> orgChartApprover = workflowTempApproverList.stream().filter(e -> e.getOrgChartCode() != null)
                    .map(e -> new WorkflowTempOrgChartResponse(e.getOrgChartCode(), e.getOrgChartName())).distinct().toList();

            WorkflowTempApproverResponse workflowTempApproverResponse = new WorkflowTempApproverResponse();
            workflowTempApproverResponse.setLevel(i);
            workflowTempApproverResponse.setCompList(companyApprover);
            workflowTempApproverResponse.setOrgList(organizationApprover);
            workflowTempApproverResponse.setJobLvlList(jobLvlApprover);
            workflowTempApproverResponse.setJobTtlList(jobTtlApprover);
            workflowTempApproverResponse.setLocList(locationApprover);
            workflowTempApproverResponse.setPosList(positionApprover);
            workflowTempApproverResponse.setEmpList(employeeApprover);
            workflowTempApproverResponse.setOrgChartList(orgChartApprover);
            approverList.add(workflowTempApproverResponse);
        }
        workflowTempResponse.setApproverList(approverList);
        return workflowTempResponse;
    }
}
