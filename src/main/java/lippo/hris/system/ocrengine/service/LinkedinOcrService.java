package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.Education;
import lippo.hris.system.ocrengine.response.Experience;
import lippo.hris.system.ocrengine.response.OcrResponse;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class LinkedinOcrService {

    @Autowired
    PdfLayoutService pdfLayoutService;

    @Autowired
    LinkedinExtractService linkedinExtractService;

    public OcrResponse readText(MultipartFile file) throws Exception {
        OcrResponse ocrResponse = extractTextFromPdfLayout(file);
        return postProcess(ocrResponse);
    }

    public OcrResponse extractTextFromPdfLayout(MultipartFile file) throws Exception {
        List<List<TextPosition>> positionsList = pdfLayoutService.extract(file);
//        linkedinExtractService.rightTest(positionsList);

        OcrResponse ocrResponse = new OcrResponse();
        ocrResponse.setName(linkedinExtractService.getLinkedInName(positionsList));
        ocrResponse.setAddress(linkedinExtractService.getLinkedInAddress(positionsList));
        ocrResponse.setMobilePhone(linkedinExtractService.getLinkedInMobilePhone(positionsList));
        ocrResponse.setEmail(linkedinExtractService.getLinkedInEmail(positionsList));
        ocrResponse.setLinkedInLink(linkedinExtractService.getLinkedInLink(positionsList));
        ocrResponse.setTopSkills(linkedinExtractService.getLinkedInTopSkills(positionsList));
        ocrResponse.setLanguages(linkedinExtractService.getLinkedInLanguages(positionsList));
        ocrResponse.setCertifications(linkedinExtractService.getLinkedInCertifications(positionsList));
        ocrResponse.setAchievements(linkedinExtractService.getLinkedInAchievements(positionsList));
        ocrResponse.setPublications(linkedinExtractService.getLinkedInPublications(positionsList));
        ocrResponse.setExperience(linkedinExtractService.getLinkedInExperience(positionsList));
        ocrResponse.setEducation(linkedinExtractService.getLinkedInEducation(positionsList));
        return ocrResponse;
    }

    public OcrResponse postProcess(OcrResponse ocrResponse) {
        for(Education education : ocrResponse.getEducation()) {
            String durationEducation = education.getDuration().replace("(", "")
                    .replace(")", "");

            String durationStart = parseMonths(durationEducation.substring(0, Math.max(durationEducation.indexOf("-"), 0)).trim());
            String durationEnd = parseMonths(durationEducation.substring(Math.max(durationEducation.indexOf("-") + 1, 0)).trim());

            education.setStartYear(durationStart.contains("-") ? durationStart.substring(0, Math.max(durationStart.indexOf("-"), 0)).trim() : durationStart);
            education.setStartMonth(durationStart.contains("-") ? durationStart.substring(Math.max(durationStart.indexOf("-") + 1, 0)).trim() : null);
            education.setEndYear(durationEnd.contains("-") ? durationEnd.substring(0, Math.max(durationEnd.indexOf("-"), 0)).trim() : durationEnd);
            education.setEndMonth(durationEnd.contains("-") ? durationEnd.substring(Math.max(durationEnd.indexOf("-") + 1, 0)).trim() : null);
        }

        for(Experience experience : ocrResponse.getExperience()) {
            String durationExperience = experience.getDuration().substring(0, experience.getDuration().indexOf("(")).trim();

            if(!durationExperience.isEmpty()) {
                String durationStart = parseMonths(durationExperience.substring(0, durationExperience.indexOf("-")).trim());
                String durationEnd = parseMonths(durationExperience.substring(durationExperience.indexOf("-") + 1).trim());

                experience.setStartYear(durationStart.contains("-") ? durationStart.substring(0, durationStart.indexOf("-")).trim() : durationStart);
                experience.setStartMonth(durationStart.contains("-") ? durationStart.substring(durationStart.indexOf("-") + 1).trim() : null);
                experience.setEndYear(durationEnd.contains("-") ? durationEnd.substring(0, durationEnd.indexOf("-")).trim() : durationEnd);
                experience.setEndMonth(durationEnd.contains("-") ? durationEnd.substring(durationEnd.indexOf("-") + 1).trim() : null);
            }
        }
        return ocrResponse;
    }

    public String parseMonths(String duration){
        duration = duration.replace("\u00A0", "");
        if(duration.contains("January") || duration.contains("Januari")){
            duration = duration.replace("January", "")
                    .replace("Januari", "").trim() + "-01";
        }else if(duration.contains("February") || duration.contains("Februari")){
            duration = duration.replace("February", "")
                    .replace("Februari", "").trim() + "-02";
        }else if(duration.contains("March") || duration.contains("Maret")){
            duration = duration.replace("March", "")
                    .replace("Maret", "").trim() + "-03";
        }else if(duration.contains("April")){
            duration = duration.replace("April", "").trim() + "-04";
        }else if(duration.contains("May") || duration.contains("Mei")){
            duration = duration.replace("May", "")
                    .replace("Mei", "").trim() + "-05";
        }else if(duration.contains("June") || duration.contains("Juni")){
            duration = duration.replace("June", "")
                    .replace("Juni", "").trim() + "-06";
        }else if(duration.contains("July") || duration.contains("Juli")){
            duration = duration.replace("July", "")
                    .replace("Juli", "").trim() + "-07";
        }else if(duration.contains("August") || duration.contains("Agustus")){
            duration = duration.replace("August", "")
                    .replace("Agustus", "").trim() + "-08";
        }else if(duration.contains("September")){
            duration = duration.replace("September", "").trim() + "-09";
        }else if(duration.contains("October") || duration.contains("Oktober")){
            duration = duration.replace("October", "")
                    .replace("Oktober", "").trim() + "-10";
        }else if(duration.contains("November")){
            duration = duration.replace("November", "").trim() + "-11";
        }else if(duration.contains("December") || duration.contains("Desember")){
            duration = duration.replace("December", "")
                    .replace("Desember", "").trim() + "-12";
        }else if(duration.contains("Present")){
            duration = LocalDate.now().getYear()+"-"+String.format("%02d", LocalDate.now().getMonthValue());
        }
        return duration;
    }
}