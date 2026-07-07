package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.EmploymentType;
import lippo.hris.system.recruitment.entity.Venue;
import lippo.hris.system.recruitment.repository.EmploymentTypeRepository;
import lippo.hris.system.recruitment.request.EmploymentTypeReq;
import lippo.hris.system.recruitment.request.VenueReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmploymentTypeService {

    @Autowired
    EmploymentTypeRepository employmentTypeRepository;

    public void addEmploymentType(EmploymentTypeReq employmentTypeReq) {
        EmploymentType employmentType = new EmploymentType();
        employmentType.setCode(employmentTypeReq.getCode());
        employmentType.setName(employmentTypeReq.getName());
        employmentTypeRepository.save(employmentType);
    }

    public void modifyEmploymentType(EmploymentTypeReq employmentTypeReq) {
        EmploymentType employmentType = employmentTypeRepository.findById(employmentTypeReq.getId()).orElse(new EmploymentType());
        employmentType.setCode(employmentTypeReq.getCode());
        employmentType.setName(employmentTypeReq.getName());
        employmentTypeRepository.save(employmentType);
    }

    public Page<EmploymentType> getEmploymentType(String code, String name, Pageable pageable) {
        return employmentTypeRepository.getEmploymentType(code, name, pageable);
    }

    public List<EmploymentType> getEmploymentType() {
        return employmentTypeRepository.findAll();
    }

    public EmploymentType getEmploymentType(Long id) {
        return employmentTypeRepository.findById(id).orElse(null);
    }

    public void deleteEmploymentType(Long id) {
        employmentTypeRepository.deleteById(id);
    }
}
