package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CandidateValidation {

    @Autowired
    CandidateRepository candidateRepository;

    public void addCandidateRequired(CandidateReq candidateReq) {
        if(candidateReq.getName() == null || candidateReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }

        if(candidateReq.getMobilePhone() == null || candidateReq.getMobilePhone().trim().isEmpty()){
            throw new BadRequestException("Mobile Phone cannot be empty");
        }

        if(candidateReq.getEmail() == null || candidateReq.getEmail().trim().isEmpty()){
            throw new BadRequestException("Email cannot be empty");
        }

        if(candidateReq.getUserId() == null){
            throw new BadRequestException("PIC cannot be empty");
        }
    }

    public void modifyValidatePic(Long candidateId, String username){
        Candidate candidate = candidateRepository.findById(candidateId).get();
        if(!candidate.getUser().getUsername().equals(username)){
            throw new ConflictException("You are not allowed to modify this candidate");
        }
    }
}
