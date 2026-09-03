package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.CVResponse;
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

    public CVResponse readText(MultipartFile file) throws Exception {
        CVResponse CVResponse = extractTextFromPdfLayout(file);
        return CVResponse;
    }

    public CVResponse extractTextFromPdfLayout(MultipartFile file) throws Exception {
        List<List<TextPosition>> positionsList = pdfLayoutService.extract(file);
//        atsExtractService.testFirstPage(positionsList);

        CVResponse CVResponse = new CVResponse();
        CVResponse.setName(atsExtractService.getATSName(positionsList));
        CVResponse.setAddress(null);
        CVResponse.setMobilePhone(postProcessMobilePhone(atsExtractService.getATSMobilePhone(positionsList)));
        CVResponse.setEmail(postProcessEmail(atsExtractService.getATSEmail(positionsList)));
        CVResponse.setLinkedInLink(null);
        CVResponse.setTopSkills(new ArrayList<>());
        CVResponse.setLanguages(new ArrayList<>());
        CVResponse.setCertifications(new ArrayList<>());
        CVResponse.setAchievements(new ArrayList<>());
        CVResponse.setPublications(new ArrayList<>());
        CVResponse.setExperience(new ArrayList<>());
        CVResponse.setEducation(new ArrayList<>());
        return CVResponse;
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
