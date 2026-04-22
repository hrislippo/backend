package lippo.hris.system.recruitment.service;

import lippo.hris.system.ocrengine.response.Experience;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateExperience;
import lippo.hris.system.recruitment.repository.CandidateExperienceRepository;
import lippo.hris.system.recruitment.request.CandidateExperienceReq;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateExperienceService {

    @Autowired
    CandidateExperienceRepository candidateExperienceRepository;

    public void save(Candidate candidate, List<CandidateExperienceReq> experiences){
        for(CandidateExperienceReq experience : experiences){
            CandidateExperience candidateExperience = new CandidateExperience();
            candidateExperience.setCandidate(candidate);
            candidateExperience.setCompanyName(experience.getCompanyName());
            candidateExperience.setPositionName(experience.getPositionName());
            candidateExperience.setLocationName(experience.getLocationName());
            candidateExperience.setDescription(experience.getDescription());
            candidateExperience.setStartMonth(experience.getStartMonth());
            candidateExperience.setStartYear(experience.getStartYear());
            candidateExperience.setEndMonth(experience.getEndMonth());
            candidateExperience.setEndYear(experience.getEndYear());
            candidateExperienceRepository.save(candidateExperience);
        }
    }

    public void upload(Candidate candidate, List<Experience> experiences){
        for(Experience experience : experiences){
            CandidateExperience candidateExperience = new CandidateExperience();
            candidateExperience.setCandidate(candidate);
            candidateExperience.setCompanyName(experience.getCompanyName());
            candidateExperience.setPositionName(experience.getPositionName());
            candidateExperience.setLocationName(experience.getLocationName());
            candidateExperience.setDescription(experience.getDescription());
            candidateExperience.setStartMonth(experience.getStartMonth());
            candidateExperience.setStartYear(experience.getStartYear());
            candidateExperience.setEndMonth(experience.getEndMonth());
            candidateExperience.setEndYear(experience.getEndYear());
            candidateExperienceRepository.save(candidateExperience);
        }
    }

    public void modify(Candidate candidate, List<CandidateExperienceReq> experiences){
        List<CandidateExperience> candidateExperiences = candidateExperienceRepository.findByCandidate(candidate);
        for(CandidateExperienceReq experience : experiences){
            CandidateExperience candidateExperience = new CandidateExperience();
            if(experience.getId() != null){
                candidateExperience = candidateExperiences.stream()
                        .filter(l -> l.getId().equals(experience.getId())).findFirst().get();
                candidateExperiences.removeIf(user -> user.getId().equals(experience.getId()));
            } else{
                candidateExperience.setCandidate(candidate);
            }
            candidateExperience.setCompanyName(experience.getCompanyName());
            candidateExperience.setPositionName(experience.getPositionName());
            candidateExperience.setLocationName(experience.getLocationName());
            candidateExperience.setDescription(experience.getDescription());
            candidateExperience.setStartYear(experience.getStartMonth());
            candidateExperience.setStartYear(experience.getStartYear());
            candidateExperience.setEndMonth(experience.getEndMonth());
            candidateExperience.setEndYear(experience.getEndYear());
            candidateExperienceRepository.save(candidateExperience);
        }

        for(CandidateExperience experience : candidateExperiences){
            candidateExperienceRepository.delete(experience);
        }
    }

    public void setExperience(Candidate candidate, CandidateReq candidateReq){
        List<CandidateExperience> candidateExperiences = candidateExperienceRepository.findByCandidate(candidate);
        List<CandidateExperienceReq> experiences = new ArrayList<>();
        for(CandidateExperience candidateExperience : candidateExperiences){
            CandidateExperienceReq experience = new CandidateExperienceReq();
            experience.setId(candidateExperience.getId());
            experience.setCompanyName(candidateExperience.getCompanyName());
            experience.setPositionName(candidateExperience.getPositionName());
            experience.setLocationName(candidateExperience.getLocationName());
            experience.setDescription(candidateExperience.getDescription());
            experience.setStartMonth(candidateExperience.getStartMonth());
            experience.setStartYear(candidateExperience.getStartYear());
            experience.setEndMonth(candidateExperience.getEndMonth());
            experience.setEndYear(candidateExperience.getEndYear());
            experiences.add(experience);
        }
        candidateReq.setExperience(experiences);
    }

    public void delete(Candidate candidate){
        candidateExperienceRepository.deleteAllByCandidate(candidate);
    }
}
