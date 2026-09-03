package lippo.hris.system.google.controller;

import lippo.hris.system.google.service.GoogleSheetsService;
import lippo.hris.system.recruitment.service.RecruitmentSchedulerService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/googlesheets")
public class GoogleSheetsController {

    @Autowired
    RecruitmentSchedulerService recruitmentSchedulerService;

    @GetMapping("/read")
    public ApiResponse readSheets() throws Exception {
        recruitmentSchedulerService.readSheets();
        return ApiResponse.ok(null, "Successfully read google sheets");
    }
}
