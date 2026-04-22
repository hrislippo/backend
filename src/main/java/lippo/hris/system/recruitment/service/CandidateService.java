package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.ocrengine.response.OcrResponse;
import lippo.hris.system.ocrengine.service.LinkedinOcrService;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAddress;
import lippo.hris.system.recruitment.entity.Interview;
import lippo.hris.system.recruitment.repository.CandidateAddressRepository;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.recruitment.repository.EmployeeRequestCandidateRepository;
import lippo.hris.system.recruitment.repository.InterviewRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import lippo.hris.system.recruitment.request.CandidateShortlistReq;
import lippo.hris.system.recruitment.response.CandidateResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateSkillService candidateSkillService;

    @Autowired
    CandidateCertificationService candidateCertificationService;

    @Autowired
    CandidateAchievementService candidateAchievementService;

    @Autowired
    CandidatePublicationService candidatePublicationService;

    @Autowired
    CandidateLanguageService candidateLanguageService;

    @Autowired
    CandidateEducationService candidateEducationService;

    @Autowired
    CandidateExperienceService candidateExperienceService;

    @Autowired
    CandidateAddressService candidateAddressService;

    @Autowired
    CandidateContactService candidateContactService;

    @Autowired
    CandidateSocialMediaService candidateSocialMediaService;

    @Autowired
    EmployeeRequestCandidateRepository employeeRequestCandidateRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CandidateAddressRepository candidateAddressRepository;

    @Autowired
    InterviewRepository interviewRepository;

    public String generateRunningNumber(){
        String prefix = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Long runningNumber = candidateRepository.countByCandidateNumberStartingWith(prefix);
        if(runningNumber != null){
            return String.valueOf(runningNumber + 1);
        }else{
            return prefix + String.format("%04d", 1);
        }
    }

    public void saveCandidate(CandidateReq candidateReq){
        Candidate candidate = new Candidate();
        User user = userRepository.findById(candidateReq.getUserId()).get();
        candidate.setName(candidateReq.getName());
        candidate.setCanNumber(generateRunningNumber());
        candidate.setBirthDate(candidateReq.getBirthDate());
        candidate.setHashtag(candidateReq.getHashtags().toString());
        candidate.setCurrentSalary(candidateReq.getCurrentSalary());
        candidate.setExpectedSalary(candidateReq.getExpectedSalary());
        candidate.setUser(user);
        candidate = candidateRepository.save(candidate);

        candidateAddressService.save(candidate, candidateReq.getAddress());
        candidateContactService.saveMobilePhone(candidate, candidateReq.getMobilePhone());
        candidateContactService.saveEmail(candidate, candidateReq.getEmail());
        candidateSocialMediaService.saveLinkedIn(candidate, candidateReq.getLinkedinLink());
        candidateSkillService.save(candidate, candidateReq.getSkill());
        candidateCertificationService.save(candidate, candidateReq.getCertification());
        candidateAchievementService.save(candidate, candidateReq.getAchievement());
        candidatePublicationService.save(candidate, candidateReq.getPublication());
        candidateLanguageService.save(candidate, candidateReq.getLanguage());
        candidateEducationService.save(candidate, candidateReq.getEducation());
        candidateExperienceService.save(candidate, candidateReq.getExperience());
    }

    public void modifyCandidate(Long id, CandidateReq candidateReq){
        Candidate candidate = candidateRepository.findById(id).get();
        User user = userRepository.findById(candidateReq.getUserId()).get();
        candidate.setName(candidateReq.getName());
        candidate.setBirthDate(candidateReq.getBirthDate());
        candidate.setHashtag(candidateReq.getHashtags().toString());
        candidate.setCurrentSalary(candidateReq.getCurrentSalary());
        candidate.setExpectedSalary(candidateReq.getExpectedSalary());
        candidate.setUser(user);
        candidate = candidateRepository.save(candidate);

        candidateAddressService.modifyAddress(candidate, candidateReq.getAddress());
        candidateContactService.modifyMobilePhone(candidate, candidateReq.getMobilePhone());
        candidateContactService.modifyEmail(candidate, candidateReq.getEmail());
        candidateSocialMediaService.modifyLinkedin(candidate, candidateReq.getLinkedinLink());
        candidateSkillService.modify(candidate, candidateReq.getSkill());
        candidateCertificationService.modify(candidate, candidateReq.getCertification());
        candidateAchievementService.modify(candidate, candidateReq.getAchievement());
        candidatePublicationService.modify(candidate, candidateReq.getPublication());
        candidateLanguageService.modify(candidate, candidateReq.getLanguage());
        candidateEducationService.modify(candidate, candidateReq.getEducation());
        candidateExperienceService.modify(candidate, candidateReq.getExperience());
    }

    public void modifyShortlist(CandidateShortlistReq candidateShortlistReq){
        Candidate candidate = candidateRepository.findById(candidateShortlistReq.getId()).get();

        if(candidateShortlistReq.getDirect() == false){
            CandidateAddress candidateAddress = candidateAddressRepository.findByCandidate(candidate).orElse(null);

            if(candidateAddress == null){
                candidateAddress = new CandidateAddress();
                candidateAddress.setCandidate(candidate);
            }

            if(candidateShortlistReq.getAddress() != null){
                candidateAddress.setAddress(candidateShortlistReq.getAddress());
                candidateAddressRepository.save(candidateAddress);
            }

            candidate.setCurrentSalary(candidateShortlistReq.getCurrentSalary());
            candidate.setExpectedSalary(candidateShortlistReq.getExpectedSalary());
            candidate.setBirthDate(candidateShortlistReq.getBirthDate());

            Interview interview = new Interview();
            interview.setCandidate(candidate);
            interview.setStartTime(candidateShortlistReq.getStartTime());
            interview.setEndTime(LocalDateTime.now());
            interview.setNotes(candidateShortlistReq.getNotes());
            interview.setFlagShortlist(candidateShortlistReq.getShortlist());
            interviewRepository.save(interview);
        }
        candidate.setFlagShortlist(candidateShortlistReq.getShortlist());
        candidateRepository.save(candidate);
    }

    public Page<CandidateResp> getCandidate(String keywords, Pageable pageable){
        return candidateRepository.getCandidate(keywords, pageable);
    }

    public List<Candidate> getCandidateList(String keywords){
        return candidateRepository.getCandidateList(keywords);
    }

    public CandidateReq getCandidateDetail(Long id, String username){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);

        CandidateReq candidateReq = new CandidateReq();
        Candidate candidate = candidateRepository.findById(id).get();
        candidateReq.setId(candidate.getId());
        candidateReq.setName(candidate.getName());
        candidateReq.setBirthDate(candidate.getBirthDate());
        candidateReq.setBirthDateFormat(candidate.getBirthDate() == null ? null : candidate.getBirthDate().format(formatter));
        candidateReq.setCanNumber(candidate.getCanNumber());

        if(candidate.getUser().getUsername().equals(username)){
            candidateReq.setCurrentSalary(candidate.getCurrentSalary());
            candidateReq.setExpectedSalary(candidate.getExpectedSalary());
        }
        candidateReq.setUserId(candidate.getUser().getId());
        candidateReq.setUserName(candidate.getUser().getUsername() + " - " + candidate.getUser().getName());

        String cleaned = candidate.getHashtag().replaceAll("[\\[\\]]", "");
        List<String> hashtags = Arrays.stream(cleaned.split(","))
                .map(String::trim)
                .filter(hashtag -> !hashtag.isEmpty())
                .collect(Collectors.toList());
        candidateReq.setHashtags(hashtags);

        candidateAddressService.setAddress(candidate, candidateReq);
        candidateContactService.setMobilePhone(candidate, candidateReq);
        candidateContactService.setEmail(candidate, candidateReq);
        candidateSocialMediaService.setLinkedin(candidate, candidateReq);
        candidateSkillService.setSkill(candidate, candidateReq);
        candidateCertificationService.setCertification(candidate, candidateReq);
        candidateAchievementService.setAchievement(candidate, candidateReq);
        candidatePublicationService.setPublication(candidate, candidateReq);
        candidateLanguageService.setLanguage(candidate, candidateReq);
        candidateEducationService.setEducation(candidate, candidateReq);
        candidateExperienceService.setExperience(candidate, candidateReq);

        return candidateReq;
    }

    public CandidateReq getCandidateDuplicate(String email, String phoneNumber){
        Long candidateId = candidateRepository.getCandidateByEmailOrPhoneNumber(email, phoneNumber);
        return candidateId == null ? null : getCandidateDetail(candidateId, null);
    }

    public void deleteCandidate(Long id){
        Candidate candidate = candidateRepository.findById(id).get();
        if(!employeeRequestCandidateRepository.findByCandidate(candidate).isEmpty()){
            throw new ConflictException("Candidate cannot be deleted due to become ERF Candidate already");
        }

        candidateAddressService.deleteAddress(candidate);
        candidateContactService.deleteContact(candidate);
        candidateSocialMediaService.deleteSocialMedia(candidate);
        candidateSkillService.delete(candidate);
        candidateCertificationService.delete(candidate);
        candidateAchievementService.delete(candidate);
        candidatePublicationService.delete(candidate);
        candidateLanguageService.delete(candidate);
        candidateEducationService.delete(candidate);
        candidateExperienceService.delete(candidate);
        candidateRepository.deleteById(id);
    }
}
