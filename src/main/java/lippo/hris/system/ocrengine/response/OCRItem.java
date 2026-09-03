package lippo.hris.system.ocrengine.response;

import lombok.Data;

import java.util.List;

@Data
public class OCRItem {

    private String text;
    private double confidence;
    private List<Integer> box;
}
