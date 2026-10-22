package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.CandidatePoolReq;
import lippo.hris.system.recruitment.service.CandidatePoolService;
import lippo.hris.system.recruitment.validation.CandidatePoolValidation;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class CandidatePoolController {

    @Autowired
    CandidatePoolValidation candidatePoolValidation;

    @Autowired
    CandidatePoolService candidatePoolService;

    @PostMapping("/candidate-pool")
    public ApiResponse addCandidatePool(@RequestBody CandidatePoolReq candidatePoolReq) {
        candidatePoolValidation.addCandidatePoolRequired(candidatePoolReq);
        candidatePoolService.addCandidatePool(candidatePoolReq);
        return ApiResponse.ok(null, "Candidate Pool Saved");
    }

    @PutMapping("/candidate-pool")
    public ApiResponse modifyCandidatePool(@RequestBody CandidatePoolReq candidatePoolReq) {
        candidatePoolValidation.addCandidatePoolRequired(candidatePoolReq);
        candidatePoolService.modifyCandidatePool(candidatePoolReq);
        return ApiResponse.ok(null, "Candidate Pool Modified");
    }

    @GetMapping("/candidate-pool")
    public ApiResponse getCandidatePool(@RequestParam(value = "code", required = false) String code,
                                       @RequestParam(value = "name", required = false) String name,
                                       Pageable pageable) {
        return ApiResponse.ok(candidatePoolService.getCandidatePool(code, name, pageable), "Get Candidate Pool Successfully");
    }

    @GetMapping("/candidate-pool-detail")
    public ApiResponse getCandidatePoolDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(candidatePoolService.getCandidatePoolDetail(id), "Get Candidate Pool Detail Successfully");
    }

    @GetMapping("/candidate-pool-list")
    public ApiResponse getCandidatePoolList(){
        return ApiResponse.ok(candidatePoolService.getCandidatePoolList(), "Get Candidate Pool List Successfully");
    }

    @GetMapping("/candidate-pool-user")
    public ApiResponse getCandidatePoolUser(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(candidatePoolService.getCandidatePoolUser(customUserDetails.getUsername()), "Get Candidate Pool User Successfully");
    }
}
