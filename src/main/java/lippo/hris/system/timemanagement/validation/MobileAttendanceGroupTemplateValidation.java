package lippo.hris.system.timemanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.timemanagement.repository.MobileAttendanceGroupTemplateRepository;
import lippo.hris.system.timemanagement.request.MobileAttendanceGroupTemplateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MobileAttendanceGroupTemplateValidation {

    @Autowired
    MobileAttendanceGroupTemplateRepository mobileAttendanceGroupTemplateRepository;

    public void codeNameExist(MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq){
        if(mobileAttendanceGroupTemplateReq.getCode() == null || mobileAttendanceGroupTemplateReq.getCode().isEmpty()){
            throw new BadRequestException("Code is required");
        }
        if(mobileAttendanceGroupTemplateReq.getName() == null || mobileAttendanceGroupTemplateReq.getName().isEmpty()){
            throw new BadRequestException("Name is required");
        }
        if(mobileAttendanceGroupTemplateRepository.findExistingCode(mobileAttendanceGroupTemplateReq.getCode(), mobileAttendanceGroupTemplateReq.getId()) != null){
            throw new BadRequestException("Code is already exist");
        }
        if(mobileAttendanceGroupTemplateRepository.findExistingName(mobileAttendanceGroupTemplateReq.getName(), mobileAttendanceGroupTemplateReq.getId()) != null){
            throw new BadRequestException("Name is already exist");
        }
    }

    public void hrbpRequired(MobileAttendanceGroupTemplateReq mobileAttendanceGroupTemplateReq){
        if(mobileAttendanceGroupTemplateReq.getHrbpId() == null){
            throw new BadRequestException("HRBP is required");
        }
    }
}
