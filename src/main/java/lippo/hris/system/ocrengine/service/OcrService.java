package lippo.hris.system.ocrengine.service;

import lippo.hris.system.ocrengine.response.CVResponse;
import lippo.hris.system.ocrengine.response.OCRResponse;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.TextPosition;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class OcrService {
    private final RestClient restClient;

    @Autowired
    PdfLayoutService pdfLayoutService;

    @Autowired
    LinkedinOcrService linkedinOcrService;

    @Autowired
    AtsOcrService atsOcrService;

    public OcrService() {
        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8030")
                .build();
    }

    public CVResponse readText(MultipartFile file) throws Exception {
        List<List<TextPosition>> positionsList = pdfLayoutService.extract(file);
        if(!positionsList.isEmpty() && !positionsList.getFirst().isEmpty() && positionsList.getFirst().getFirst().getY() == 10.314026F){
            return linkedinOcrService.readText(file);
        } else{
            return atsOcrService.readText(file);
        }
    }

    public ByteArrayResource preprocessingOCR(ByteArrayResource resource) throws Exception {
        byte[] bytes = resource.getByteArray();

        Tika tika = new Tika();
        String detectedType = tika.detect(bytes);

        if ("application/pdf".equals(detectedType)) {
            bytes = pdfToImage(bytes);
            detectedType = "image/jpeg";
        }

        if ("image/heic".equalsIgnoreCase(detectedType)
                || "image/heif".equalsIgnoreCase(detectedType)) {
            return new ByteArrayResource(bytes) {

                @Override
                public String getFilename() {
                    return "image.heic";
                }
            };
        }

        if (!detectedType.startsWith("image/")) {
            throw new IllegalArgumentException(
                    "Unsupported file type: " + detectedType
            );
        }

        String finalDetectedType = detectedType;
        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {

                if ("image/png".equals(finalDetectedType)) {
                    return "image.png";
                }

                return "image.jpg";
            }
        };
    }

    public OCRResponse performOCR(ByteArrayResource resource) throws IOException {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", resource);

        return restClient.post()
                .uri("/ocr")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(OCRResponse.class);
    }

    private byte[] pdfToImage(byte[] pdfBytes) throws IOException {

        try (PDDocument document = Loader.loadPDF(pdfBytes)) {
            PDFRenderer renderer = new PDFRenderer(document);
            BufferedImage image = renderer.renderImageWithDPI(0, 300);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", output);
            return output.toByteArray();
        }
    }

    public ByteArrayResource toByteArrayResource(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();

        return new ByteArrayResource(bytes) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };
    }
}
