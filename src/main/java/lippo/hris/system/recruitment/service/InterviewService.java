package lippo.hris.system.recruitment.service;

import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.emailengine.service.IcsService;
import lippo.hris.system.exception.NotFoundException;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.EmployeeRequestCandidateActivity;
import lippo.hris.system.recruitment.entity.Interview;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.recruitment.repository.EmployeeRequestCandidateActivityRepository;
import lippo.hris.system.recruitment.repository.InterviewRepository;
import lippo.hris.system.recruitment.request.InterviewReq;
import lippo.hris.system.recruitment.response.InterviewResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@Transactional
public class InterviewService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IcsService icsService;

    @Autowired
    private CandidateContactService candidateContactService;

    @Autowired
    EmployeeRequestCandidateActivityRepository employeeRequestCandidateActivityRepository;

    public void saveInterview(InterviewReq interviewReq) {
        Candidate candidate = candidateRepository.findById(interviewReq.getCandidateId()).orElse(null);

        String icsId = UUID.randomUUID().toString();
        Interview interview = new Interview();
        interview.setCandidate(candidate);
        interview.setStartTime(interviewReq.getStartTime());
        interview.setEndTime(interviewReq.getEndTime());
        interview = interviewRepository.save(interview);

        String email = candidateContactService.getEmail(candidate);
        if(email == null){
            throw new NotFoundException("Email not found");
        }

        ZonedDateTime start = ZonedDateTime.of(interview.getStartTime().getYear(), interview.getStartTime().getMonthValue(),
                interview.getStartTime().getDayOfMonth(), interview.getStartTime().getHour(), interview.getStartTime().getMinute(),
                interview.getStartTime().getSecond(), 0, ZoneId.of("Asia/Jakarta"));
        ZonedDateTime end = ZonedDateTime.of(interview.getEndTime().getYear(), interview.getEndTime().getMonthValue(),
                interview.getEndTime().getDayOfMonth(), interview.getEndTime().getHour(), interview.getEndTime().getMinute(),
                interview.getEndTime().getSecond(), 0, ZoneId.of("Asia/Jakarta"));
        String ics = icsService.createIcs(icsId, "Interview Candidate",
                "Ya interview aja", "Zoom", start, end, email, 0);

        try{
            emailService.sendEmailIcs(email, "New Candidate", "Welcome to Lippo Indonesia Recruitment!", ics);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void modifyInterview(InterviewReq interviewReq) {
        Interview interview = interviewRepository.findById(interviewReq.getId()).orElse(null);
        interview.setStartTime(interviewReq.getStartTime());
        interview.setEndTime(interviewReq.getEndTime());
        interviewRepository.save(interview);

        String email = candidateContactService.getEmail(interview.getCandidate());
        if(email == null){
            throw new NotFoundException("Email not found");
        }

        ZonedDateTime start = ZonedDateTime.of(interview.getStartTime().getYear(), interview.getStartTime().getMonthValue(),
                interview.getStartTime().getDayOfMonth(), interview.getStartTime().getHour(), interview.getStartTime().getMinute(),
                interview.getStartTime().getSecond(), 0, ZoneId.of("Asia/Jakarta"));
        ZonedDateTime end = ZonedDateTime.of(interview.getEndTime().getYear(), interview.getEndTime().getMonthValue(),
                interview.getEndTime().getDayOfMonth(), interview.getEndTime().getHour(), interview.getEndTime().getMinute(),
                interview.getEndTime().getSecond(), 0, ZoneId.of("Asia/Jakarta"));
        String ics = icsService.createIcs("0", "Interview Candidate",
                "Ya interview aja", "Zoom", start, end, email, 0);

        try{
            emailService.sendEmailIcs(email, "New Candidate", "Welcome to Lippo Indonesia Recruitment!", ics);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Page<InterviewResp> getInterview(Pageable pageable) {
        return interviewRepository.getInterview(pageable);
    }

    public Interview getInterviewDetail(Long id) {
        return interviewRepository.findById(id).orElse(null);
    }

    public Interview getInterviewByAction(Long id) {
        EmployeeRequestCandidateActivity employeeRequestCandidateActivity = employeeRequestCandidateActivityRepository.findById(id).get();
        return interviewRepository.findByEmployeeRequestCandidateActivity(employeeRequestCandidateActivity);
    }

    public void deleteInterview(Long id) {
        Interview interview = interviewRepository.findById(id).orElse(null);
        String email = candidateContactService.getEmail(interview.getCandidate());
        if(email == null){
            throw new NotFoundException("Email not found");
        }

        String ics = icsService.cancelIcs("0", email, "Cancel Interview", "Sorry!",
                0, interview.getStartTime().atZone(ZoneId.of("Asia/Jakarta")));
        try{
            emailService.sendEmailIcs(email, "Cancel Interview", "Sorry!", ics);
        }catch(Exception e){
            e.printStackTrace();
        }
        interviewRepository.deleteById(id);
    }
}
