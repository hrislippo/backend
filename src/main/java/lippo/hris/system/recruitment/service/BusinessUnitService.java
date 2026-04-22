package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.BusinessUnit;
import lippo.hris.system.recruitment.repository.BusinessUnitRepository;
import lippo.hris.system.recruitment.request.BusinessUnitReq;
import lippo.hris.system.recruitment.response.BusinessUnitResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusinessUnitService {

    @Autowired
    BusinessUnitRepository businessUnitRepository;

    public void addBusinessUnit(BusinessUnitReq businessUnitReq) {
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setCode(businessUnitReq.getCode());
        businessUnit.setName(businessUnitReq.getName());
        businessUnitRepository.save(businessUnit);
    }

    public void modifyBusinessUnit(BusinessUnitReq businessUnitReq) {
        BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitReq.getId()).get();
        businessUnit.setCode(businessUnitReq.getCode());
        businessUnit.setName(businessUnitReq.getName());
        businessUnitRepository.save(businessUnit);
    }

    public Page<BusinessUnitResp> getBusinessUnit(String code, String name, Pageable pageable) {
        return businessUnitRepository.findByCodeAndName(code, name, pageable);
    }

    public List<BusinessUnit> getBusinessUnitList() {
        return businessUnitRepository.findAll();
    }

    public BusinessUnit getBusinessUnitDetail(Long id) {
        return businessUnitRepository.findById(id).get();
    }

    public void deleteBusinessUnit(Long id) {
        businessUnitRepository.deleteById(id);
    }
}
