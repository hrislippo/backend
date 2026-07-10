package lippo.hris.system.timemanagement.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DayPaymentValidation {

    public void checkDayPaymentValue(TMDPRightsReq tmDPRightsReq) {
        if(tmDPRightsReq.getDpExpiredDate().isBefore(tmDPRightsReq.getDpDate())) {
            throw new BadRequestException("Expiry Date is before Day Payment Date");
        }
    }

    public void inputDayPaymentRequired(TMDPRightsReq tmDPRightsReq) {
        if(tmDPRightsReq.getNikEmp() == null || tmDPRightsReq.getNikEmp().trim().isEmpty()){
            throw new BadRequestException("Employee NIK cannot be empty");
        }

        if(tmDPRightsReq.getDpCount() == null ||tmDPRightsReq.getDpCount() < 0){
            throw new BadRequestException("DP Amount must be greater than 0");
        }

        if(tmDPRightsReq.getDpDate() == null){
            throw new BadRequestException("DP Date cannot be empty");
        }

        if(tmDPRightsReq.getDpExpiredDate() == null){
            throw new BadRequestException("DP Expiry Date cannot be empty");
        }

        if(tmDPRightsReq.getDescription() == null || tmDPRightsReq.getDescription().trim().isEmpty()){
            throw new BadRequestException("DP description cannot be empty");
        }
    }
}
