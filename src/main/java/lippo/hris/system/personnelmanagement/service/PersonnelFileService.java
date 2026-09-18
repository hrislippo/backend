package lippo.hris.system.personnelmanagement.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lippo.hris.system.feign.ProIntClient;
import lippo.hris.system.personnelmanagement.response.PersonnelFileResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonnelFileService {

    @Autowired
    ProIntClient proIntClient;

    @Autowired
    ObjectMapper objectMapper;

    public List<PersonnelFileResp> getPersonnelFileList(String empNIK){
        Object result = proIntClient.getEmployeeFile(empNIK).getData();
        List<PersonnelFileResp> fileList = objectMapper.convertValue(result, new TypeReference<>(){});

        for(PersonnelFileResp file : fileList){
            if(file.getFilePath().contains(".pdf")){
                file.setContentType("application/pdf");
            } else if(file.getFilePath().contains(".jpg")){
                file.setContentType("image/jpeg");
            }
        }

        return fileList;
    }

}
