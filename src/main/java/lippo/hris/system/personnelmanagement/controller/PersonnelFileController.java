package lippo.hris.system.personnelmanagement.controller;

import lippo.hris.system.personnelmanagement.response.PersonnelFileResp;
import lippo.hris.system.personnelmanagement.service.PersonnelFileService;
import lippo.hris.system.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api/personnelmanagement")
public class PersonnelFileController {

    @Autowired
    PersonnelFileService personnelFileService;

    @GetMapping("/file")
    public ResponseEntity<StreamingResponseBody> getEmployeeFile(@RequestParam("empNIK") String empNIK) {

        StreamingResponseBody stream = outputStream -> {
            try (ZipOutputStream zipOut = new ZipOutputStream(outputStream)) {
                List<PersonnelFileResp> fileData = personnelFileService.getPersonnelFileList(empNIK);

                for (PersonnelFileResp file : fileData) {
                    ZipEntry entry = new ZipEntry(file.getFilePath());
                    zipOut.putNextEntry(entry);
                    zipOut.write(file.getEmployeeFile());
                    zipOut.closeEntry();
                }
            }
        };

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+empNIK+".zip\""
                )
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(stream);
    }
}
