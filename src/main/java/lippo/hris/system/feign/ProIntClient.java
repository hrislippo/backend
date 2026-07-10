package lippo.hris.system.feign;

import lippo.hris.system.config.FeignConfig;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "proint-service", url = "${proint.service.url}",
        configuration = FeignConfig.class)
public interface ProIntClient {

    @PostMapping(value = "/api-proint/TMDPRights", consumes = MediaType.APPLICATION_JSON_VALUE)
    void addDayPayment(@RequestBody TMDPRightsReq tmDPRightsReq);
}
