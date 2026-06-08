package lippo.hris.system.emailengine.service;

import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.request.EmailTemplateReq;
import lippo.hris.system.emailengine.response.EmailTemplateResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmailTemplateService {

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    public void addEmailTemplate(EmailTemplateReq emailTemplateReq) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setCode(emailTemplateReq.getCode());
        emailTemplate.setName(emailTemplateReq.getName());
        emailTemplate.setSubject(emailTemplateReq.getSubject());
        emailTemplate.setContentHtml(emailTemplateReq.getContentHtml());
        emailTemplateRepository.save(emailTemplate);
    }

    public void modifyEmailTemplate(Long id, EmailTemplateReq emailTemplateReq) {
        EmailTemplate emailTemplate = getEmailTemplate(id);
        emailTemplate.setCode(emailTemplateReq.getCode());
        emailTemplate.setName(emailTemplateReq.getName());
        emailTemplate.setSubject(emailTemplateReq.getSubject());
        emailTemplate.setContentHtml(emailTemplateReq.getContentHtml());
        emailTemplateRepository.save(emailTemplate);
    }

    public Page<EmailTemplateResp> getEmailTemplate(String code, String name, Pageable pageable) {
        return emailTemplateRepository.getEmailTemplate(code, name, pageable);
    }

    public List<EmailTemplateResp> getEmailTemplateList(){
        return emailTemplateRepository.findAllList();
    }

    public EmailTemplate getEmailTemplate(Long id) {
        return emailTemplateRepository.findById(id).get();
    }

    public void deleteEmailTemplate(Long id) {
        emailTemplateRepository.deleteById(id);
    }
}
