package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.VenueReq;
import lippo.hris.system.recruitment.service.VenueService;
import lippo.hris.system.recruitment.validation.VenueValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class VenueController {

    @Autowired
    VenueService venueService;

    @Autowired
    VenueValidation venueValidation;

    @PostMapping("/venue")
    public ApiResponse addVenue(@RequestBody VenueReq venueReq) {
        venueValidation.addVenueRequired(venueReq);
        venueService.addVenue(venueReq);
        return ApiResponse.ok(null, "Venue Saved");
    }

    @PutMapping("/venue")
    public ApiResponse modifyVenue(@RequestBody VenueReq venueReq) {
        venueValidation.addVenueRequired(venueReq);
        venueService.modifyVenue(venueReq);
        return ApiResponse.ok(null, "Venue Modified");
    }

    @GetMapping("/venue")
    public ApiResponse getVenue(@RequestParam(value = "code", required = false) String code,
                                        @RequestParam(value = "name", required = false) String name,
                                        Pageable pageable) {
        return ApiResponse.ok(venueService.getVenue(code, name, pageable), "Get Venue Successfully");
    }

    @GetMapping("/venue-list")
    public ApiResponse getVenueList() {
        return ApiResponse.ok(venueService.getVenue(), "Get Venue List Successfully");
    }

    @GetMapping("/venue-detail")
    public ApiResponse getVenueDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(venueService.getVenue(id), "Get Venue Successfully");
    }

    @DeleteMapping("/venue")
    public ApiResponse deleteVenue(@RequestParam Long id) {
        venueService.deleteVenue(id);
        return ApiResponse.ok(null, "Venue Deleted");
    }
}
