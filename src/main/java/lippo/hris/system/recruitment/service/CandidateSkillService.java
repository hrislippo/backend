package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateSkill;
import lippo.hris.system.recruitment.repository.CandidateSkillRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidateSkillService {

    @Autowired
    CandidateSkillRepository candidateSkillRepository;

    public void save(Candidate candidate, List<String> skills){
        for(String skill : skills){
            CandidateSkill candidateSkill = new CandidateSkill();
            candidateSkill.setSkill(skill);
            candidateSkill.setCandidate(candidate);
            candidateSkillRepository.saveAndFlush(candidateSkill);
        }
    }

    public void modify(Candidate candidate, List<String> skills){
        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCandidate(candidate);
        List<String> existingSkills = candidateSkills.stream().map(CandidateSkill::getSkill).toList();

        List<String> added = new ArrayList<>(skills);
        added.removeAll(existingSkills);
        List<String> removed = new ArrayList<>(existingSkills);
        removed.removeAll(skills);

        save(candidate, added);
        delete(candidate, removed);
    }

    public void setSkill(Candidate candidate, CandidateReq candidateReq){
        List<CandidateSkill> candidateSkills = candidateSkillRepository.findByCandidate(candidate);
        List<String> skills = new ArrayList<>();
        for(CandidateSkill candidateSkill : candidateSkills){
            skills.add(candidateSkill.getSkill());
        }
        candidateReq.setSkill(skills);
    }

    public void delete(Candidate candidate, List<String> skills){
        for(String skill : skills){
            CandidateSkill candidateSkill = candidateSkillRepository.findByCandidateAndSkill(candidate, skill).get();
            candidateSkillRepository.delete(candidateSkill);
        }
    }

    public void delete(Candidate candidate){
        candidateSkillRepository.deleteAllByCandidate(candidate);
    }
}
