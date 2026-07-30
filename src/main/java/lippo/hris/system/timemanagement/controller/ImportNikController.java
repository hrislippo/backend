package lippo.hris.system.timemanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.service.ImportNikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/timemanagement")
public class ImportNikController {

    @Autowired
    ImportNikService importNikService;

    @PostMapping("/import-nik")
    public ApiResponse importNik(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.ok(importNikService.importNik(file), "NIK extracted from Excel");
    }

    @GetMapping("/download-import-nik")
    public ResponseEntity<?> downloadImportNik() throws IOException {
        return importNikService.downloadImportNik();
    }
}
