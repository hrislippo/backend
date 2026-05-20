package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.OcrResponse;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class AtsOcrService {

    @Autowired
    PdfLayoutService pdfLayoutService;

    @Autowired
    AtsExtractService atsExtractService;

    public OcrResponse readText(MultipartFile file) throws Exception {
        OcrResponse ocrResponse = extractTextFromPdfLayout(file);
        return ocrResponse;
    }

    public OcrResponse extractTextFromPdfLayout(MultipartFile file) throws Exception {
        List<List<TextPosition>> positionsList = pdfLayoutService.extract(file);
//        atsExtractService.testFirstPage(positionsList);

        OcrResponse ocrResponse = new OcrResponse();
        ocrResponse.setName(atsExtractService.getATSName(positionsList));
        ocrResponse.setAddress(null);
        ocrResponse.setMobilePhone(postProcessMobilePhone(atsExtractService.getATSMobilePhone(positionsList)));
        ocrResponse.setEmail(postProcessEmail(atsExtractService.getATSEmail(positionsList)));
        ocrResponse.setLinkedInLink(null);
        ocrResponse.setTopSkills(new ArrayList<>());
        ocrResponse.setLanguages(new ArrayList<>());
        ocrResponse.setCertifications(new ArrayList<>());
        ocrResponse.setAchievements(new ArrayList<>());
        ocrResponse.setPublications(new ArrayList<>());
        ocrResponse.setExperience(new ArrayList<>());
        ocrResponse.setEducation(new ArrayList<>());
        return ocrResponse;
    }

    public String postProcessMobilePhone(String mobilePhone){
        if(mobilePhone == null || mobilePhone.isEmpty()){
            return null;
        }

        if(mobilePhone.startsWith("0")){
            mobilePhone = "+62"+mobilePhone.substring(1);
        }else if(!mobilePhone.startsWith("+")){
            mobilePhone = "+62"+mobilePhone;
        }
        return mobilePhone;
    }

    public String postProcessEmail(String email){
        if(email == null || email.isEmpty()){
            return null;
        }

        email = email.replaceFirst("^\\d+", "");
        return email;
    }
}
