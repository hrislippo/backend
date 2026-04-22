package lippo.hris.system.ocrengine.util;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoordinateTextStripper extends PDFTextStripper {

    private final List<TextPosition> positions = new ArrayList<>();

    public CoordinateTextStripper() throws IOException {
        setSortByPosition(true);
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        positions.add(text);
    }

    public List<TextPosition> getPositions() {
        return positions;
    }
}
