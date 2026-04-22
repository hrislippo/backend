package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.ActivityEmailReq;
import lippo.hris.system.recruitment.request.ActivityInterviewReq;
import lippo.hris.system.recruitment.request.ActivityReq;
import lippo.hris.system.recruitment.service.RecruitmentActivityService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class RecruitmentActivityController {

    @Autowired
    private RecruitmentActivityService recruitmentActivityService;

    @PostMapping("/activity")
    public ApiResponse addActivity(@RequestBody ActivityReq activityReq) {
        recruitmentActivityService.addActivity(activityReq);
        return ApiResponse.ok(null, "Activity Saved");
    }

    @PutMapping("/activity")
    public ApiResponse modifyActivity(@RequestBody ActivityReq activityReq) {
        recruitmentActivityService.modifyActivity(activityReq);
        return ApiResponse.ok(null, "Activity Modified");
    }

    @PutMapping("/activity-interview")
    public ApiResponse modifyActivityInterview(@RequestBody ActivityInterviewReq activityInterviewReq) {
        recruitmentActivityService.modifyActivityInterview(activityInterviewReq);
        return ApiResponse.ok(null, "Activity Interview Modified");
    }

    @PutMapping("/activity-email")
    public ApiResponse modifyActivityEmail(@RequestBody ActivityEmailReq activityEmailReq) {
        recruitmentActivityService.modifyActivityEmail(activityEmailReq);
        return ApiResponse.ok(null, "Activity Email Modified");
    }

    @GetMapping("/group-activity")
    public ApiResponse getGroupActivity() {
        return ApiResponse.ok(recruitmentActivityService.getGroupActivity(), "Get Group Activities Successfully");
    }

    @GetMapping("/activity")
    public ApiResponse getActivity(@RequestParam(value = "group", required = false) String group,
                                   @RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "name", required = false) String name,
                                   Pageable pageable) {
        return ApiResponse.ok(recruitmentActivityService.getActivity(group, code, name, pageable), "Get Activities Successfully");
    }

    @GetMapping("/activity-list")
    public ApiResponse getActivityList() {
        return ApiResponse.ok(recruitmentActivityService.getActivityList(), "Get Activities List Successfully");
    }

    @GetMapping("/activity-detail")
    public ApiResponse getActivityDetail(@RequestParam(value = "id") Long id) {
        return ApiResponse.ok(recruitmentActivityService.getActivityDetail(id), "Get Activity Detail");
    }

    @DeleteMapping("/activity")
    public ApiResponse deleteActivity(@RequestParam("id") Long id) {
        recruitmentActivityService.deleteActivity(id);
        return ApiResponse.ok(null, "Activity Deleted");
    }
}
