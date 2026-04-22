package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateAddress;
import lippo.hris.system.recruitment.repository.CandidateAddressRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CandidateAddressService {

    @Autowired
    CandidateAddressRepository candidateAddressRepository;

    public void save(Candidate candidate, String address){
        if(address == null || address.isEmpty()){
            return;
        }
        CandidateAddress candidateAddress = new CandidateAddress();
        candidateAddress.setCandidate(candidate);
        candidateAddress.setAddress(address);
        candidateAddressRepository.save(candidateAddress);
    }

    public void modifyAddress(Candidate candidate, String address){
        CandidateAddress candidateAddress = new CandidateAddress();
        Optional<CandidateAddress> existingCandidateAddress = candidateAddressRepository.findByCandidate(candidate);
        if(existingCandidateAddress.isPresent()){
            candidateAddress = existingCandidateAddress.get();
        } else{
            candidateAddress.setCandidate(candidate);
        }

        if(address == null || address.isEmpty()){
            return;
        }

        candidateAddress.setAddress(address);
        candidateAddressRepository.save(candidateAddress);
    }

    public void setAddress(Candidate candidate, CandidateReq candidateReq){
        Optional<CandidateAddress> candidateAddress = candidateAddressRepository.findByCandidate(candidate);
        candidateAddress.ifPresent(address -> candidateReq.setAddress(address.getAddress()));
    }

    public void deleteAddress(Candidate candidate){
        candidateAddressRepository.deleteAllByCandidate(candidate);
    }
}
