package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.emailengine.EmailType;
import lippo.hris.system.emailengine.entity.EmailTemplate;
import lippo.hris.system.emailengine.repository.EmailTemplateRepository;
import lippo.hris.system.emailengine.service.EmailService;
import lippo.hris.system.entity.SystemParameter;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestPIC;
import lippo.hris.system.recruitment.repository.EmployeeRequestPICRepository;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import lippo.hris.system.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EmployeeRequestPICService {

    @Autowired
    EmployeeRequestPICRepository employeeRequestPICRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    SystemParameterRepository systemParameterRepository;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    public void modifyEmployeeRequestPIC(EmployeeRequestReq employeeRequestReq, EmployeeRequestForm employeeRequestForm){
        List<EmployeeRequestPIC> employeeRequestPIC = employeeRequestPICRepository.findByEmployeeRequestForm(employeeRequestForm);
        List<User> savedEmployeeRequestPIC = employeeRequestPIC.stream().map(EmployeeRequestPIC::getUser).toList();
        List<Long> savedPIC = savedEmployeeRequestPIC.stream().map(User::getId).toList();

        List<Long> added = new ArrayList<>(employeeRequestReq.getPic());
        added.removeAll(savedPIC);
        List<Long> removed = new ArrayList<>(savedPIC);
        removed.removeAll(employeeRequestReq.getPic());

        save(employeeRequestForm, added);
        delete(employeeRequestForm, removed);
    }

    public void save(EmployeeRequestForm employeeRequestForm, List<Long> added){
        for(Long pic : added){
            User user = userRepository.findById(pic).get();
            EmployeeRequestPIC employeeRequestPIC = new EmployeeRequestPIC();
            employeeRequestPIC.setEmployeeRequestForm(employeeRequestForm);
            employeeRequestPIC.setUser(user);
            employeeRequestPICRepository.save(employeeRequestPIC);

            SystemParameter systemParameter = systemParameterRepository.findByKey("Recruitment PIC Notification");
            EmailTemplate emailTemplate = emailTemplateRepository.findByCode(systemParameter.getValue());

            List<String> emailTos = new ArrayList<>();
            emailTos.add(user.getEmail());
            Map<String, Object> param = new HashMap<>();
            param.put("Nama Recruiter", user.getName());
            param.put("Nama ERF", employeeRequestForm.getName());

            try{
                emailService.sendEmail(emailTos, new ArrayList<>(), new ArrayList<>(), emailTemplate.getSubject(), emailTemplate.getContentHtml(),
                        param, null, EmailType.HRIS);
            }catch(Exception e){
            }
        }
    }

    public void delete(EmployeeRequestForm employeeRequestForm, List<Long> deleted){
        for(Long pic : deleted){
            User user = userRepository.findById(pic).get();
            EmployeeRequestPIC employeeRequestPIC = employeeRequestPICRepository.findByEmployeeRequestFormAndUser(employeeRequestForm, user);
            employeeRequestPICRepository.delete(employeeRequestPIC);
        }
    }
}
