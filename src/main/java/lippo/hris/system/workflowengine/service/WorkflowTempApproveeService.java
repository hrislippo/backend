package lippo.hris.system.workflowengine.service;

import lippo.hris.system.workflowengine.entity.WorkflowTemp;
import lippo.hris.system.workflowengine.entity.WorkflowTempApprovee;
import lippo.hris.system.workflowengine.repository.WorkflowTempApproveeRepository;
import lippo.hris.system.workflowengine.repository.WorkflowTempRepository;
import lippo.hris.system.workflowengine.request.TempApproveeRequest;
import lippo.hris.system.workflowengine.request.TempApproveeRequestDt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WorkflowTempApproveeService {

    @Autowired
    WorkflowTempRepository workflowTempRepository;

    @Autowired
    WorkflowTempApproveeRepository workflowTempApproveeRepository;

    public void modifyTemplateApprovee(TempApproveeRequest tempApproveeRequest) {

        WorkflowTemp workflowTemp = workflowTempRepository.findById(tempApproveeRequest.getTempId()).orElse(null);
        List<WorkflowTempApprovee> tempApproveeList = workflowTempApproveeRepository.findByWorkflowTemp(workflowTemp);

        List<TempApproveeRequestDt> newDetail = tempApproveeRequest.getDetailList();
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
}
