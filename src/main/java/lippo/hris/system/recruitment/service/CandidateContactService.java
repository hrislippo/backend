package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAddress;
import lippo.hris.system.recruitment.entity.CandidateContact;
import lippo.hris.system.recruitment.entity.CandidateContactMaster;
import lippo.hris.system.recruitment.repository.CandidateContactMasterRepository;
import lippo.hris.system.recruitment.repository.CandidateContactRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import lippo.hris.system.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CandidateContactService {

    @Autowired
    CandidateContactRepository candidateContactRepository;

    @Autowired
    CandidateContactMasterRepository candidateContactMasterRepository;

    public void saveMobilePhone(Candidate candidate, String mobilePhone) {
        if(mobilePhone == null || mobilePhone.isEmpty()){
            return;
        }
        CandidateContact candidateContact = new CandidateContact();
        candidateContact.setCandidate(candidate);
        candidateContact.setType(candidateContactMasterRepository.findByType("Mobile Phone").get());
        candidateContact.setContact(StringUtil.formatPhoneNumber(mobilePhone));
        candidateContactRepository.save(candidateContact);
    }

    public void modifyMobilePhone(Candidate candidate, String mobilePhone) {
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Mobile Phone").get();
        CandidateContact candidateContact = new CandidateContact();
        Optional<CandidateContact> existingCandidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        if(existingCandidateContact.isPresent()){
            candidateContact = existingCandidateContact.get();
        }else{
            candidateContact.setCandidate(candidate);
            candidateContact.setType(candidateContactMasterRepository.findByType("Mobile Phone").get());
        }

        if(mobilePhone == null || mobilePhone.isEmpty()){
            return;
        }
        candidateContact.setContact(StringUtil.formatPhoneNumber(mobilePhone));
        candidateContactRepository.save(candidateContact);
    }

    public void saveEmail(Candidate candidate, String email) {
        if(email == null || email.isEmpty()){
            return;
        }
        CandidateContact candidateContact = new CandidateContact();
        candidateContact.setCandidate(candidate);
        candidateContact.setType(candidateContactMasterRepository.findByType("Email").get());
        candidateContact.setContact(email);
        candidateContactRepository.save(candidateContact);
    }

    public void modifyEmail(Candidate candidate, String email) {
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Email").get();
        CandidateContact candidateContact = new CandidateContact();
        Optional<CandidateContact> existingCandidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        if(existingCandidateContact.isPresent()){
            candidateContact = existingCandidateContact.get();
        }else{
            candidateContact.setCandidate(candidate);
            candidateContact.setType(candidateContactMasterRepository.findByType("Email").get());
        }

        if(email == null || email.isEmpty()){
            return;
        }
        candidateContact.setContact(email);
        candidateContactRepository.save(candidateContact);
    }

    public void setMobilePhone(Candidate candidate, CandidateReq candidateReq){
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Mobile Phone").get();
        Optional<CandidateContact> candidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        candidateContact.ifPresent(contact -> candidateReq.setMobilePhone(contact.getContact()));
    }

    public void setEmail(Candidate candidate, CandidateReq candidateReq){
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Email").get();
        Optional<CandidateContact> candidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        candidateContact.ifPresent(contact -> candidateReq.setEmail(contact.getContact()));
    }

    public String getMobilePhone(Candidate candidate){
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Mobile Phone").get();
        Optional<CandidateContact> candidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        return candidateContact.map(CandidateContact::getContact).orElse(null);
    }

    public String getEmail(Candidate candidate){
        CandidateContactMaster candidateContactMaster = candidateContactMasterRepository.findByType("Email").get();
        Optional<CandidateContact> candidateContact = candidateContactRepository.findByCandidateAndCandidateContactMaster(candidate.getId(), candidateContactMaster.getId());
        return candidateContact.map(CandidateContact::getContact).orElse(null);
    }

    public void deleteContact(Candidate candidate){
        candidateContactRepository.deleteAllByCandidate(candidate);
    }
}
