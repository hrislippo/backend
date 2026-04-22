package lippo.hris.system.ocrengine.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.ocrengine.service.LinkedinOcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/ocr")
public class LinkedinOcrController {

    @Autowired
    LinkedinOcrService linkedinOcrService;

    @PostMapping("/linkedin-cv")
    public ApiResponse linkedinOcr(@RequestParam("file") MultipartFile file) throws Exception {
        return ApiResponse.ok(linkedinOcrService.readText(file), "Text extracted from LinkedIn CV");
    }
}
