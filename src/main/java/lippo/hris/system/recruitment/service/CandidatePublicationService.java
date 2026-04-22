package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAchievement;
import lippo.hris.system.recruitment.entity.CandidateCertification;
import lippo.hris.system.recruitment.entity.CandidatePublication;
import lippo.hris.system.recruitment.repository.CandidatePublicationRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidatePublicationService {

    @Autowired
    CandidatePublicationRepository candidatePublicationRepository;

    public void save(Candidate candidate, List<String> publications){
        for(String publication : publications){
            CandidatePublication candidatePublication = new CandidatePublication();
            candidatePublication.setCandidate(candidate);
            candidatePublication.setPublication(publication);
            candidatePublicationRepository.save(candidatePublication);
        }
    }

    public void modify(Candidate candidate, List<String> publications){
        List<CandidatePublication> candidatePublications = candidatePublicationRepository.findByCandidate(candidate);
        List<String> existingPublications = candidatePublications.stream().map(CandidatePublication::getPublication).toList();
        List<String> added = new ArrayList<>(publications);
        added.removeAll(existingPublications);
        List<String> removed = new ArrayList<>(existingPublications);
        removed.removeAll(publications);

        save(candidate, added);
        delete(candidate, removed);
    }

    public void setPublication(Candidate candidate, CandidateReq candidateReq){
        List<CandidatePublication> candidatePublications = candidatePublicationRepository.findByCandidate(candidate);
        List<String> publications = new ArrayList<>();
        for(CandidatePublication candidatePublication : candidatePublications){
            publications.add(candidatePublication.getPublication());
        }
        candidateReq.setPublication(publications);
    }

    public void delete(Candidate candidate, List<String> publications){
        for(String publication : publications){
            CandidatePublication candidatePublication = candidatePublicationRepository.findByCandidateAndPublication(candidate, publication).get();
            candidatePublicationRepository.delete(candidatePublication);
        }
    }

    public void delete(Candidate candidate){
        candidatePublicationRepository.deleteAllByCandidate(candidate);
    }
}
