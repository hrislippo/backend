package lippo.hris.system.personnel.response;

import lombok.Data;

@Data
public class PersonnelFileResp {

    private byte[] employeeFile;
    private String filePath;
    private String contentType;
}
