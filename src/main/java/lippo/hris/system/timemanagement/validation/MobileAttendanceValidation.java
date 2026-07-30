package lippo.hris.system.timemanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrReq;
import org.springframework.stereotype.Component;

@Component
public class MobileAttendanceValidation {

    public void checkMobileAttendanceValue(MOTMAtdTempMbrReq motmAtdTempMbrReq) {
        if(motmAtdTempMbrReq.getStartDate() != null && motmAtdTempMbrReq.getEndDate() != null &&
                motmAtdTempMbrReq.getEndDate().isBefore(motmAtdTempMbrReq.getStartDate())) {
            throw new BadRequestException("End Date is before Start Date");
        }

        if(motmAtdTempMbrReq.getStartDate() != null && motmAtdTempMbrReq.getEndDate() == null) {
            throw new BadRequestException("End Date is required after Start Date");
        }

        if(motmAtdTempMbrReq.getEndDate() != null && motmAtdTempMbrReq.getStartDate() == null) {
            throw new BadRequestException("Start Date is required after End Date");
        }
    }

    public void inputMobileAttendanceRequired(MOTMAtdTempMbrReq motmAtdTempMbrReq) {
        if(motmAtdTempMbrReq.getNikList() == null || motmAtdTempMbrReq.getNikList().size() <= 0){
            throw new BadRequestException("Employee NIK List cannot be empty");
        }

        if(motmAtdTempMbrReq.getTempCode() == null || motmAtdTempMbrReq.getTempCode().trim().isEmpty()){
            throw new BadRequestException("Template Code cannot be empty");
        }

        if(motmAtdTempMbrReq.getDescription() == null || motmAtdTempMbrReq.getDescription().trim().isEmpty()){
            throw new BadRequestException("Template Description cannot be empty");
        }
    }
}
