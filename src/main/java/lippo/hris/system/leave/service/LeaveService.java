package lippo.hris.system.leave.service;

import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.leave.request.LeaveFeignReq;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LeaveService {

    @Autowired
    ProIntClient proIntClient;

    public void importLeave(MultipartFile file, String username) throws IOException {
        List<LeaveFeignReq> leaveFeignReqList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                LeaveFeignReq leaveFeignReq = new LeaveFeignReq();
                leaveFeignReq.setInitiatorNik(username);

                Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                for(int j = 0; j <= 2; j++){
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }

                    if(j == 0){
                        String nik = formatter.formatCellValue(cell).trim();
                        if (!nik.isBlank()) {
                            leaveFeignReq.setEmployeeNik(nik);
                        }
                    }

                    if(j == 1){
                        leaveFeignReq.setLeaveDate(getLocalDate(cell));
                    }

                    if(j == 2){
                        leaveFeignReq.setHalfLeave(getBoolean(cell, formatter));
                    }
                }
                leaveFeignReqList.add(leaveFeignReq);
            }
        }
        proIntClient.insertLeave(leaveFeignReqList);
    }

    public ResponseEntity<?> downloadImportLeave() throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/ImportLeaveTemplate.xlsx");

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=ImportLeaveTemplate.xlsx")
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    private LocalDate getLocalDate(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }

        return null;
    }

    public Boolean getBoolean(Cell cell, DataFormatter formatter) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        }

        String value = formatter.formatCellValue(cell).trim().toLowerCase();

        return switch (value) {
            case "true", "yes", "1" -> true;
            case "false", "no", "0" -> false;
            default -> null;
        };
    }
}
