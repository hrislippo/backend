package lippo.hris.system.talentmanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.talentmanagement.entity.TalentPool;
import lippo.hris.system.talentmanagement.repository.TalentPoolRepository;
import lippo.hris.system.talentmanagement.request.TalentPoolDetailReq;
import lippo.hris.system.talentmanagement.request.TalentPoolEmployeeReq;
import lippo.hris.system.talentmanagement.request.TalentPoolReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TalentPoolValidation {

    @Autowired
    private TalentPoolRepository talentPoolRepository;

    public void nikRequired(TalentPoolReq talentPoolReq){
        if(talentPoolReq.getEmployeeNIK() == null || talentPoolReq.getEmployeeNIK().size() <= 0){
            throw new BadRequestException("NIK cannot be empty");
        }
    }

    public void nikRequired(TalentPoolDetailReq talentPoolDetailReq){
        for(TalentPoolEmployeeReq employee : talentPoolDetailReq.getEmployeeList()){
            if(employee.getEmployeeNIK() == null || employee.getEmployeeNIK().isEmpty()){
                throw new BadRequestException("NIK cannot be empty");
            }
        }
    }

    public void nikDuplicate(TalentPoolReq talentPoolReq){
        boolean hasDuplicate = talentPoolReq.getEmployeeNIK().stream()
                .distinct()
                .count() != talentPoolReq.getEmployeeNIK().size();

        if(hasDuplicate){
            throw new BadRequestException("NIK cannot be duplicate");
        }
    }

    public void nikDuplicate(TalentPoolDetailReq talentPoolDetailReq){
        boolean hasDuplicate = talentPoolDetailReq.getEmployeeList().stream().map(TalentPoolEmployeeReq::getEmployeeNIK).toList()
                .stream().distinct()
                .count() != talentPoolDetailReq.getEmployeeList().stream().map(TalentPoolEmployeeReq::getEmployeeNIK).toList().size();

        if(hasDuplicate){
            throw new BadRequestException("NIK cannot be duplicate");
        }
    }

    public void positionRequired(TalentPoolReq talentPoolReq){
        if(talentPoolReq.getPositionCode() == null || talentPoolReq.getPositionCode().isEmpty()){
            throw new BadRequestException("Position cannot be empty");
        }
    }

    public void positionExists(TalentPoolReq talentPoolReq){
        if(!talentPoolRepository.findByPositionCode(talentPoolReq.getPositionCode()).isEmpty()){
            throw new BadRequestException("Position Talent Pool already exists");
        }
    }
}
