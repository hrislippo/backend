package lippo.hris.system.recruitment.service;

import lippo.hris.system.ocrengine.response.Education;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateEducation;
import lippo.hris.system.recruitment.repository.CandidateEducationRepository;
import lippo.hris.system.recruitment.request.CandidateEducationReq;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateEducationService {

    @Autowired
    CandidateEducationRepository candidateEducationRepository;

    public void save(Candidate candidate, List<CandidateEducationReq> educations){
        for(CandidateEducationReq education : educations){
            CandidateEducation candidateEducation = new CandidateEducation();
            candidateEducation.setCandidate(candidate);
            candidateEducation.setInstitutionName(education.getInstitutionName());
            candidateEducation.setDegreeName(education.getDegreeName());
            candidateEducation.setStartMonth(education.getStartMonth());
            candidateEducation.setStartYear(education.getStartYear());
            candidateEducation.setEndMonth(education.getEndMonth());
            candidateEducation.setEndYear(education.getEndYear());
            candidateEducationRepository.save(candidateEducation);
        }
    }

    public void upload(Candidate candidate, List<Education> educations){
        for(Education education : educations){
            CandidateEducation candidateEducation = new CandidateEducation();
            candidateEducation.setCandidate(candidate);
            candidateEducation.setInstitutionName(education.getInstitutionName());
            candidateEducation.setDegreeName(education.getDegreeName());
            candidateEducation.setStartMonth(education.getStartMonth());
            candidateEducation.setStartYear(education.getStartYear());
            candidateEducation.setEndMonth(education.getEndMonth());
            candidateEducation.setEndYear(education.getEndYear());
            candidateEducationRepository.save(candidateEducation);
        }
    }

    public void modify(Candidate candidate, List<CandidateEducationReq> educations){
        List<CandidateEducation> candidateEducations = candidateEducationRepository.findByCandidate(candidate);
        for(CandidateEducationReq education : educations){
            CandidateEducation candidateEducation = new CandidateEducation();
            if(education.getId() != null){
                candidateEducation = candidateEducations.stream()
                        .filter(l -> l.getId().equals(education.getId())).findFirst().get();
                candidateEducations.removeIf(user -> user.getId().equals(education.getId()));
            } else{
                candidateEducation.setCandidate(candidate);
            }
            candidateEducation.setInstitutionName(education.getInstitutionName());
            candidateEducation.setDegreeName(education.getDegreeName());
            candidateEducation.setStartMonth(education.getStartMonth());
            candidateEducation.setStartYear(education.getStartYear());
            candidateEducation.setEndMonth(education.getEndMonth());
            candidateEducation.setEndYear(education.getEndYear());
            candidateEducationRepository.save(candidateEducation);
        }

        for(CandidateEducation education : candidateEducations){
            candidateEducationRepository.delete(education);
        }
    }

    public void setEducation(Candidate candidate, CandidateReq candidateReq){
        List<CandidateEducation> candidateEducations = candidateEducationRepository.findByCandidate(candidate);
        List<CandidateEducationReq> educations = new ArrayList<>();
        for(CandidateEducation candidateEducation : candidateEducations){
            CandidateEducationReq education = new CandidateEducationReq();
            education.setId(candidateEducation.getId());
            education.setInstitutionName(candidateEducation.getInstitutionName());
            education.setDegreeName(candidateEducation.getDegreeName());
            education.setStartMonth(candidateEducation.getStartMonth());
            education.setStartYear(candidateEducation.getStartYear());
            education.setEndMonth(candidateEducation.getEndMonth());
            education.setEndYear(candidateEducation.getEndYear());
            educations.add(education);
        }
        candidateReq.setEducation(educations);
    }

    public void delete(Candidate candidate){
        candidateEducationRepository.deleteAllByCandidate(candidate);
    }
}
