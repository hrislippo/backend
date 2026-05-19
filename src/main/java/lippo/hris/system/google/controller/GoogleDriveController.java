package lippo.hris.system.google.controller;

import lippo.hris.system.google.service.GoogleDriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/googledrive")
public class GoogleDriveController {

    @Autowired
    GoogleDriveService googleDriveService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
        String url = googleDriveService.upload(file, null);
        return ResponseEntity.ok(url);
    }
}
