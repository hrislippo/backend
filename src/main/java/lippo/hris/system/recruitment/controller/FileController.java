package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.service.FileService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/file-activity")
    public ApiResponse getFileActivity (@RequestParam("id") Long id) {
        return ApiResponse.ok(fileService.getFileActivity(id), "Get URL Preview Successfully");
    }

    @GetMapping("/file-cv")
    public ApiResponse getFileCV (@RequestParam("id") Long id) {
        return ApiResponse.ok(fileService.getFileCV(id), "Get URL Preview Successfully");
    }

    @GetMapping("/download-file-cv")
    public ApiResponse downloadCV (@RequestParam("id") Long id) {
        return ApiResponse.ok(fileService.downloadCV(id), "Get URL Download Successfully");
    }
}
