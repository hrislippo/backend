package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.HRBPReq;
import lippo.hris.system.recruitment.service.HRBPService;
import lippo.hris.system.recruitment.validation.HRBPValidation;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class HRBPController {

    @Autowired
    HRBPService hrbpService;

    @Autowired
    HRBPValidation hrbpValidation;

    @PostMapping("/hrbp")
    public ApiResponse addHRBP(@RequestBody HRBPReq hrbpReq) {
        hrbpValidation.addHRBPRequired(hrbpReq);
        hrbpService.addHRBP(hrbpReq);
        return ApiResponse.ok(null, "HRBP Saved");
    }

    @PutMapping("/hrbp")
    public ApiResponse modifyHRBP(@RequestBody HRBPReq hrbpReq) {
        hrbpValidation.addHRBPRequired(hrbpReq);
        hrbpService.modifyHRBP(hrbpReq);
        return ApiResponse.ok(null, "HRBP Modified");
    }

    @GetMapping("/hrbp")
    public ApiResponse getHRBP(@RequestParam(value = "BUName", required = false) String BUName,
                               @RequestParam(value = "code", required = false) String code,
                               @RequestParam(value = "name", required = false) String name,
                               Pageable pageable) {
        return ApiResponse.ok(hrbpService.getHRBP(BUName, code, name, pageable), "Get HRBP Data Successfully");
    }

    @GetMapping("/hrbp-list")
    public ApiResponse getHRBPList(@RequestParam(value = "BUId", required = false) Long BUId) {
        return ApiResponse.ok(hrbpService.getHRBPList(BUId), "Get HRBP List Successfully");
    }

    @GetMapping("/hrbp-detail")
    public ApiResponse getHRBPDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(hrbpService.getHRBPDetail(id), "Get HRBP Detail Data Successfully");
    }

    @DeleteMapping("/hrbp")
    public ApiResponse deleteHRBP(@RequestParam(value = "id") Long id) {
        hrbpService.deleteHRBP(id);
        return ApiResponse.ok(null, "HRBP Deleted");
    }
}
