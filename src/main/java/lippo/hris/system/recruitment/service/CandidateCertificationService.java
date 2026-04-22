package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateCertification;
import lippo.hris.system.recruitment.entity.CandidateSkill;
import lippo.hris.system.recruitment.repository.CandidateCertificationRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateCertificationService {

    @Autowired
    CandidateCertificationRepository candidateCertificationRepository;

    public void save(Candidate candidate, List<String> certifications){
        for(String certification : certifications){
            CandidateCertification candidateCertification = new CandidateCertification();
            candidateCertification.setCandidate(candidate);
            candidateCertification.setCertification(certification);
            candidateCertificationRepository.save(candidateCertification);
        }
    }

    public void modify(Candidate candidate, List<String> certifications){
        List<CandidateCertification> candidateCertifications = candidateCertificationRepository.findByCandidate(candidate);
        List<String> existingCertifications = candidateCertifications.stream().map(CandidateCertification::getCertification).toList();

        List<String> added = new ArrayList<>(certifications);
        added.removeAll(existingCertifications);
        List<String> removed = new ArrayList<>(existingCertifications);
        removed.removeAll(certifications);

        save(candidate, added);
        delete(candidate, removed);
    }

    public void setCertification(Candidate candidate, CandidateReq candidateReq){
        List<CandidateCertification> candidateCertifications = candidateCertificationRepository.findByCandidate(candidate);
        List<String> certifications = new ArrayList<>();
        for(CandidateCertification candidateCertification : candidateCertifications){
            certifications.add(candidateCertification.getCertification());
        }
        candidateReq.setCertification(certifications);
    }

    public void delete(Candidate candidate, List<String> certifications){
        for(String certification : certifications){
            CandidateCertification candidateCertification = candidateCertificationRepository.findByCandidateAndCertification(candidate, certification).get();
            candidateCertificationRepository.delete(candidateCertification);
        }
    }

    public void delete(Candidate candidate){
        candidateCertificationRepository.deleteAllByCandidate(candidate);
    }
}
