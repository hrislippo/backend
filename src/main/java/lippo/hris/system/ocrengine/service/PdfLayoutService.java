package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.util.CoordinateTextStripper;
import lippo.hris.system.ocrengine.util.SkillsExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PdfLayoutService {

    public List<List<TextPosition>> extract(MultipartFile file) throws Exception {
        List<List<TextPosition>> documentText = new ArrayList<>();
        PDDocument document = PDDocument.load(file.getInputStream());

        for(int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
            CoordinateTextStripper stripper = new CoordinateTextStripper();

            stripper.setStartPage(pageIndex + 1);
            stripper.setEndPage(pageIndex + 1);

            stripper.getText(document);

            List<TextPosition> pageText = stripper.getPositions();
            documentText.add(pageText);
        }

        document.close();
        return documentText;
    }
}
