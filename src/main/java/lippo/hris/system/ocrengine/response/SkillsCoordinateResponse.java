package lippo.hris.system.ocrengine.response;

import lombok.Data;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.TextPosition;

import java.util.List;

@Data
public class SkillsCoordinateResponse {
    private Integer page;
    private List<TextPosition> positionList;
    private Float fontSize;
    private PDFont font;
}
