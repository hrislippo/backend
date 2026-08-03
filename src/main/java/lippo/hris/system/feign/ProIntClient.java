package lippo.hris.system.feign;

import lippo.hris.system.config.FeignConfig;
import lippo.hris.system.recruitment.request.RCCanPhotoReq;
import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrReq;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "proint-service", url = "${proint.service.url}",
        configuration = FeignConfig.class)
public interface ProIntClient {

    @PostMapping(value = "/api-proint/TMDPRights", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addDayPayment(@RequestBody TMDPRightsReq tmDPRightsReq);

    @PostMapping(value = "/api-proint/MOTMAtdTempMbr", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addMobileAttendanceTemplateMember(@RequestBody MOTMAtdTempMbrReq motmAtdTempMbrReq);

    @PostMapping(value = "/api-proint/RCCanPhoto", consumes = MediaType.APPLICATION_JSON_VALUE)
    void insertCandidatePhoto(@RequestBody RCCanPhotoReq rcCanPhotoReq);

    @GetMapping(value = "/api-proint/PMEmployee")
    ApiResponse getEmployeeInfo(@RequestParam List<String> nikList);

    @GetMapping(value = "/api-proint/MOTMAtdTemplate")
    ApiResponse getMOTMAtdTemplate(@RequestParam String tempCode);

    @DeleteMapping(value = "/api-proint/MOTMAtdTempMbr", consumes = MediaType.APPLICATION_JSON_VALUE)
    void deleteMobileAttendanceTemplateMember(@RequestBody MOTMAtdTempMbrReq motmAtdTempMbrReq);

}
