package lippo.hris.system.emailengine.service;

import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.request.EmailProcessReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailProcessService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    EmailService emailService;

//    public void sendEmailTemplate(EmailProcessReq emailProcessReq) throws Exception {
//        EmailTemplate emailTemplate = emailTemplateRepository.findByName(emailProcessReq.getTemplateName());
//        emailService.sendEmail(emailProcessReq.getEmail(), emailTemplate.getSubject(), emailTemplate.getBody(), emailProcessReq.getParameter());
//    }
}
