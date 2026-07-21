package lippo.hris.system.emailengine.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lippo.hris.system.emailengine.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailHRIS;

    @Value("${spring.mail.password}")
    private String passwordHRIS;

    @Value("${spring.mail.recruitment.username}")
    private String emailRecruitment;

    @Value("${spring.mail.recruitment.password}")
    private String passwordRecruitment;

    public JavaMailSender createMailSender(String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.office365.com");
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.starttls.required", "true");

        return mailSender;
    }

    public void sendEmail(List<String> to, List<String> cc, List<String> bcc, String subject, String body,
                          Map<String, Object> params, MultipartFile file, EmailType emailType) throws IOException, MessagingException {

        String email = emailHRIS;
        String password = passwordHRIS;

        if(emailType == EmailType.RECRUITMENT) {
            email = emailRecruitment;
            password = passwordRecruitment;
        }

        JavaMailSender sender = createMailSender(email, password);
        MimeMessage message = sender.createMimeMessage();

        // true = multipart (attachment enabled)
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(email);
        helper.setTo(to.toArray(String[]::new));
        helper.setCc(cc.toArray(String[]::new));
        helper.setBcc(bcc.toArray(String[]::new));
        helper.setSubject(render(subject, params));
        helper.setText(render(body, params), true);

        if(file != null){
            helper.addAttachment(file.getOriginalFilename(), new ByteArrayResource(file.getBytes()));
        }

        sender.send(message);
    }

    public void sendEmailIcs(String to, String subject, String body, String icsContent) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        // true = multipart (attachment enabled)
        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("hrislippo2025@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false);
        helper.addAttachment("invite.ics", new ByteArrayResource(icsContent.getBytes()),
                "text/calendar;method=REQUEST;charset=UTF-8");

        mailSender.send(message);
    }

    public String render(String template, Map<String, Object> params) {
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher = pattern.matcher(template);

        StringBuffer result = new StringBuffer();

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            Object value = params.getOrDefault(key, "");
            matcher.appendReplacement(result, value.toString());
        }

        matcher.appendTail(result);
        return result.toString()// 1. normalize all <p>
                .replaceAll("<p(\\s+[^>]*)?>", "<p style=\"margin:0;padding:0;\">")

                // 3. remove empty <br> paragraphs
                .replaceAll("<p style=\"margin:0;padding:0;\"><br\\s*/?></p>", "");
    }
}
