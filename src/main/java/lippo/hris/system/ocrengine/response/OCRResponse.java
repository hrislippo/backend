package lippo.hris.system.ocrengine.response;

import lombok.Data;

import java.util.List;

@Data
public class OCRResponse {
    private List<OCRItem> results;
}
