package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.service.DashboardService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recruitment")
public class DashboardController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/dashboard")
    public ApiResponse getDashboard() {
        return ApiResponse.ok(dashboardService.getDashboard(), "Get recruitment dashboard successfully");
    }
}
