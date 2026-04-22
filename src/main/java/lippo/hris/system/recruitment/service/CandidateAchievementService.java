package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAchievement;
import lippo.hris.system.recruitment.entity.CandidateCertification;
import lippo.hris.system.recruitment.repository.CandidateAchievementRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateAchievementService {

    @Autowired
    CandidateAchievementRepository candidateAchievementRepository;

    public void save(Candidate candidate, List<String> achievements){
        for(String achievement : achievements){
            CandidateAchievement candidateAchievement = new CandidateAchievement();
            candidateAchievement.setCandidate(candidate);
            candidateAchievement.setAchievement(achievement);
            candidateAchievement.setId(null);
            candidateAchievementRepository.save(candidateAchievement);
        }
    }

    public void modify(Candidate candidate, List<String> achievements){
        List<CandidateAchievement> candidateAchievements = candidateAchievementRepository.findByCandidate(candidate);
        List<String> existingAchievements = candidateAchievements.stream().map(CandidateAchievement::getAchievement).toList();

        List<String> added = new ArrayList<>(achievements);
        added.removeAll(existingAchievements);
        List<String> removed = new ArrayList<>(existingAchievements);
        removed.removeAll(achievements);

        save(candidate, added);
        delete(candidate, removed);
    }

    public void setAchievement(Candidate candidate, CandidateReq candidateReq){
        List<CandidateAchievement> candidateAchievements = candidateAchievementRepository.findByCandidate(candidate);
        List<String> achievements = new ArrayList<>();
        for(CandidateAchievement candidateAchievement : candidateAchievements){
            achievements.add(candidateAchievement.getAchievement());
        }
        candidateReq.setAchievement(achievements);
    }

    public void delete(Candidate candidate, List<String> achievements){
        for(String achievement : achievements){
            CandidateAchievement candidateAchievement = candidateAchievementRepository.findByCandidateAndAchievement(candidate, achievement).get();
            candidateAchievementRepository.delete(candidateAchievement);
        }
    }

    public void delete(Candidate candidate){
        candidateAchievementRepository.deleteAllByCandidate(candidate);
    }
}
