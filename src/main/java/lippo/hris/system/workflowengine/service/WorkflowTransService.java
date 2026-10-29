package lippo.hris.system.workflowengine.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.repository.DayPaymentRequestRepository;
import lippo.hris.system.workflowengine.entity.*;
import lippo.hris.system.workflowengine.repository.*;
import lippo.hris.system.workflowengine.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class WorkflowTransService {

    @Autowired
    WorkflowDocTypeRepository workflowDocTypeRepository;

    @Autowired
    WorkflowStatusRepository workflowStatusRepository;

    @Autowired
    WorkflowTempApproveeRepository workflowTempApproveeRepository;

    @Autowired
    WorkflowTempApproverRepository workflowTempApproverRepository;

    @Autowired
    WorkflowTransRepository workflowTransRepository;

    @Autowired
    WorkflowTransApproverRepository workflowTransApproverRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DayPaymentRequestRepository dayPaymentRequestRepository;

    public Page<WorkflowTransResponse> getApproverTransaction(String username, String docTypeCode, String status, LocalDate applyDate, String transactionCode, Pageable pageable) {
        List<WorkflowTransResponse> workflowTransList = workflowTransRepository.getApproverTransaction(username, docTypeCode, status, applyDate, transactionCode);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), workflowTransList.size());
        if (start > workflowTransList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, workflowTransList.size());
        }
        List<WorkflowTransResponse> pageContent = workflowTransList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, workflowTransList.size());
    }

    public Page<WorkflowTransResponse> getMyTransaction(String username, String docTypeCode, String status, LocalDate applyDate, String transactionCode, Pageable pageable) {
        List<WorkflowTransResponse> workflowTransList = workflowTransRepository.getMyTransaction(username, docTypeCode, status, applyDate, transactionCode);

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), workflowTransList.size());
        if (start > workflowTransList.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, workflowTransList.size());
        }
        List<WorkflowTransResponse> pageContent = workflowTransList.subList(start, end);
        return new PageImpl<>(pageContent, pageable, workflowTransList.size());
    }

    public List<WorkflowApproverData> getApproverDetail(Long id){
        List<WorkflowApproverData> workflowApproverDataList = new ArrayList<>();
        WorkflowTrans workflowTrans = workflowTransRepository.findById(id).get();
        Integer maxLevel = workflowTransApproverRepository.findMaxLevel(id);
        for(int i = 1; i <= maxLevel; i++){
            Integer level = i;

            List<WorkflowApproverDetail> workflowApproverDetailList = new ArrayList<>();
            List<WorkflowTransApprover> workflowTransApproverList = workflowTransApproverRepository.findByLevelAndWorkflowTrans(level, workflowTrans);
            for(WorkflowTransApprover workflowTransApprover : workflowTransApproverList){
                WorkflowApproverDetail workflowApproverDetail = new WorkflowApproverDetail();
                workflowApproverDetail.setApproverNik(workflowTransApprover.getApprover().getUsername());
                workflowApproverDetail.setApproverName(workflowTransApprover.getApprover().getName());
                workflowApproverDetail.setApproveAction(workflowTransApprover.getApprove() == null ? null : workflowTransApprover.getApprove() ? "APPROVED" : "REJECTED");
                workflowApproverDetail.setApproveDescription(workflowTransApprover.getApprove() != null ? workflowTransApprover.getDescription() : null);
                workflowApproverDetail.setApproveTime(workflowTransApprover.getApprove() != null ? workflowTransApprover.getActionTime() : null);
                workflowApproverDetailList.add(workflowApproverDetail);
            }
            WorkflowApproverData workflowApproverData = new WorkflowApproverData();
            workflowApproverData.setLevel(level);
            workflowApproverData.setApproverDetail(workflowApproverDetailList);
            workflowApproverDataList.add(workflowApproverData);
        }
        return workflowApproverDataList;
    }

    public Object getTransactionDetail(Long id){
        WorkflowTrans workflowTrans = workflowTransRepository.findById(id).get();
        if(workflowTrans.getWorkflowDocType().getCode().equals("DAYPAY")){
            return dayPaymentRequestRepository.findById(workflowTrans.getDocId()).get();
        }
        return null;
    }

    public List<WorkflowApproveeDetail> getEmployeeDetail(String username){
        Object employeeData = proIntClient.getEmployeePositionNik(username).getData();
        List<WorkflowApproveeDetail> employees = objectMapper.convertValue(employeeData, new TypeReference<>(){});
        return employees;
    }

    public void generateTransaction(Long transactionId, String codeDocType, String username, String positionCode){
        User approvee = userRepository.findByusername(username).orElse(null);
        if(approvee == null){
            throw new ConflictException("User not found");
        }

        WorkflowDocType workflowDocType = workflowDocTypeRepository.findByCode(codeDocType);
        Object approveeData = proIntClient.getEmployeeData(username, positionCode).getData();
        WorkflowApproveeData approveeCriteria = objectMapper.convertValue(approveeData, new TypeReference<>(){});
        WorkflowTempApprovee workflowTempApprovee = workflowTempApproveeRepository.generateWorkflowTemp(workflowDocType.getId(), approveeCriteria.getCompCode(), approveeCriteria.getOrgCode(),
                approveeCriteria.getJobLvlCode(), approveeCriteria.getJobTtlCode(), approveeCriteria.getLocationCode(), approveeCriteria.getPosCode(),
                approveeCriteria.getEmpNIK());

        WorkflowTrans workflowTrans = new WorkflowTrans();
        workflowTrans.setCode(generateCode(codeDocType));
        workflowTrans.setWorkflowDocType(workflowDocType);
        workflowTrans.setDocId(transactionId);
        workflowTrans.setApprovee(approvee);
        workflowTrans.setWorkflowStatus(workflowStatusRepository.findByDescription("OPEN"));
        workflowTrans.setWorkflowTemp(workflowTempApprovee.getWorkflowTemp());
        workflowTrans = workflowTransRepository.save(workflowTrans);

        Integer maxLevelApprover = workflowTempApproverRepository.findMaxLevel(workflowTrans.getWorkflowTemp().getId());
        List<WorkflowTempApprover> workflowTempApproverList = workflowTempApproverRepository.generateWorkflowTemp(workflowTrans.getWorkflowTemp().getId());
        List<WorkflowTransApprover> workflowTransApproverList = new ArrayList<>();
        for(int i = 1; i <= maxLevelApprover; i++){
            Set<String> approverNikLevel = new HashSet<>();

            Integer level = i;
            Integer sequence = 1;
            List<WorkflowTempApprover> workflowTempApproverLevel = workflowTempApproverList.stream().filter(e -> e.getLevel().equals(level)).toList();
            List<String> compCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getCompanyCode).filter(Objects::nonNull).toList();
            List<String> orgCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getOrganizationCode).filter(Objects::nonNull).toList();
            List<String> jobLevelCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getJobLevelCode).filter(Objects::nonNull).toList();
            List<String> jobTitleCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getJobTitleCode).filter(Objects::nonNull).toList();
            List<String> positionCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getPositionCode).filter(Objects::nonNull).toList();
            List<String> locationCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getLocationCode).filter(Objects::nonNull).toList();
            List<String> employeeNiks = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getEmployeeNik).filter(Objects::nonNull).toList();
            List<String> orgChartCodes = workflowTempApproverLevel.stream().map(WorkflowTempApprover::getOrgChartCode).filter(Objects::nonNull).toList();

            Set<String> superiorNik = generateSuperior(username, positionCode, orgChartCodes);
            approverNikLevel.addAll(superiorNik);

            if(!compCodes.isEmpty() || !orgCodes.isEmpty() && !jobLevelCodes.isEmpty() && !jobTitleCodes.isEmpty() && !locationCodes.isEmpty() && !positionCodes.isEmpty() && !employeeNiks.isEmpty()) {
                Object employeeNIKData = proIntClient.getEmployeeAuth(compCodes, orgCodes, jobLevelCodes, jobTitleCodes, positionCodes, locationCodes, employeeNiks).getData();
                if (employeeNIKData != null) {
                    Set<String> employeeNIKList = objectMapper.convertValue(employeeNIKData, new TypeReference<>() {});
                    approverNikLevel.addAll(employeeNIKList);
                }
            }

            for(String approverNik : approverNikLevel){
                User approver = userRepository.findByusername(approverNik).orElse(null);
                if(approver == null){
                    throw new ConflictException("User Approver not found ("+approverNik+")");
                }

                if(workflowTransApproverList.stream().filter(e -> e.getApprover().equals(approver)).toList().size() <= 0){
                    WorkflowTransApprover workflowTransApprover = new WorkflowTransApprover();
                    workflowTransApprover.setWorkflowTrans(workflowTrans);
                    workflowTransApprover.setApprover(approver);
                    workflowTransApprover.setLevel(level);
                    workflowTransApprover.setSequence(sequence++);
                    workflowTransApproverList.add(workflowTransApprover);
                }
            }
        }
        for(WorkflowTransApprover workflowTransApprover : workflowTransApproverList){
            workflowTransApproverRepository.save(workflowTransApprover);
        }
    }

    public Set<String> generateSuperior(String empNik, String posCode, List<String> orgChartCodes){
        Set<String> superiorList = new HashSet<>();
        if(orgChartCodes.contains("S1")){
            String superiorNik = proIntClient.getEmployeeSuperior(empNik, posCode, 1).getData().toString();
            superiorList.add(superiorNik);
        } if(orgChartCodes.contains("S2")){
            String superiorNik = proIntClient.getEmployeeSuperior(empNik, posCode, 2).getData().toString();
            superiorList.add(superiorNik);
        } if(orgChartCodes.contains("S3")){
            String superiorNik = proIntClient.getEmployeeSuperior(empNik, posCode, 3).getData().toString();
            superiorList.add(superiorNik);
        } if(orgChartCodes.contains("S4")){
            String superiorNik = proIntClient.getEmployeeSuperior(empNik, posCode, 4).getData().toString();
            superiorList.add(superiorNik);
        } if(orgChartCodes.contains("S5")){
            String superiorNik = proIntClient.getEmployeeSuperior(empNik, posCode, 5).getData().toString();
            superiorList.add(superiorNik);
        }
        return superiorList;
    }

    public String generateCode(String documentType) {
        String randomCode = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase(Locale.ROOT);

        return documentType.toUpperCase(Locale.ROOT)
                + "-"
                + randomCode;
    }
}
