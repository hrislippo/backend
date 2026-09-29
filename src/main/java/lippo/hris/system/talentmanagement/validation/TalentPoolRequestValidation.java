package lippo.hris.system.talentmanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.exception.ConflictException;
import lippo.hris.system.talentmanagement.repository.TalentPoolRequestRepository;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolRequestReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TalentPoolRequestValidation {

    @Autowired
    TalentPoolRequestRepository talentPoolRequestRepository;

    public void talentRequired(TalentPoolRequestReq talentPoolRequestReq){
        for(TalentPoolRequestDetailReq talentPoolRequestDetailReq : talentPoolRequestReq.getTalents()){
            if(talentPoolRequestDetailReq.getTalentId() == null){
                throw new BadRequestException("Talent cannot be empty");
            }
        }
    }

    public void talentDuplicate(TalentPoolRequestReq talentPoolRequestReq){
        boolean hasDuplicate = talentPoolRequestReq.getTalents().stream().map(TalentPoolRequestDetailReq::getTalentId).toList()
                .stream().distinct()
                .count() != talentPoolRequestReq.getTalents().stream().map(TalentPoolRequestDetailReq::getTalentId).toList().size();

        if(hasDuplicate){
            throw new BadRequestException("Talent cannot be duplicate");
        }
    }

    public void positionRequired(TalentPoolRequestReq talentPoolRequestReq){
        if(talentPoolRequestReq.getPositionCode() == null || talentPoolRequestReq.getPositionCode().isEmpty()){
            throw new BadRequestException("Position cannot be empty");
        }
    }

    public void positionExists(TalentPoolRequestReq talentPoolRequestReq){
        if(talentPoolRequestRepository.findByPositionCode(talentPoolRequestReq.getPositionCode()) != null){
            throw new ConflictException("Position Talent Pool already exists");
        }
    }
}
