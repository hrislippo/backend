package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.InterviewReq;
import lippo.hris.system.recruitment.service.InterviewService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class InterviewController {

    @Autowired
    InterviewService interviewService;

    @PostMapping("/interview")
    public ApiResponse addInterview(@RequestBody InterviewReq interviewReq){
        interviewService.saveInterview(interviewReq);
        return ApiResponse.ok(null, "Interview Saved");
    }

    @PutMapping("/interview")
    public ApiResponse modifyInterview(@RequestBody InterviewReq interviewReq){
        interviewService.modifyInterview(interviewReq);
        return ApiResponse.ok(null, "Interview Updated");
    }

    @GetMapping("/interview")
    public ApiResponse getInterview(Pageable pageable){
        return ApiResponse.ok(interviewService.getInterview(pageable), "Get Interview Successfully");
    }

    @GetMapping("/interview-action")
    public ApiResponse getInterviewAction(@RequestParam(value = "actionId") Long actionId){
        return ApiResponse.ok(interviewService.getInterviewByAction(actionId), "Get Interview Successfully");
    }

    @GetMapping("/interview-detail")
    public ApiResponse getInterviewDetail(@RequestParam(value = "id") Long id){
        return ApiResponse.ok(interviewService.getInterviewDetail(id), "Get Interview Successfully");
    }

    @DeleteMapping("/interview")
    public ApiResponse deleteInterview(@RequestParam(value = "id") Long id){
        interviewService.deleteInterview(id);
        return ApiResponse.ok(null, "Interview Cancelled");
    }
}
