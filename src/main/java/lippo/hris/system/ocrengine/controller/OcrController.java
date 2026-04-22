package lippo.hris.system.ocrengine.controller;

import lippo.hris.system.ocrengine.service.LinkedinOcrService;
import lippo.hris.system.ocrengine.service.OcrService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/ocr")
public class OcrController {

    @Autowired
    OcrService ocrService;

    @PostMapping("/cv")
    public ApiResponse ocr(@RequestParam("file") MultipartFile file) throws Exception {
        return ApiResponse.ok(ocrService.readText(file), "Text extracted from CV");
    }
}
