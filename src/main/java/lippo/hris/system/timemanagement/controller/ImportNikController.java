package lippo.hris.system.timemanagement.controller;

import lippo.hris.system.response.ApiResponse;
import lippo.hris.system.timemanagement.service.ImportNikService;
import lippo.hris.system.timemanagement.validation.DayPaymentValidation;
import lippo.hris.system.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/timemanagement")
public class ImportNikController {

    @Autowired
    ImportNikService importNikService;

    @Autowired
    DayPaymentValidation dayPaymentValidation;

    @PostMapping("/import-nik")
    public ApiResponse importNik(@RequestParam("file") MultipartFile file,
                                 Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ApiResponse.ok(importNikService.importNik(file, userDetails.getAuthorizations()), "NIK extracted from Excel");
    }

    @GetMapping("/download-import-nik")
    public ResponseEntity<?> downloadImportNik() throws IOException {
        return importNikService.downloadImportNik();
    }

    @GetMapping("/check-nik")
    public ApiResponse checkNik(@RequestParam(value = "nikList", required = false) List<String> nikList,
                                Authentication authentication){
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        dayPaymentValidation.nikRequired(nikList);
        return ApiResponse.ok(importNikService.checkNik(nikList, userDetails.getAuthorizations()), "NIK checked successfully");
    }
}
