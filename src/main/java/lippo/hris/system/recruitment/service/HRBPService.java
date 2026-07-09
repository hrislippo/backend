package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.BusinessUnit;
import lippo.hris.system.recruitment.entity.HRBP;
import lippo.hris.system.recruitment.repository.BusinessUnitRepository;
import lippo.hris.system.recruitment.repository.HRBPRepository;
import lippo.hris.system.recruitment.request.HRBPReq;
import lippo.hris.system.recruitment.response.HRBPResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@Transactional
public class HRBPService {

    @Autowired
    BusinessUnitRepository businessUnitRepository;

    @Autowired
    HRBPRepository hrbpRepository;

    public void addHRBP(HRBPReq hrbpReq) {
        BusinessUnit businessUnit = businessUnitRepository.findById(hrbpReq.getBusinessUnitId()).get();

        HRBP hrbp = new HRBP();
        hrbp.setBusinessUnit(businessUnit);
        hrbp.setCode(hrbpReq.getCode());
        hrbp.setName(hrbpReq.getName());
        hrbpRepository.save(hrbp);
    }

    public void modifyHRBP(HRBPReq hrbpReq) {
        BusinessUnit businessUnit = businessUnitRepository.findById(hrbpReq.getBusinessUnitId()).get();

        HRBP hrbp = hrbpRepository.findById(hrbpReq.getId()).get();
        hrbp.setBusinessUnit(businessUnit);
        hrbp.setCode(hrbpReq.getCode());
        hrbp.setName(hrbpReq.getName());
        hrbpRepository.save(hrbp);
    }

    public Page<HRBPResp> getHRBP(String BUName, String code, String name, Pageable pageable) {
        return hrbpRepository.findByCodeAndName(BUName, code, name, pageable);
    }

    public List<HRBP> getHRBPList(Long BUId) {
        if(BUId != null){
            BusinessUnit businessUnit = businessUnitRepository.findById(BUId).get();
            return hrbpRepository.findByBusinessUnit(businessUnit);
        }
        return hrbpRepository.findAll();
    }

    public HRBP getHRBPDetail(Long id) {
        return hrbpRepository.findById(id).get();
    }

    public void deleteHRBP(Long id) {
        hrbpRepository.deleteById(id);
    }
}
