package lippo.hris.system.recruitment.service;

import lippo.hris.system.emailengine.EmailType;
import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.entity.SystemParameter;
import lippo.hris.system.google.service.GoogleDriveService;
import lippo.hris.system.google.service.GoogleSheetsService;
import lippo.hris.system.ocrengine.response.KTPData;
import lippo.hris.system.ocrengine.service.KtpService;
import lippo.hris.system.ocrengine.service.OcrService;
import lippo.hris.system.recruitment.enumeration.MajorMDP;
import lippo.hris.system.recruitment.enumeration.UniversityMDP;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecruitmentSchedulerService {

    @Autowired
    EmailService emailService;

    @Autowired
    SystemParameterRepository systemParameterRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    GoogleSheetsService googleSheetsService;

    @Autowired
    GoogleDriveService googleDriveService;

    @Autowired
    KtpService ktpService;

    @Value("${google.sheet-id}")
    private String spreadsheetId;

    public void sendAddCandidateReminder(){
        List<String> emailTo = new ArrayList<>();
        emailTo.add("rossiane.kurniawan@lippo.co.id");
        emailTo.add("anita.veronica@lippo.co.id");

        List<String> emailCc = new ArrayList<>();
        emailCc.add("albert@lippo.co.id");
        emailCc.add("himawan@lippo.co.id");

        SystemParameter systemParameter = systemParameterRepository.findByKey("Recruitment Add Candidate Template Email");
        EmailTemplate emailTemplate = emailTemplateRepository.findByCode(systemParameter.getValue());
        Integer candidateLastWeek = candidateRepository.getCandidateAddedLastWeek().size();
        Integer totalCandidate = candidateRepository.findAll().size();

        Map<String, Object> params = new HashMap<>();
        params.put("Jumlah Penambahan Mingguan", candidateLastWeek);
        params.put("Total Kandidat", totalCandidate);

        try {
            emailService.sendEmail(emailTo, emailCc, new ArrayList<>(), emailTemplate.getSubject(), emailTemplate.getContentHtml(),
                    params, null, EmailType.HRIS);
        }catch(Exception e){
        }
    }

    public void readSheets() throws Exception {
        List<List<Object>> values = googleSheetsService.readSheets(spreadsheetId, "Form Responses 1");
        List<Object> headers = values.get(0);

        int ktpIndex = headers.indexOf("Upload your ID Card (KTP)");

        if (ktpIndex == -1) {
            throw new RuntimeException("KTP column not found");
        }

        SystemParameter systemParameter = systemParameterRepository.findByKey("OCR Key Google Sheets");
        int start = Integer.parseInt(systemParameter.getValue()) + 1;
        int end = values.size() - 1;
        for (int i = start; i <= end; i++) {
            List<Object> row = values.get(i);
            if (ktpIndex < row.size()) {
                Object ktpValue = row.get(ktpIndex);
                String result = checkEligibleOCR(row, headers);
                KTPData ktpData = new KTPData();

                if(result == null){
                    MultipartFile fileKtp = googleDriveService.downloadFile(ktpValue.toString());
                    ktpData = ktpService.processKTP(fileKtp, ktpValue.toString());
                }
                googleSheetsService.appendKTP(spreadsheetId, "KTP Responses 1", ktpData, result, i);
            }
            systemParameter.setValue(String.valueOf(i));
            systemParameterRepository.save(systemParameter);
        }
    }

    public String checkEligibleOCR(List<Object> values, List<Object> headers) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        int gpaIndex = headers.indexOf("GPA");
        int majorIndex = headers.indexOf("Major");
        int universityIndex = headers.indexOf("University");
        int dateBirthIndex = headers.indexOf("Date of Birth");

        Float gpa = Float.parseFloat(values.get(gpaIndex).toString());
        String major = values.get(majorIndex).toString();
        String university = values.get(universityIndex).toString();
        LocalDate dateBirth = LocalDate.parse(values.get(dateBirthIndex).toString(), formatter);

        if(Period.between(dateBirth, LocalDate.now()).getYears() > 27){
            return "REJECTED (AGE ABOVE 27)";
        }

        if(gpa <= 3 || gpa > 4){
            return "REJECTED (GPA BELOW 3)";
        }

        if(MajorMDP.find(major) == null){
            return "REJECTED (MAJOR UNMATCHED)";
        }

        if(UniversityMDP.find(university) == null){
            return "REJECTED (UNIVERSITY UNMATCHED)";
        }

        return null;
    }
}
