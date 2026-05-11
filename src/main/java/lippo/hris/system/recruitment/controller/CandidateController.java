package lippo.hris.system.recruitment.controller;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.request.UserActiveRequest;
import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.emailengine.service.IcsService;
import lippo.hris.system.recruitment.request.CandidateReq;
import lippo.hris.system.recruitment.request.CandidateShortlistReq;
import lippo.hris.system.recruitment.validation.CandidateValidation;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.recruitment.service.CandidateService;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/recruitment")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @Autowired
    CandidateValidation candidateValidation;

    @PostMapping("/candidate")
    public ApiResponse addCandidate(@RequestBody CandidateReq candidateReq){
        candidateValidation.addCandidateRequired(candidateReq);
        return ApiResponse.ok(candidateService.saveCandidate(candidateReq), "Candidate Saved");
    }

    @PostMapping("/candidate-document")
    public ApiResponse addCandidateDocument(@RequestParam(value = "id") Long id,
                                            @RequestParam(value = "file") MultipartFile file) {
        candidateService.saveCandidateDocument(id, file);
        return ApiResponse.ok(null, "Save Candidate Document Successfully");
    }

    @PutMapping("/candidate")
    public ApiResponse modifyCandidate(@RequestParam(value = "id") Long id,
                                       @RequestBody CandidateReq candidateReq,
                                       Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        candidateValidation.modifyValidatePic(id, customUserDetails.getUsername());
        candidateValidation.addCandidateRequired(candidateReq);
        candidateService.modifyCandidate(id, candidateReq);
        return ApiResponse.ok(null, "Candidate Modified");
    }

    @PutMapping("/candidate-shortlist")
    public ApiResponse modifyShortlist(@RequestBody CandidateShortlistReq candidateShortlistReq) {
        candidateService.modifyShortlist(candidateShortlistReq);
        return ApiResponse.ok(null, "Shortlist Modified");
    }

    @GetMapping("/candidate")
    public ApiResponse getCandidate(@RequestParam(value = "keywords", required = false) String keywords,
                                    Pageable pageable) {
        return ApiResponse.ok(candidateService.getCandidate(keywords, pageable), "Get candidate successfully");
    }

    @GetMapping("/candidate-list")
    public ApiResponse getCandidateList(@RequestParam(value = "keywords", required = false) String keywords){
        return ApiResponse.ok(candidateService.getCandidateList(keywords), "Get candidate list successfully");
    }

    @GetMapping("/candidate-detail")
    public ApiResponse getCandidateDetail(@RequestParam(value = "id") Long id, Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(candidateService.getCandidateDetail(id, customUserDetails.getUsername()), "Get candidate detail successfully");
    }

    @GetMapping("/candidate-duplicate")
    public ApiResponse getCandidateDuplicate(@RequestParam(value = "email", required = false) String email,
                                      @RequestParam(value = "phoneNumber", required = false) String phoneNumber){
        return ApiResponse.ok(candidateService.getCandidateDuplicate(email, phoneNumber), "Get candidate successfully");
    }

    @DeleteMapping("/candidate")
    public ApiResponse deleteCandidate(@RequestParam(value = "id") Long id){
        candidateService.deleteCandidate(id);
        return ApiResponse.ok(null, "Delete candidate successfully");
    }
}
