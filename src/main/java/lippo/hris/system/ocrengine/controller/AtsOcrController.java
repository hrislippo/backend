package lippo.hris.system.ocrengine.controller;

import lippo.hris.system.ocrengine.service.AtsOcrService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class AtsOcrController {

    @Autowired
    AtsOcrService atsOcrService;

    @PostMapping("/ats-cv")
    public ApiResponse atsOcr(@RequestParam("file") MultipartFile file) throws Exception {
        return ApiResponse.ok(atsOcrService.readText(file), "Text extracted from ATS CV");
    }
}
