package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.*;
import lippo.hris.system.recruitment.repository.CandidateSocialMediaMasterRepository;
import lippo.hris.system.recruitment.repository.CandidateSocialMediaRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CandidateSocialMediaService {

    @Autowired
    CandidateSocialMediaRepository candidateSocialMediaRepository;

    @Autowired
    CandidateSocialMediaMasterRepository candidateSocialMediaMasterRepository;

    public void saveLinkedIn(Candidate candidate, String linkedIn) {
        if(linkedIn == null || linkedIn.isEmpty()){
            return;
        }
        CandidateSocialMedia candidateSocialMedia = new CandidateSocialMedia();
        candidateSocialMedia.setCandidate(candidate);
        candidateSocialMedia.setType(candidateSocialMediaMasterRepository.findByType("LinkedIn").get());
        candidateSocialMedia.setLink(linkedIn);
        candidateSocialMediaRepository.save(candidateSocialMedia);
    }

    public void modifyLinkedin(Candidate candidate, String linkedinLink) {
        CandidateSocialMediaMaster candidateSocialMediaMaster = candidateSocialMediaMasterRepository.findByType("LinkedIn").get();
        CandidateSocialMedia candidateSocialMedia = new CandidateSocialMedia();
        Optional<CandidateSocialMedia> existingCandidateSocialMedia = candidateSocialMediaRepository.findByCandidateAndCandidateSocialMediaMaster
                (candidate.getId(), candidateSocialMediaMaster.getId());
        if(existingCandidateSocialMedia.isPresent()){
            candidateSocialMedia = existingCandidateSocialMedia.get();
        }else{
            candidateSocialMedia.setCandidate(candidate);
            candidateSocialMedia.setType(candidateSocialMediaMasterRepository.findByType("LinkedIn").get());
        }

        if(linkedinLink == null || linkedinLink.isEmpty()){
            return;
        }

        candidateSocialMedia.setLink(linkedinLink);
        candidateSocialMediaRepository.save(candidateSocialMedia);
    }

    public void setLinkedin(Candidate candidate, CandidateReq candidateReq) {
        CandidateSocialMediaMaster candidateSocialMediaMaster = candidateSocialMediaMasterRepository.findByType("LinkedIn").get();
        Optional<CandidateSocialMedia> candidateSocialMedia = candidateSocialMediaRepository.findByCandidateAndCandidateSocialMediaMaster
                (candidate.getId(), candidateSocialMediaMaster.getId());
        candidateSocialMedia.ifPresent(socialMedia -> candidateReq.setLinkedinLink(socialMedia.getLink()));
    }

    public void deleteSocialMedia(Candidate candidate){
        candidateSocialMediaRepository.deleteAllByCandidate(candidate);
    }
}
