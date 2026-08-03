package lippo.hris.system.recruitment.request;

import lombok.Data;

@Data
public class RCCanPhotoReq {

    private String canCode;
    private String createdBy;
    private byte[] photo;
}
