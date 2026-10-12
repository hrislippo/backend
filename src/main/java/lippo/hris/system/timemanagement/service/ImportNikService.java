package lippo.hris.system.timemanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.authentication.service.TemplateAuthService;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.timemanagement.request.MOTMAtdTempMbrEmpReq;
import lippo.hris.system.timemanagement.response.EmployeeResp;
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

    @Autowired
    TemplateAuthService templateAuthService;

    @Autowired
    ObjectMapper objectMapper;

    public Object importNik(MultipartFile file, List<String> authorizations) throws IOException {
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
        return checkNik(employeeNiks, authorizations);
    }

    public Object checkNik(List<String> nikList, List<String> authorizations){
        List<String> nikAuthList = templateAuthService.getAuthEmployees(authorizations);
        Object employeeData = proIntClient.getEmployeeInfo(nikList).getData();
        List<EmployeeResp> employees = objectMapper.convertValue(employeeData, new TypeReference<>(){});

        for(EmployeeResp employee : employees){
            if(!nikAuthList.contains(employee.getEmpNik()) && !employee.getEmpName().contains("not found")){
                employee.setEmpName("Employee not authorized");
            }
        }
        return employees;
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
