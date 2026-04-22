package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.recruitment.entity.EmployeeRequestForm;
import lippo.hris.system.recruitment.entity.EmployeeRequestPIC;
import lippo.hris.system.recruitment.repository.EmployeeRequestPICRepository;
import lippo.hris.system.recruitment.request.EmployeeRequestReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeRequestPICService {

    @Autowired
    EmployeeRequestPICRepository employeeRequestPICRepository;

    @Autowired
    UserRepository userRepository;

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
