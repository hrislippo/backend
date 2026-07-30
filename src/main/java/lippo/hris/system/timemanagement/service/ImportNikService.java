package lippo.hris.system.timemanagement.service;

import lippo.hris.system.feign.ProIntClient;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImportNikService {

    @Autowired
    ProIntClient proIntClient;

    public Object importNik(MultipartFile file) throws IOException {
        List<String> employeeNiks = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                Cell cell = row.getCell(0);

                if (cell == null) {
                    continue;
                }

                String nik = formatter.formatCellValue(cell).trim();

                if (!nik.isBlank()) {
                    employeeNiks.add(nik);
                }
            }
        }
        return proIntClient.getEmployeeInfo(employeeNiks).getData();
    }

    public ResponseEntity<?> downloadImportNik() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/ImportDayPaymentTemplate.xlsx");

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ImportDayPaymentTemplate.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
