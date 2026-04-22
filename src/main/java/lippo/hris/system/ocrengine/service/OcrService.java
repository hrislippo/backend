package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.OcrResponse;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OcrService {

    @Autowired
    PdfLayoutService pdfLayoutService;

    @Autowired
    LinkedinOcrService linkedinOcrService;

    @Autowired
    AtsOcrService atsOcrService;

    public OcrResponse readText(MultipartFile file) throws Exception {
        List<List<TextPosition>> positionsList = pdfLayoutService.extract(file);
        if(positionsList.getFirst().getFirst().getY() == 10.314026F){
            return linkedinOcrService.readText(file);
        } else{
            return atsOcrService.readText(file);
        }
    }
}
