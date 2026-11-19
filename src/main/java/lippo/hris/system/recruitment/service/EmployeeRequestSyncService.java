package lippo.hris.system.recruitment.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.recruitment.entity.*;
import lippo.hris.system.recruitment.repository.*;
import lippo.hris.system.recruitment.request.EmployeeRequestEmployeeDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestPersonalDataReq;
import lippo.hris.system.recruitment.request.EmployeeRequestSyncReq;
import lippo.hris.system.recruitment.request.RCCandidateFeignReq;
import lippo.hris.system.recruitment.response.EmployeeRequestResultPersonalResp;
import lippo.hris.system.recruitment.response.RelatedPositionInfoResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeRequestSyncService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeRequestResultRepository employeeRequestResultRepository;

    @Autowired
    EmployeeRequestResultLinkRepository employeeRequestResultLinkRepository;

    @Autowired
    EmployeeRequestResultEmployeeDtRepository employeeRequestResultEmployeeDtRepository;

    @Autowired
    EmployeeRequestResultPersonalDtRepository employeeRequestResultPersonalDtRepository;

    @Autowired
    CandidateAddressRepository candidateAddressRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateContactRepository candidateContactRepository;

    @Autowired
    CandidateContactMasterRepository candidateContactMasterRepository;

    public void syncEmployeeRequest(EmployeeRequestSyncReq employeeRequestSyncReq, String username){
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(employeeRequestSyncReq.getResultId()).orElse(null);
        EmployeeRequestResultEmployeeDt employeeRequestResultEmployeeDt = employeeRequestResultEmployeeDtRepository.findByEmployeeRequestResult(employeeRequestResult);
        if(employeeRequestResultEmployeeDt == null){
            throw new ConflictException("Employee Data Not Found");
        }

        EmployeeRequestResultPersonalDt employeeRequestResultPersonalDt = employeeRequestResultPersonalDtRepository.findByEmployeeRequestResult(employeeRequestResult);
        if(employeeRequestResultPersonalDt == null){
            throw new ConflictException("Personal Data Not Found");
        }

        Optional<CandidateAddress> candidateAddress = candidateAddressRepository.findByCandidate(employeeRequestResult.getCandidate());
        if(candidateAddress.isEmpty()){
            throw new ConflictException("Candidate Address Not Found");
        }

        CandidateContactMaster candidateContactMasterPhone = candidateContactMasterRepository.findByType("Mobile Phone").get();
        Optional<CandidateContact> candidateMobilePhone = candidateContactRepository.findByCandidateAndCandidateContactMaster(employeeRequestResult.getCandidate().getId(), candidateContactMasterPhone.getId());
        if(candidateMobilePhone.isEmpty()) {
            throw new ConflictException("Candidate Mobile Phone Number Not Found");
        }

        CandidateContactMaster candidateContactMasterEmail = candidateContactMasterRepository.findByType("Email").get();
        Optional<CandidateContact> candidateEmail = candidateContactRepository.findByCandidateAndCandidateContactMaster(employeeRequestResult.getCandidate().getId(), candidateContactMasterEmail.getId());
        if(candidateEmail.isEmpty()) {
            throw new ConflictException("Candidate Email Not Found");
        }

        RCCandidateFeignReq rcCandidateFeignReq = new RCCandidateFeignReq();
        rcCandidateFeignReq.setCanName(employeeRequestResult.getCandidate().getName());
        rcCandidateFeignReq.setBirthDate(employeeRequestResult.getCandidate().getBirthDate());
        rcCandidateFeignReq.setGender(employeeRequestResultPersonalDt.getGender());
        rcCandidateFeignReq.setMaritalStatus(employeeRequestResultPersonalDt.getMaritalStatus());
        rcCandidateFeignReq.setReligion(employeeRequestResultPersonalDt.getReligion());
        rcCandidateFeignReq.setInitiatorNik(username);
        rcCandidateFeignReq.setResAddress(candidateAddress.get().getAddress());
        rcCandidateFeignReq.setOriAddress(candidateAddress.get().getAddress());
        rcCandidateFeignReq.setPhoneNumber(candidateMobilePhone.get().getContact());
        rcCandidateFeignReq.setEmail(candidateEmail.get().getContact());
        rcCandidateFeignReq.setPositionCode(employeeRequestResultEmployeeDt.getPositionCode());
        rcCandidateFeignReq.setJobLvlCode(employeeRequestResultEmployeeDt.getJobLevelCode());
        rcCandidateFeignReq.setJobTtlCode(employeeRequestResultEmployeeDt.getJobTitleCode());
        rcCandidateFeignReq.setOrgCode(employeeRequestResultEmployeeDt.getOrganizationCode());
        rcCandidateFeignReq.setCompanyCode(employeeRequestResultEmployeeDt.getCompanyCode());
        rcCandidateFeignReq.setLocationCode(employeeRequestResultEmployeeDt.getLocationCode());
        rcCandidateFeignReq.setCostCenterCode(employeeRequestResultEmployeeDt.getCostCenterCode());
        rcCandidateFeignReq.setEmpType(employeeRequestResultEmployeeDt.getEmploymentType());
        rcCandidateFeignReq.setJobStartDate(employeeRequestResultEmployeeDt.getJobStartDate());
        rcCandidateFeignReq.setJobEndDate(employeeRequestResultEmployeeDt.getJobEndDate());
        rcCandidateFeignReq.setLocationRecruitCode(employeeRequestResultEmployeeDt.getLocationRecruitCode());
        rcCandidateFeignReq.setEffectiveDate(employeeRequestResultEmployeeDt.getEffectiveDate());
        rcCandidateFeignReq.setStartDate(employeeRequestResultEmployeeDt.getStartDate());
        rcCandidateFeignReq.setJoinDate(employeeRequestResultEmployeeDt.getJoinDate());
        rcCandidateFeignReq.setSignDate(employeeRequestResultEmployeeDt.getSignDate());
        rcCandidateFeignReq.setKtpNumber(employeeRequestResultPersonalDt.getKtpNumber());

        Object result = proIntClient.syncCandidate(rcCandidateFeignReq).getData();
        Long insertedCanId = Long.parseLong(result.toString());
        employeeRequestResult.setCanProIntId(insertedCanId);
        employeeRequestResult.setSyncFlag(true);
        employeeRequestResultRepository.save(employeeRequestResult);
    }

    public void inputEmployeeData(EmployeeRequestEmployeeDataReq employeeRequestEmployeeDataReq){
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(employeeRequestEmployeeDataReq.getResultId()).get();
        EmployeeRequestResultEmployeeDt employeeRequestResultEmployeeDt = employeeRequestResultEmployeeDtRepository.findByEmployeeRequestResult(employeeRequestResult);

        if(employeeRequestResultEmployeeDt == null){
            employeeRequestResultEmployeeDt = new EmployeeRequestResultEmployeeDt();
        }

        employeeRequestResultEmployeeDt.setEmployeeRequestResult(employeeRequestResult);
        employeeRequestResultEmployeeDt.setEffectiveDate(employeeRequestEmployeeDataReq.getStartDate());
        employeeRequestResultEmployeeDt.setStartDate(employeeRequestEmployeeDataReq.getStartDate());
        employeeRequestResultEmployeeDt.setSignDate(employeeRequestEmployeeDataReq.getSignDate());
        employeeRequestResultEmployeeDt.setJoinDate(employeeRequestEmployeeDataReq.getJoinDate());
        employeeRequestResultEmployeeDt.setCompanyCode(employeeRequestEmployeeDataReq.getCompCode());
        employeeRequestResultEmployeeDt.setCompanyName(employeeRequestEmployeeDataReq.getCompName());
        employeeRequestResultEmployeeDt.setOrganizationCode(employeeRequestEmployeeDataReq.getOrgCode());
        employeeRequestResultEmployeeDt.setOrganizationName(employeeRequestEmployeeDataReq.getOrgName());
        employeeRequestResultEmployeeDt.setJobLevelCode(employeeRequestEmployeeDataReq.getJobLvlCode());
        employeeRequestResultEmployeeDt.setJobLevelName(employeeRequestEmployeeDataReq.getJobLvlName());
        employeeRequestResultEmployeeDt.setJobTitleCode(employeeRequestEmployeeDataReq.getJobTtlCode());
        employeeRequestResultEmployeeDt.setJobTitleName(employeeRequestEmployeeDataReq.getJobTtlName());
        employeeRequestResultEmployeeDt.setLocationCode(employeeRequestEmployeeDataReq.getLocCode());
        employeeRequestResultEmployeeDt.setLocationName(employeeRequestEmployeeDataReq.getLocName());
        employeeRequestResultEmployeeDt.setPositionCode(employeeRequestEmployeeDataReq.getPosCode());
        employeeRequestResultEmployeeDt.setPositionName(employeeRequestEmployeeDataReq.getPosName());
        employeeRequestResultEmployeeDt.setCostCenterCode(employeeRequestEmployeeDataReq.getCostCenterCode());
        employeeRequestResultEmployeeDt.setCostCenterName(employeeRequestEmployeeDataReq.getCostCenterName());
        employeeRequestResultEmployeeDt.setEmploymentType(employeeRequestEmployeeDataReq.getEmploymentType());
        employeeRequestResultEmployeeDt.setEmploymentTypeMonth(employeeRequestEmployeeDataReq.getEmploymentTypeMonth());
        employeeRequestResultEmployeeDt.setLocationRecruitCode(employeeRequestEmployeeDataReq.getLocRecruitCode());
        employeeRequestResultEmployeeDt.setLocationRecruitName(employeeRequestEmployeeDataReq.getLocRecruitName());
        employeeRequestResultEmployeeDt.setJobStartDate(employeeRequestEmployeeDataReq.getJobStartDate());
        employeeRequestResultEmployeeDt.setJobEndDate(employeeRequestEmployeeDataReq.getJobEndDate());
        employeeRequestResultEmployeeDtRepository.save(employeeRequestResultEmployeeDt);
    }

    public void inputPersonalData(EmployeeRequestPersonalDataReq employeeRequestPersonalDataReq){
        EmployeeRequestResultPersonalDt employeeRequestResultPersonalDt = new EmployeeRequestResultPersonalDt();
        EmployeeRequestResult employeeRequestResult = new EmployeeRequestResult();

        if(employeeRequestPersonalDataReq.getResultId() != null){
            employeeRequestResult = employeeRequestResultRepository.findById(employeeRequestPersonalDataReq.getResultId()).get();
            employeeRequestResultPersonalDt = employeeRequestResultPersonalDtRepository.findByEmployeeRequestResult(employeeRequestResult);
        } else if(employeeRequestPersonalDataReq.getToken() != null){
            EmployeeRequestResultLink employeeRequestResultLink = employeeRequestResultLinkRepository.findByToken(employeeRequestPersonalDataReq.getToken());
            employeeRequestResult = employeeRequestResultLink.getEmployeeRequestResult();
            employeeRequestResultPersonalDt = employeeRequestResultPersonalDtRepository.findByEmployeeRequestResult(employeeRequestResult);
        }

        if(employeeRequestResultPersonalDt == null){
            employeeRequestResultPersonalDt = new EmployeeRequestResultPersonalDt();
        }

        Candidate candidate = employeeRequestResult.getCandidate();
        CandidateAddress candidateAddress = candidateAddressRepository.findByCandidate(candidate).orElse(new CandidateAddress());
        CandidateContactMaster candidateContactMasterPhone = candidateContactMasterRepository.findByType("Mobile Phone").get();
        CandidateContact candidateMobilePhone = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMasterPhone.getId()).orElse(new CandidateContact());
        CandidateContactMaster candidateContactMasterEmail = candidateContactMasterRepository.findByType("Email").get();
        CandidateContact candidateEmail = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMasterEmail.getId()).orElse(new CandidateContact());
        candidate.setName(employeeRequestPersonalDataReq.getName());
        candidate.setBirthDate(employeeRequestPersonalDataReq.getBirthDate());
        candidateRepository.save(candidate);

        candidateAddress.setCandidate(candidate);
        candidateAddress.setAddress(employeeRequestPersonalDataReq.getAddress());
        candidateAddressRepository.save(candidateAddress);

        candidateMobilePhone.setCandidate(candidate);
        candidateMobilePhone.setType(candidateContactMasterPhone);
        candidateMobilePhone.setContact(employeeRequestPersonalDataReq.getPhoneNumber());
        candidateContactRepository.save(candidateMobilePhone);

        candidateEmail.setCandidate(candidate);
        candidateEmail.setType(candidateContactMasterEmail);
        candidateEmail.setContact(employeeRequestPersonalDataReq.getEmail());
        candidateContactRepository.save(candidateEmail);

        employeeRequestResultPersonalDt.setEmployeeRequestResult(employeeRequestResult);
        employeeRequestResultPersonalDt.setKtpNumber(employeeRequestPersonalDataReq.getKtpNumber());
        employeeRequestResultPersonalDt.setGender(employeeRequestPersonalDataReq.getGender());
        employeeRequestResultPersonalDt.setMaritalStatus(employeeRequestPersonalDataReq.getMaritalStatus());
        employeeRequestResultPersonalDt.setReligion(employeeRequestPersonalDataReq.getReligion());
        employeeRequestResultPersonalDtRepository.save(employeeRequestResultPersonalDt);
    }

    public EmployeeRequestResultEmployeeDt getEmployeeData(Long id){
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(id).get();
        return employeeRequestResultEmployeeDtRepository.findByEmployeeRequestResult(employeeRequestResult);
    }

    public EmployeeRequestResultPersonalResp getPersonalData(Long id){
        EmployeeRequestResult employeeRequestResult = employeeRequestResultRepository.findById(id).get();
        return getPersonalDataDetail(employeeRequestResult);
    }

    public EmployeeRequestResultPersonalResp getPersonalData(String token){
        EmployeeRequestResultLink employeeRequestResultLink = employeeRequestResultLinkRepository.findByToken(token);
        EmployeeRequestResult employeeRequestResult = employeeRequestResultLink.getEmployeeRequestResult();
        return getPersonalDataDetail(employeeRequestResult);
    }

    public RelatedPositionInfoResp getRelatedInfoPosition(String posCode) {
        if(posCode == null){
            return null;
        }
        Object relatedData = proIntClient.getRelatedPosition(posCode).getData();
        RelatedPositionInfoResp relatedInfo = objectMapper.convertValue(relatedData, new TypeReference<>(){});
        return relatedInfo;
    }

    public List<String> getMaritalStatusList(){
        Object maritalStatusData = proIntClient.getAllMaritalStatus().getData();
        List<String> maritalStatusList = objectMapper.convertValue(maritalStatusData, new TypeReference<>(){});
        return maritalStatusList;
    }

    public List<String> getReligionList(){
        Object religionData = proIntClient.getAllReligion().getData();
        List<String> religionList = objectMapper.convertValue(religionData, new TypeReference<>(){});
        return religionList;
    }

    public List<String> getGenderList(){
        Object genderData = proIntClient.getAllGender().getData();
        List<String> genderList = objectMapper.convertValue(genderData, new TypeReference<>(){});
        return genderList;
    }

    private EmployeeRequestResultPersonalResp getPersonalDataDetail(EmployeeRequestResult employeeRequestResult){
        EmployeeRequestResultPersonalDt employeeRequestResultPersonalDt = employeeRequestResultPersonalDtRepository.findByEmployeeRequestResult(employeeRequestResult);
        Candidate candidate = employeeRequestResult.getCandidate();
        CandidateAddress candidateAddress = candidateAddressRepository.findByCandidate(candidate).orElse(null);
        CandidateContactMaster candidateContactMasterPhone = candidateContactMasterRepository.findByType("Mobile Phone").get();
        CandidateContact candidateMobilePhone = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMasterPhone.getId()).orElse(null);
        CandidateContactMaster candidateContactMasterEmail = candidateContactMasterRepository.findByType("Email").get();
        CandidateContact candidateEmail = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMasterEmail.getId()).orElse(null);

        EmployeeRequestResultPersonalResp employeeRequestResultPersonalResp = new EmployeeRequestResultPersonalResp();
        employeeRequestResultPersonalResp.setName(candidate.getName());
        employeeRequestResultPersonalResp.setBirthDate(candidate.getBirthDate());
        employeeRequestResultPersonalResp.setAddress(candidateAddress == null ? null : candidateAddress.getAddress());
        employeeRequestResultPersonalResp.setMobilePhone(candidateMobilePhone == null ? null : candidateMobilePhone.getContact());
        employeeRequestResultPersonalResp.setEmail(candidateEmail == null ? null : candidateEmail.getContact());
        employeeRequestResultPersonalResp.setPersonalData(employeeRequestResultPersonalDt);
        return employeeRequestResultPersonalResp;
    }
}
