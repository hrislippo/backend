package lippo.hris.system.ocrengine.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OcrResponse {
    private String name;
    private String address;
    private String mobilePhone;
    private String email;
    private String linkedInLink;
    private List<String> topSkills;
    private List<Language> languages;
    private List<String> certifications;
    private List<String> achievements;
    private List<String> publications;
    private List<Education> education;
    private List<Experience> experience;
}
