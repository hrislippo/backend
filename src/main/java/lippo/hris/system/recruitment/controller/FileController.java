package lippo.hris.system.recruitment.controller;

import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import lippo.hris.system.recruitment.response.FileResp;
import lippo.hris.system.recruitment.service.FileService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruitment")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/file-activity")
    public ResponseEntity<byte[]> getFileActivity (@RequestParam("id") Long id) {
        FileResp fileResp = fileService.getFileActivity(id);
        if(fileResp.getData() == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileResp.getFileName())
                .contentType(MediaType.parseMediaType(fileResp.getContentType()))
                .body(fileResp.getData());
    }
}
