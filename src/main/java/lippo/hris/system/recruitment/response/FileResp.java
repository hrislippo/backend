package lippo.hris.system.recruitment.response;

import lombok.Data;

@Data
public class FileResp {
    private String fileName;
    private String contentType;
    private byte[] data;
}
