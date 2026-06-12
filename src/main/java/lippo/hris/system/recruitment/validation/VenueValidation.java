package lippo.hris.system.recruitment.validation;

import lippo.hris.system.exception.BadRequestException;
import lippo.hris.system.recruitment.request.VenueReq;
import org.springframework.stereotype.Component;

@Component
public class VenueValidation {

    public void addVenueRequired(VenueReq venueReq) {
        if(venueReq.getCode() == null || venueReq.getCode().trim().isEmpty()){
            throw new BadRequestException("Code cannot be empty");
        }

        if(venueReq.getName() == null || venueReq.getName().trim().isEmpty()){
            throw new BadRequestException("Name cannot be empty");
        }
    }
}
