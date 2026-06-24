package lippo.hris.system.recruitment.service;

import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.entity.SystemParameter;
import lippo.hris.system.recruitment.repository.CandidateRepository;
import lippo.hris.system.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecruitmentSchedulerService {

    @Autowired
    EmailService emailService;

    @Autowired
    SystemParameterRepository systemParameterRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    CandidateRepository candidateRepository;

    public void sendAddCandidateReminder(){
        List<String> emailTo = new ArrayList<>();
        emailTo.add("rossiane.kurniawan@lippo.co.id");
        emailTo.add("anita.veronica@lippo.co.id");

        List<String> emailCc = new ArrayList<>();
        emailCc.add("albert@lippo.co.id");
        emailCc.add("himawan@lippo.co.id");

        SystemParameter systemParameter = systemParameterRepository.findByKey("Recruitment Add Candidate Template Email");
        EmailTemplate emailTemplate = emailTemplateRepository.findByCode(systemParameter.getValue());
        Integer candidateLastWeek = candidateRepository.getCandidateAddedLastWeek().size();
        Integer totalCandidate = candidateRepository.findAll().size();

        Map<String, Object> params = new HashMap<>();
        params.put("Jumlah Penambahan Mingguan", candidateLastWeek);
        params.put("Total Kandidat", totalCandidate);

        try {
            emailService.sendEmail(emailTo, emailCc, new ArrayList<>(), emailTemplate.getSubject(), emailTemplate.getContentHtml(),
                    params, null);
        }catch(Exception e){
        }
    }
}
