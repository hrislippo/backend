package lippo.hris.system.recruitment.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CandidateReq {
    private Long id;
    private Long userId;
    private String userName;
    private String name;
    private String canNumber;
    private String address;
    private LocalDate birthDate;
    private String birthDateFormat;
    private String mobilePhone;
    private String email;
    private String linkedinLink;
    private Long currentSalary;
    private Long expectedSalary;
    private List<String> hashtags;
    private List<String> skill;
    private List<String> certification;
    private List<String> achievement;
    private List<String> publication;
    private List<CandidateLanguageReq> language;
    private List<CandidateEducationReq> education;
    private List<CandidateExperienceReq> experience;
}
