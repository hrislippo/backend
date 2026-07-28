package lippo.hris.system.timemanagement.service;

import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.timemanagement.entity.DayPaymentRequest;
import lippo.hris.system.timemanagement.repository.DayPaymentRequestRepository;
import lippo.hris.system.timemanagement.request.TMDPRightsReq;
import lippo.hris.system.timemanagement.response.DayPaymentResp;
import lippo.hris.system.timemanagement.response.ImportNikResp;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class DayPaymentService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    DayPaymentRequestRepository dayPaymentRequestRepository;

    public void addDayPayment(TMDPRightsReq tmDPRightsReq, String username){
        tmDPRightsReq.setNikEmpCreate(username);
        proIntClient.addDayPayment(tmDPRightsReq);

        for(String nikEmp : tmDPRightsReq.getNikEmp()){
            DayPaymentRequest dayPaymentRequest = new DayPaymentRequest();
            dayPaymentRequest.setEmployee(nikEmp);
            dayPaymentRequest.setCount(tmDPRightsReq.getDpCount());
            dayPaymentRequest.setDate(tmDPRightsReq.getDpDate());
            dayPaymentRequest.setExpiredDate(tmDPRightsReq.getDpExpiredDate());
            dayPaymentRequest.setDescription(tmDPRightsReq.getDescription());
            dayPaymentRequestRepository.save(dayPaymentRequest);
        }
    }

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

    public Page<DayPaymentResp> getDayPayment(String empNIK, LocalDate startDate, LocalDate expiryDate, Pageable pageable){
        return dayPaymentRequestRepository.getDayPayment(empNIK, startDate, expiryDate, pageable);
    }

    public DayPaymentRequest getDayPaymentDetail(Long id){
        return dayPaymentRequestRepository.findById(id).get();
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
