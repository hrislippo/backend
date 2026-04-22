package lippo.hris.system.recruitment.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EmployeeRequestEmailReq {
    Long id;
    MultipartFile file;
}
