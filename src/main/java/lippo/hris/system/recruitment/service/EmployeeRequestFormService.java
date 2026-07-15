package lippo.hris.system.recruitment.service;

import jakarta.mail.MessagingException;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.authentication.response.UserResponsev2;
import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.entity.SystemParameter;
import lippo.hris.system.google.service.GoogleDriveService;
import lippo.hris.system.recruitment.entity.*;
import lippo.hris.system.recruitment.enumeration.*;
import lippo.hris.system.recruitment.repository.*;
import lippo.hris.system.recruitment.request.*;
import lippo.hris.system.recruitment.response.*;
import lippo.hris.system.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeRequestFormService {

    @Autowired
    EmployeeRequestFormRepository employeeRequestFormRepository;

    @Autowired
    EmployeeRequestPICRepository employeeRequestPICRepository;

    @Autowired
    EmployeeRequestCandidateRepository employeeRequestCandidateRepository;

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    @Autowired
    RecruitmentActivityService recruitmentActivityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecruitmentTemplateRepository recruitmentTemplateRepository;

    @Autowired
    EmployeeRequestPICService employeeRequestPICService;

    @Autowired
    EmployeeRequestCandidateService employeeRequestCandidateService;

    @Autowired
    RecruitmentActivityRepository recruitmentActivityRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    RecruitmentActivityTemplateRepository recruitmentActivityTemplateRepository;

    @Autowired
    BusinessUnitRepository businessUnitRepository;

    @Autowired
    HRBPRepository hrbpRepository;

    @Autowired
    EmployeeRequestCandidateActivityService employeeRequestCandidateActivityService;

    @Autowired
    CandidateAddressRepository candidateAddressRepository;

    @Autowired
    RecruitmentLevelTemplateRepository recruitmentLevelTemplateRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    CandidateContactMasterRepository candidateContactMasterRepository;

    @Autowired
    CandidateContactRepository candidateContactRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    EmployeeRequestEmailRepository employeeRequestEmailRepository;

    @Autowired
    EmployeeRequestResultRepository employeeRequestResultRepository;

    @Autowired
    GoogleDriveService googleDriveService;

    @Autowired
    EmployeeRequestHoldRepository employeeRequestHoldRepository;

    @Autowired
    EmployeeRequestLogExpDateRepository employeeRequestLogExpDateRepository;

    @Autowired
    CandidateLogSalaryRepository candidateLogSalaryRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    SystemParameterRepository systemParameterRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    EmploymentTypeRepository employmentTypeRepository;

    @Autowired
    PositionLevelRepository positionLevelRepository;

    @Autowired
    EmployeeRequestCancelRepository employeeRequestCancelRepository;

    public void addEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(employeeRequestReq.getTemplate()).get();
        RecruitmentLevelTemplate recruitmentLevelTemplate = recruitmentLevelTemplateRepository.findById(employeeRequestReq.getTemplateLevel()).get();
        BusinessUnit businessUnit = businessUnitRepository.findById(employeeRequestReq.getBusinessUnitId()).get();
        HRBP hrbp = hrbpRepository.findById(employeeRequestReq.getHrbpId()).get();
        EmploymentType employmentType = employmentTypeRepository.findById(employeeRequestReq.getEmploymentType()).get();
        PositionLevel positionLevel = positionLevelRepository.findById(employeeRequestReq.getPositionLevel() == null ? 0 : employeeRequestReq.getPositionLevel()).orElse(null);
        List<EmployeeRequestCandidate> employeeRequestCandidates = new ArrayList<>();

        EmployeeRequestForm employeeRequestForm = new EmployeeRequestForm();
        employeeRequestForm.setCode(generateRunningNumber());
        employeeRequestForm.setName(employeeRequestReq.getName());
        employeeRequestForm.setStartDate(employeeRequestReq.getStartDate());
        employeeRequestForm.setExpDate(employeeRequestReq.getExpDate());
        employeeRequestForm.setRecruitmentTemplate(recruitmentTemplate);
        employeeRequestForm.setRecruitmentLevelTemplate(recruitmentLevelTemplate);
        employeeRequestForm.setBusinessUnit(businessUnit);
        employeeRequestForm.setHrbp(hrbp);
        employeeRequestForm.setRequestNumber(employeeRequestReq.getRequestNumber());
        employeeRequestForm.setHiringName(employeeRequestReq.getHiringName());
        employeeRequestForm.setHiringPositionName(employeeRequestReq.getHiringPositionName());
        employeeRequestForm.setHiringCompanyName(employeeRequestReq.getHiringCompanyName());
        employeeRequestForm.setHiringDivisionName(employeeRequestReq.getHiringDivisionName());
        employeeRequestForm.setReportTo(employeeRequestReq.getReportTo());
        employeeRequestForm.setEmploymentType(employmentType);
        employeeRequestForm.setRequisitionType(employeeRequestReq.getRequisitionType());
        employeeRequestForm.setReplacementEmployee(employeeRequestReq.getReplacementEmployee());
        employeeRequestForm.setPositionLevel(positionLevel);
        employeeRequestForm.setWorkingDays(employeeRequestReq.getWorkingDays());
        employeeRequestForm.setJobDescription(employeeRequestReq.getJobDescription());
        employeeRequestForm.setEducationBackground(employeeRequestReq.getEducationBackground());
        employeeRequestForm.setMinimumExperience(employeeRequestReq.getMinimumExperience());
        employeeRequestForm.setSkillRequirement(employeeRequestReq.getSkillRequirement());
        employeeRequestForm = employeeRequestFormRepository.save(employeeRequestForm);

        employeeRequestPICService.save(employeeRequestForm, employeeRequestReq.getPic());

        for(Long candidateId : employeeRequestReq.getCandidate()){
            Candidate candidate = candidateRepository.findById(candidateId).get();
            EmployeeRequestCandidate employeeRequestCandidate = new EmployeeRequestCandidate();
            employeeRequestCandidate.setEmployeeRequestForm(employeeRequestForm);
            employeeRequestCandidate.setCandidate(candidate);
            employeeRequestCandidate = employeeRequestCandidateRepository.save(employeeRequestCandidate);
            employeeRequestCandidates.add(employeeRequestCandidate);
        }

        List<RecruitmentActivityTemplate> recruitmentActivityTemplateList =
                recruitmentActivityTemplateRepository.findByTemplate(employeeRequestForm.getRecruitmentTemplate());
        List<RecruitmentActivity> recruitmentActivityList = recruitmentActivityTemplateList.stream().map(RecruitmentActivityTemplate::getActivity).toList();

        for(EmployeeRequestCandidate employeeRequestCandidate : employeeRequestCandidates){
            for(RecruitmentActivity recruitmentActivity : recruitmentActivityList){
                EmployeeRequestCandidateActivity employeeRequestCandidateActivity = new EmployeeRequestCandidateActivity();
                employeeRequestCandidateActivity.setEmployeeRequestCandidate(employeeRequestCandidate);
                employeeRequestCandidateActivity.setRecruitmentActivity(recruitmentActivity);
                employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.NOT_STARTED.toString());
                employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
            }
        }
    }

    public void addInterview(AddInterviewReq addInterviewReq) {
        EmployeeRequestCandidate employeeRequestCandidate = employeeRequestCandidateRepository.findById(addInterviewReq.getId()).get();
        RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findByName(addInterviewReq.getName());

        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = new EmployeeRequestCandidateActivity();
        employeeRequestCandidateActivity.setEmployeeRequestCandidate(employeeRequestCandidate);
        employeeRequestCandidateActivity.setRecruitmentActivity(recruitmentActivity);
        employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.NOT_STARTED.toString());
        employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
    }

    public void processEmployeeRequest(EmployeeRequestReq employeeRequestReq) {
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(employeeRequestReq.getId()).get();
        employeeRequestForm.setStatus(EmployeeRequestFormStatus.IN_PROGRESS.toString());
        employeeRequestFormRepository.save(employeeRequestForm);
    }

    public void modifyEmployeeRequest(@RequestBody EmployeeRequestReq employeeRequestReq) {
        RecruitmentTemplate recruitmentTemplate = recruitmentTemplateRepository.findById(employeeRequestReq.getTemplate()).get();
        RecruitmentLevelTemplate recruitmentLevelTemplate = recruitmentLevelTemplateRepository.findById(employeeRequestReq.getTemplateLevel()).get();
        BusinessUnit businessUnit = businessUnitRepository.findById(employeeRequestReq.getBusinessUnitId()).get();
        HRBP hrbp = hrbpRepository.findById(employeeRequestReq.getHrbpId()).get();
        EmploymentType employmentType = employmentTypeRepository.findById(employeeRequestReq.getEmploymentType()).get();
        PositionLevel positionLevel = positionLevelRepository.findById(employeeRequestReq.getPositionLevel() == null ? 0 : employeeRequestReq.getPositionLevel()).orElse(null);

        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(employeeRequestReq.getId()).get();
        RecruitmentTemplate oldRecruitmentTemplate = employeeRequestForm.getRecruitmentTemplate();
        employeeRequestForm.setName(employeeRequestReq.getName());
        employeeRequestForm.setStartDate(employeeRequestReq.getStartDate());

        if(!employeeRequestForm.getExpDate().isEqual(employeeRequestReq.getExpDate())){
            EmployeeRequestLogExpDate employeeRequestLogExpDate = new EmployeeRequestLogExpDate();
            employeeRequestLogExpDate.setEmployeeRequestForm(employeeRequestForm);
            employeeRequestLogExpDate.setOldValue(employeeRequestForm.getExpDate());
            employeeRequestLogExpDate.setNewValue(employeeRequestReq.getExpDate());
            employeeRequestLogExpDateRepository.save(employeeRequestLogExpDate);

            employeeRequestForm.setExpDate(employeeRequestReq.getExpDate());
        }

        employeeRequestForm.setRecruitmentTemplate(recruitmentTemplate);
        employeeRequestForm.setRecruitmentLevelTemplate(recruitmentLevelTemplate);
        employeeRequestForm.setBusinessUnit(businessUnit);
        employeeRequestForm.setHrbp(hrbp);
        employeeRequestForm.setRequestNumber(employeeRequestReq.getRequestNumber());
        employeeRequestForm.setHiringName(employeeRequestReq.getHiringName());
        employeeRequestForm.setHiringPositionName(employeeRequestReq.getHiringPositionName());
        employeeRequestForm.setHiringCompanyName(employeeRequestReq.getHiringCompanyName());
        employeeRequestForm.setHiringDivisionName(employeeRequestReq.getHiringDivisionName());
        employeeRequestForm.setReportTo(employeeRequestReq.getReportTo());
        employeeRequestForm.setEmploymentType(employmentType);
        employeeRequestForm.setRequisitionType(employeeRequestReq.getRequisitionType());
        employeeRequestForm.setReplacementEmployee(employeeRequestReq.getReplacementEmployee());
        employeeRequestForm.setPositionLevel(positionLevel);
        employeeRequestForm.setWorkingDays(employeeRequestReq.getWorkingDays());
        employeeRequestForm.setJobDescription(employeeRequestReq.getJobDescription());
        employeeRequestForm.setEducationBackground(employeeRequestReq.getEducationBackground());
        employeeRequestForm.setMinimumExperience(employeeRequestReq.getMinimumExperience());
        employeeRequestForm.setSkillRequirement(employeeRequestReq.getSkillRequirement());
        employeeRequestForm = employeeRequestFormRepository.save(employeeRequestForm);

        employeeRequestPICService.modifyEmployeeRequestPIC(employeeRequestReq, employeeRequestForm);
        employeeRequestCandidateService.modifyEmployeeRequestCandidate(employeeRequestReq, employeeRequestForm);

        if(!oldRecruitmentTemplate.equals(recruitmentTemplate)){
            employeeRequestCandidateActivityService.modifyEmployeeRequestActivity(employeeRequestForm);
        }
    }

    public void scheduleEmployeeRequest(ScheduleEmployeeReq scheduleEmployeeReq) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity =
                employeeRequestCandidateActivityRepository.findById(scheduleEmployeeReq.getId()).get();

        if(scheduleEmployeeReq.getStatus().equals(EmployeeRequestFormActivityStatus.IN_PROGRESS.toString())){
            if(employeeRequestCandidateActivity.getSchedule() == null){
                employeeRequestCandidateActivity.setStartTime(LocalDateTime.now());
            }
            employeeRequestCandidateActivity.setSchedule(scheduleEmployeeReq.getScheduleTime());
            employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());

            if(employeeRequestCandidateActivity.getRecruitmentActivity().getFlagInterview()){
                Interview interview = interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
                if(interview == null){
                    interview = new Interview();
                    interview.setEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
                    interview.setCandidate(employeeRequestCandidateActivity.getEmployeeRequestCandidate().getCandidate());
                }
                interview.setInterviewerName(scheduleEmployeeReq.getInterviewerName());
                interview.setInterviewerPosition(scheduleEmployeeReq.getInterviewerPosition());
                interview.setInterviewType(scheduleEmployeeReq.getInterviewType());
                interview.setLinkInterview(scheduleEmployeeReq.getLinkInterview());

                if(scheduleEmployeeReq.getVenueId() != null){
                    Venue venue = venueRepository.findById(scheduleEmployeeReq.getVenueId()).orElse(null);
                    interview.setVenue(venue);
                }
                interviewRepository.save(interview);
            }
        }
        employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
    }

    public void scheduleEmployeeRequest(ScheduleMassEmployeeReq scheduleMassEmployeeReq) {
        RecruitmentActivity recruitmentActivity = recruitmentActivityRepository.findById(scheduleMassEmployeeReq.getActivityId()).get();

        for(ScheduleMassEmployeeDetailReq scheduleMassEmployeeDetailReq : scheduleMassEmployeeReq.getScheduleMassEmployeeDetailReq()){
            EmployeeRequestCandidate employeeRequestCandidate = employeeRequestCandidateRepository.findById(scheduleMassEmployeeDetailReq.getEmployeeCandidateId()).get();
            EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository
                    .findByEmployeeRequestCandidateAndRecruitmentActivity(employeeRequestCandidate, recruitmentActivity);
            employeeRequestCandidateActivity.setSchedule(scheduleMassEmployeeDetailReq.getScheduleTime());
            employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
            employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
        }
    }

    public void interviewEmployeeRequest(EmployeeRequestInterviewReq employeeRequestInterviewReq) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity =
                employeeRequestCandidateActivityRepository.findById(employeeRequestInterviewReq.getId()).get();
        Candidate candidate = employeeRequestCandidateActivity.getEmployeeRequestCandidate().getCandidate();
        CandidateAddress candidateAddress = candidateAddressRepository.findByCandidate(candidate).orElse(null);

        if(candidateAddress == null){
            candidateAddress = new CandidateAddress();
            candidateAddress.setCandidate(candidate);
        }

        if(employeeRequestInterviewReq.getAddress() != null){
            candidateAddress.setAddress(employeeRequestInterviewReq.getAddress());
            candidateAddressRepository.save(candidateAddress);
        }

        if(!Objects.equals(candidate.getCurrentSalary(), employeeRequestInterviewReq.getCurrentSalary())){
            CandidateLogSalary candidateLogSalary = new CandidateLogSalary();
            candidateLogSalary.setCandidate(candidate);
            candidateLogSalary.setField("Current");
            candidateLogSalary.setOldValue(candidate.getCurrentSalary());
            candidateLogSalary.setNewValue(employeeRequestInterviewReq.getCurrentSalary());
            candidateLogSalaryRepository.save(candidateLogSalary);

            candidate.setCurrentSalary(employeeRequestInterviewReq.getCurrentSalary());
        }

        if(!Objects.equals(candidate.getExpectedSalary(), employeeRequestInterviewReq.getExpectedSalary())){
            CandidateLogSalary candidateLogSalary = new CandidateLogSalary();
            candidateLogSalary.setCandidate(candidate);
            candidateLogSalary.setField("Expected");
            candidateLogSalary.setOldValue(candidate.getExpectedSalary());
            candidateLogSalary.setNewValue(employeeRequestInterviewReq.getExpectedSalary());
            candidateLogSalaryRepository.save(candidateLogSalary);

            candidate.setExpectedSalary(employeeRequestInterviewReq.getExpectedSalary());
        }

        candidate.setBirthDate(employeeRequestInterviewReq.getBirthDate());
        candidateRepository.save(candidate);

        Interview interview = interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
        interview.setStartTime(employeeRequestInterviewReq.getStartTime());
        interview.setEndTime(LocalDateTime.now());
        interview.setNotes(employeeRequestInterviewReq.getNotes());
        interviewRepository.save(interview);
    }

    public void cancelEmployeeRequest(EmployeeRequestReq employeeRequestReq) {
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(employeeRequestReq.getId()).get();
        employeeRequestForm.setStatus(EmployeeRequestFormStatus.CANCELLED.toString());
        employeeRequestFormRepository.save(employeeRequestForm);

        EmployeeRequestCancel employeeRequestCancel = new EmployeeRequestCancel();
        employeeRequestCancel.setEmployeeRequestForm(employeeRequestForm);
        employeeRequestCancel.setReason(employeeRequestReq.getReason());
        employeeRequestCancelRepository.save(employeeRequestCancel);
    }

    public void holdEmployeeRequest(EmployeeRequestReq employeeRequestReq) {
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(employeeRequestReq.getId()).get();
        employeeRequestForm.setStatus(EmployeeRequestFormStatus.ON_HOLD.toString());
        employeeRequestFormRepository.save(employeeRequestForm);

        EmployeeRequestHold employeeRequestHold = new EmployeeRequestHold();
        employeeRequestHold.setEmployeeRequestForm(employeeRequestForm);
        employeeRequestHold.setStartDate(LocalDate.now());
        employeeRequestHold.setReason(employeeRequestReq.getReason());
        employeeRequestHold.setOldStartDate(employeeRequestForm.getStartDate());
        employeeRequestHold.setOldExpiryDate(employeeRequestForm.getExpDate());
        employeeRequestHoldRepository.save(employeeRequestHold);
    }

    public void resumeEmployeeRequest(EmployeeRequestReq employeeRequestReq) {
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(employeeRequestReq.getId()).get();
        employeeRequestForm.setStatus(EmployeeRequestFormStatus.IN_PROGRESS.toString());

        List<EmployeeRequestHold> employeeRequestHolds = employeeRequestHoldRepository.findByEmployeeRequestForm(employeeRequestForm)
                .stream().filter(e -> e.getEndDate() == null).toList();
        for(EmployeeRequestHold employeeRequestHold : employeeRequestHolds){
            employeeRequestHold.setEndDate(LocalDate.now());
            employeeRequestHoldRepository.save(employeeRequestHold);

            if(employeeRequestReq.getResumeType().equals(EmployeeRequestFormResumeType.EXTEND.getLabel())){
                employeeRequestForm.setExpDate(employeeRequestForm.getExpDate().plusDays(ChronoUnit.DAYS.between(employeeRequestHold.getStartDate(), employeeRequestHold.getEndDate())));
            } else if(employeeRequestReq.getResumeType().equals(EmployeeRequestFormResumeType.RESET.getLabel())){
                employeeRequestForm.setStartDate(LocalDate.now());
                employeeRequestForm.setExpDate(LocalDate.now().plusDays(employeeRequestForm.getRecruitmentLevelTemplate().getDays()));
            }
            employeeRequestFormRepository.save(employeeRequestForm);
        }
    }

    public void emailEmployeeRequest(Long id, List<String> emailTo, List<String> emailCc,
                                     List<String> emailBcc, String subject, String body, MultipartFile file, String username) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();

        EmployeeRequestEmail employeeRequestEmail = new EmployeeRequestEmail();
        employeeRequestEmail.setEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
        employeeRequestEmail.setEmailTo(String.join(",", emailTo));
        employeeRequestEmail.setEmailCc(emailCc == null ? null : String.join(",", emailCc));
        employeeRequestEmail.setEmailBcc(emailBcc == null ? null : String.join(",", emailBcc));
        employeeRequestEmail.setEmailSubject(subject);
        employeeRequestEmail.setEmailBody(body);
        employeeRequestEmailRepository.save(employeeRequestEmail);

        Map<String, Object> params = generateParam(employeeRequestCandidateActivity, username);

        try{
            emailService.sendEmail(emailTo, emailCc, emailBcc, subject, body, params, file);
        }catch(IOException e){
        }catch(MessagingException m){
        }
    }

    public boolean actionEmployeeRequest(Long id, MultipartFile file, String result, String notes) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();
        String fileId = null;

        if(file != null){
            try{
                fileId = googleDriveService.upload(file, GoogleDriveRecruitmentFolder.RESULT_ACTIVITY_RECRUITMENT);
            }catch (Exception e){
            }
            employeeRequestCandidateActivity.setFileId(fileId);
        }

        employeeRequestCandidateActivity.setNotes(notes);
        employeeRequestCandidateActivity.setStatus(result);
        if(employeeRequestCandidateActivity.getStatus().equals(EmployeeRequestFormActivityStatus.COMPLETED.toString())){
            employeeRequestCandidateActivity.setCompletedTime(LocalDateTime.now());
        }
        employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);

        if(checkAllCompleted(employeeRequestCandidateActivity.getEmployeeRequestCandidate())){
            EmployeeRequestForm employeeRequestForm = employeeRequestCandidateActivity.getEmployeeRequestCandidate().getEmployeeRequestForm();
            Candidate candidate = employeeRequestCandidateActivity.getEmployeeRequestCandidate().getCandidate();

            EmployeeRequestResult employeeRequestResult = new EmployeeRequestResult();
            employeeRequestResult.setCandidate(candidate);
            employeeRequestResult.setEmployeeRequestForm(employeeRequestForm);
            employeeRequestResultRepository.save(employeeRequestResult);

            if(checkERFCompleted(employeeRequestForm)){
                employeeRequestForm.setStatus(EmployeeRequestFormStatus.COMPLETED.toString());
                employeeRequestFormRepository.save(employeeRequestForm);
                return true;
            }
        }
        return false;
    }

    public void revertEmployeeRequest(EmployeeRequestReq employeeRequestReq) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(employeeRequestReq.getId()).get();
        employeeRequestCandidateActivity.setStatus(EmployeeRequestFormActivityStatus.IN_PROGRESS.toString());
        employeeRequestCandidateActivityRepository.save(employeeRequestCandidateActivity);
    }

    public List<String> getResumeType() {
        return Arrays.stream(EmployeeRequestFormResumeType.values())
                .map(EmployeeRequestFormResumeType::getLabel)
                .toList();
    }

    public List<String> getSLAStatus() {
        List<String> slaStatus = new ArrayList<>();
        slaStatus.add(null);
        slaStatus.addAll(Arrays.stream(EmployeeRequestFormSLAStatus.values())
                .map(EmployeeRequestFormSLAStatus::getLabel)
                .toList());
        return slaStatus;
    }

    public List<String> getStatus() {
        List<String> status = new ArrayList<>();
        status.add(null);
        status.add("Sourcing");
        status.addAll(Arrays.stream(RecruitmentGroupActivity.values())
                .sorted(Comparator.comparing(RecruitmentGroupActivity::getOrder))
                .map(RecruitmentGroupActivity::getLabel)
                .toList());
        status.addAll(Arrays.stream(EmployeeRequestFormStatus.values())
                .filter(e -> !e.equals(EmployeeRequestFormStatus.IN_PROGRESS))
                .map(EmployeeRequestFormStatus::toString)
                .toList());
        return status;
    }

    public Page<EmployeeRequestResp> getEmployeeRequest(String code, String name, String buName, String hrbpName,
                                                        String username, List<String> roles, String pic, String sla, String status, Pageable pageable) {
        Boolean flagHRBP = false;
        if(roles.contains("ROLE_HRBP")){
            flagHRBP = true;
        }
        return employeeRequestFormRepository.getEmployeeRequest(code, name, buName, hrbpName, username, pic, sla, status, flagHRBP, pageable);
    }

    public List<EmployeeRequestCandidateResp> getEmployeeRequestCandidate(Long candidateId) {
        return employeeRequestFormRepository.getEmployeeRequestCandidate(candidateId);
    }

    public EmployeeRequestDetailResp getEmployeeRequestDetail(Long id){
        EmployeeRequestDetailResp employeeRequestDetailResp = new EmployeeRequestDetailResp();
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(id).get();
        employeeRequestDetailResp.setCode(employeeRequestForm.getCode());
        employeeRequestDetailResp.setName(employeeRequestForm.getName());
        employeeRequestDetailResp.setStartDate(employeeRequestForm.getStartDate());
        employeeRequestDetailResp.setExpiryDate(employeeRequestForm.getExpDate());
        employeeRequestDetailResp.setTemplate(employeeRequestForm.getRecruitmentTemplate());
        employeeRequestDetailResp.setLevelTemplate(employeeRequestForm.getRecruitmentLevelTemplate());
        employeeRequestDetailResp.setBusinessUnit(employeeRequestForm.getBusinessUnit());
        employeeRequestDetailResp.setHrbp(employeeRequestForm.getHrbp());
        employeeRequestDetailResp.setRequestNumber(employeeRequestForm.getRequestNumber());
        employeeRequestDetailResp.setHiringName(employeeRequestForm.getHiringName());
        employeeRequestDetailResp.setHiringPositionName(employeeRequestForm.getHiringPositionName());
        employeeRequestDetailResp.setHiringCompanyName(employeeRequestForm.getHiringCompanyName());
        employeeRequestDetailResp.setHiringDivisionName(employeeRequestForm.getHiringDivisionName());
        employeeRequestDetailResp.setReportTo(employeeRequestForm.getReportTo());
        employeeRequestDetailResp.setEmploymentType(employeeRequestForm.getEmploymentType());
        employeeRequestDetailResp.setRequisitionType(employeeRequestForm.getRequisitionType());
        employeeRequestDetailResp.setReplacementEmployee(employeeRequestForm.getReplacementEmployee());
        employeeRequestDetailResp.setPositionLevel(employeeRequestForm.getPositionLevel());
        employeeRequestDetailResp.setWorkingDays(employeeRequestForm.getWorkingDays());
        employeeRequestDetailResp.setJobDescription(employeeRequestForm.getJobDescription());
        employeeRequestDetailResp.setEducationBackground(employeeRequestForm.getEducationBackground());
        employeeRequestDetailResp.setMinimumExperience(employeeRequestForm.getMinimumExperience());
        employeeRequestDetailResp.setSkillRequirement(employeeRequestForm.getSkillRequirement());

        List<EmployeeRequestPIC> employeeRequestPIC = employeeRequestPICRepository.findByEmployeeRequestForm(employeeRequestForm);
        List<User> picList = employeeRequestPIC.stream().map(EmployeeRequestPIC::getUser).toList();
        employeeRequestDetailResp.setPic(picList);

        List<EmployeeRequestCandidate> employeeRequestCandidate = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);
        employeeRequestDetailResp.setCandidate(employeeRequestCandidate.stream().map(EmployeeRequestCandidate::getCandidate).collect(Collectors.toList()));
        return employeeRequestDetailResp;
    }

    public EmployeeRequestKanbanResp getEmployeeRequestKanban(Long id){
        EmployeeRequestKanbanResp employeeRequestKanbanResp = new EmployeeRequestKanbanResp();
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(id).get();
        employeeRequestKanbanResp.setName(employeeRequestForm.getName());

        List<String> stageList = recruitmentActivityService.getGroupActivity();
        stageList.add("Done");
        employeeRequestKanbanResp.setStage(stageList);

        List<EmployeeRequestCandidate> employeeRequestCandidateList = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);
        List<EmployeeRequestKanban> employeeRequestKanbanList = new ArrayList<>();
        for(EmployeeRequestCandidate employeeRequestCandidate : employeeRequestCandidateList){
            List<String> recruitmentTemplate = recruitmentActivityRepository.findProgressByEmployeeReqCandidate(employeeRequestCandidate.getId());

            String progressStage = null;
            for(String stage : employeeRequestKanbanResp.getStage()){
                if(recruitmentTemplate.contains(stage)){
                    progressStage = stage;
                    break;
                }
            }

            EmployeeRequestKanban employeeRequestKanban = new EmployeeRequestKanban();
            employeeRequestKanban.setId(employeeRequestCandidate.getId());
            employeeRequestKanban.setName(employeeRequestCandidate.getCandidate().getName());
            employeeRequestKanban.setStage(progressStage == null ? "Done" : progressStage);

            if(!employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidateAndStatus
                    (employeeRequestCandidate, EmployeeRequestFormActivityStatus.FAILED.toString()).isEmpty()){
                employeeRequestKanban.setStatus(EmployeeRequestFormActivityStatus.FAILED.toString());
            }

            employeeRequestKanbanList.add(employeeRequestKanban);
        }

        employeeRequestKanbanResp.setEmployeeStage(employeeRequestKanbanList);
        return employeeRequestKanbanResp;
    }

    public EmployeeRequestCandidateActivity getEmployeeRequestActivityDetail(Long id){
        return employeeRequestCandidateActivityRepository.findById(id).get();
    }

    public EmployeeRequestKanbanDetailResp getEmployeeRequestKanbanDetail(Long id, String stage){
        EmployeeRequestKanbanDetailResp employeeRequestKanbanDetailResp = new EmployeeRequestKanbanDetailResp();
        EmployeeRequestCandidate employeeRequestCandidate = employeeRequestCandidateRepository.findById(id).get();
        employeeRequestKanbanDetailResp.setName(employeeRequestCandidate.getCandidate().getName());
        employeeRequestKanbanDetailResp.setErfName(employeeRequestCandidate.getEmployeeRequestForm().getName());

        List<EmployeeRequestKanbanDetail> employeeRequestKanbanDetailList = new ArrayList<>();
        List<EmployeeRequestCandidateActivity> employeeRequestCandidateActivities = filterActivity(employeeRequestCandidateActivityRepository.
                findByEmployeeRequestCandidate(employeeRequestCandidate), stage);

        for(EmployeeRequestCandidateActivity employeeRequestCandidateActivity : employeeRequestCandidateActivities){
            EmployeeRequestKanbanDetail employeeRequestKanbanDetail = new EmployeeRequestKanbanDetail();
            employeeRequestKanbanDetail.setId(employeeRequestCandidateActivity.getId());
            employeeRequestKanbanDetail.setActivityName(employeeRequestCandidateActivity.getRecruitmentActivity().getName());
            employeeRequestKanbanDetail.setInterview(employeeRequestCandidateActivity.getRecruitmentActivity().getFlagInterview());
            employeeRequestKanbanDetail.setEmail(employeeRequestCandidateActivity.getRecruitmentActivity().getFlagEmail());

            if(employeeRequestKanbanDetail.getInterview()){
                Interview interview = interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
                if(interview != null){
                    employeeRequestKanbanDetail.setInterviewNotes(interview.getNotes());
                }
            }

            employeeRequestKanbanDetail.setStatus(employeeRequestCandidateActivity.getStatus());
            employeeRequestKanbanDetail.setScheduleTime(employeeRequestCandidateActivity.getSchedule());
            employeeRequestKanbanDetailList.add(employeeRequestKanbanDetail);
        }
        employeeRequestKanbanDetailResp.setListActivities(employeeRequestKanbanDetailList);

        if(!employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidateAndStatus
                (employeeRequestCandidate, EmployeeRequestFormActivityStatus.FAILED.toString()).isEmpty()){
            employeeRequestKanbanDetailResp.setStatus(EmployeeRequestFormActivityStatus.FAILED.toString());
        }
        return employeeRequestKanbanDetailResp;
    }

    public List<RecruitmentActivity> getEmployeeRequestActivitySchedule(Long id){
        return recruitmentActivityRepository.getEmployeeRequestActivitySchedule(id);
    }

    public EmployeeRequestEmailResp getEmployeeRequestEmailDetail(Long id, String username){
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();
        EmailTemplate emailTemplate =
                employeeRequestCandidateActivity.getRecruitmentActivity().getEmailTemplate();
        if(employeeRequestCandidateActivity.getStatus().equals(EmployeeRequestFormActivityStatus.FAILED.toString())){
            SystemParameter systemParameter = systemParameterRepository.findByKey("Recruitment Rejection Template Email");
            emailTemplate = emailTemplateRepository.findByCode(systemParameter.getValue());
        }
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Email").get();
        CandidateContact candidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(
                employeeRequestCandidateActivity.getEmployeeRequestCandidate().getCandidate().getId(),
                candidateContactMaster.getId()).get();

        List<String> emailTos = new ArrayList<>();
        emailTos.add(candidateContact.getContact());

        Map<String, Object> params = generateParam(employeeRequestCandidateActivity, username);

        EmployeeRequestEmailResp employeeRequestEmailResp = new EmployeeRequestEmailResp();
        employeeRequestEmailResp.setEmailTo(emailTos);
        employeeRequestEmailResp.setSubject(emailTemplate.getSubject());
        employeeRequestEmailResp.setBody(emailService.render(emailTemplate.getContentHtml(), params));

        return employeeRequestEmailResp;
    }

    public List<String> getEmployeeRequestResult(Long id){
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(id).get();
        return employeeRequestResultRepository.findByEmployeeRequestForm(employeeRequestForm).stream()
                .map(e -> e.getCandidate().getName()).collect(Collectors.toList());
    }

    public ScheduleResp getEmployeeRequestSchedule(Long id){
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).orElse(null);
        Interview interview = interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);

        ScheduleResp scheduleResp = new ScheduleResp();
        scheduleResp.setScheduleDate(employeeRequestCandidateActivity.getSchedule());

        if(interview != null){
            scheduleResp.setInterviewerName(interview.getInterviewerName());
            scheduleResp.setInterviewerPosition(interview.getInterviewerPosition());
            scheduleResp.setInterviewType(interview.getInterviewType());
            scheduleResp.setLinkInterview(interview.getLinkInterview());
            scheduleResp.setVenue(interview.getVenue());
        }

        return scheduleResp;
    }

    public List<String> getRequisitionType() {
        List<String> requisitionType = new ArrayList<>();
        requisitionType.addAll(Arrays.stream(RequisitionType.values())
                .map(RequisitionType::getLabel)
                .toList());
        return requisitionType;
    }

    public void deleteEmployeeRequest(Long id) {
        EmployeeRequestForm employeeRequestForm = employeeRequestFormRepository.findById(id).get();
        List<EmployeeRequestCandidate> employeeRequestCandidateList = employeeRequestCandidateRepository.findByEmployeeRequestForm(employeeRequestForm);

        for(EmployeeRequestCandidate employeeRequestCandidate : employeeRequestCandidateList){
            List<EmployeeRequestCandidateActivity> employeeRequestCandidateActivities =
                    employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidate(employeeRequestCandidate);
            employeeRequestCandidateActivityRepository.deleteAll(employeeRequestCandidateActivities);
            employeeRequestCandidateRepository.delete(employeeRequestCandidate);
        }
        employeeRequestPICRepository.deleteAllByEmployeeRequestForm(employeeRequestForm);
        employeeRequestFormRepository.deleteById(id);
    }

    private String generateRunningNumber(){
        String prefix = "ERF";
        Long runningNumber = employeeRequestFormRepository.countByEmpReqCodeStartingWith(prefix);
        if(runningNumber != null){
            return prefix + String.format("%07d", runningNumber + 1);
        }else{
            return prefix + String.format("%07d", 1);
        }
    }

    private Boolean checkAllCompleted(EmployeeRequestCandidate employeeRequestCandidate){
        return employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidate(employeeRequestCandidate)
                .equals(employeeRequestCandidateActivityRepository.findByEmployeeRequestCandidateAndStatus
                        (employeeRequestCandidate, EmployeeRequestFormActivityStatus.COMPLETED.toString()));
    }

    private Boolean checkERFCompleted(EmployeeRequestForm employeeRequestForm){
        return employeeRequestForm.getRequestNumber() == employeeRequestResultRepository.findByEmployeeRequestForm(employeeRequestForm).size();
    }

    private List<EmployeeRequestCandidateActivity> filterActivity(List<EmployeeRequestCandidateActivity> employeeRequestCandidateActivities,
                                                                  String selectedStage){
        List<EmployeeRequestCandidateActivity> result = new ArrayList<>();
        List<String> stages = recruitmentActivityService.getGroupActivity();
        for(String stage : stages){
            result.addAll(employeeRequestCandidateActivities.stream().filter(e ->
                    e.getRecruitmentActivity().getGroup().equalsIgnoreCase(stage))
                    .sorted(Comparator.comparing(e -> e.getRecruitmentActivity().getName())).toList());
            if(stage.equalsIgnoreCase(selectedStage)){
                break;
            }
        }
        return result;
    }

    private Map<String, Object> generateParam(EmployeeRequestCandidateActivity employeeRequestCandidateActivity, String username){
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        UserResponsev2 userResponse = userRepository.findByUsername(username);
        Interview interview = interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);

        Map<String, Object> params = new HashMap<>();
        params.put("Candidate Name", employeeRequestCandidateActivity.getEmployeeRequestCandidate().getCandidate().getName());
        params.put("Schedule Date", employeeRequestCandidateActivity.getSchedule().toLocalDate().format(formatterDate));
        params.put("Schedule Time", employeeRequestCandidateActivity.getSchedule().toLocalTime().format(formatterTime));
        params.put("Position Name", employeeRequestCandidateActivity.getEmployeeRequestCandidate().getEmployeeRequestForm().getName());
        params.put("Recruiter Name", userResponse.getName());
        params.put("Company Name", employeeRequestCandidateActivity.getEmployeeRequestCandidate().getEmployeeRequestForm().getBusinessUnit().getName());
        params.put("Interviewer Name", interview == null ? null : interview.getInterviewerName());
        params.put("Interviewer Position", interview == null ? null : interview.getInterviewerPosition());
        params.put("Venue", interview == null ? null : interview.getVenue() == null ? interview.getLinkInterview() : interview.getVenue().getName());

        return params;
    }
}
