package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.EmploymentType;
import lippo.hris.system.recruitment.entity.PositionLevel;
import lippo.hris.system.recruitment.repository.PositionLevelRepository;
import lippo.hris.system.recruitment.request.EmploymentTypeReq;
import lippo.hris.system.recruitment.request.PositionLevelReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PositionLevelService {

    @Autowired
    PositionLevelRepository positionLevelRepository;

    public void addPositionLevel(PositionLevelReq positionLevelReq) {
        PositionLevel positionLevel = new PositionLevel();
        positionLevel.setCode(positionLevelReq.getCode());
        positionLevel.setName(positionLevelReq.getName());
        positionLevelRepository.save(positionLevel);
    }

    public void modifyPositionLevel(PositionLevelReq positionLevelReq) {
        PositionLevel positionLevel = positionLevelRepository.findById(positionLevelReq.getId()).orElse(new PositionLevel());
        positionLevel.setCode(positionLevelReq.getCode());
        positionLevel.setName(positionLevelReq.getName());
        positionLevelRepository.save(positionLevel);
    }

    public Page<PositionLevel> getPositionLevel(String code, String name, Pageable pageable) {
        return positionLevelRepository.getPositionLevel(code, name, pageable);
    }

    public List<PositionLevel> getPositionLevel() {
        return positionLevelRepository.findAll();
    }

    public PositionLevel getPositionLevel(Long id) {
        return positionLevelRepository.findById(id).orElse(null);
    }

    public void deletePositionLevel(Long id) {
        positionLevelRepository.deleteById(id);
    }
}
