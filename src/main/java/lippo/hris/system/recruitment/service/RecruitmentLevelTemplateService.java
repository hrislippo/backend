package lippo.hris.system.recruitment.service;

import lippo.hris.system.recruitment.entity.RecruitmentLevelTemplate;
import lippo.hris.system.recruitment.repository.RecruitmentLevelTemplateRepository;
import lippo.hris.system.recruitment.request.LevelTemplateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecruitmentLevelTemplateService {

    @Autowired
    RecruitmentLevelTemplateRepository recruitmentLevelTemplateRepository;

    public void addLevelTemplate(LevelTemplateReq levelTemplateReq) {
        RecruitmentLevelTemplate recruitmentLevelTemplate = new RecruitmentLevelTemplate();
        recruitmentLevelTemplate.setCode(levelTemplateReq.getCode());
        recruitmentLevelTemplate.setName(levelTemplateReq.getName());
        recruitmentLevelTemplate.setDays(levelTemplateReq.getDays());
        recruitmentLevelTemplateRepository.save(recruitmentLevelTemplate);
    }

    public void modifyLevelTemplate(LevelTemplateReq levelTemplateReq) {
        RecruitmentLevelTemplate recruitmentLevelTemplate = recruitmentLevelTemplateRepository.findById(levelTemplateReq.getId()).get();
        recruitmentLevelTemplate.setCode(levelTemplateReq.getCode());
        recruitmentLevelTemplate.setName(levelTemplateReq.getName());
        recruitmentLevelTemplate.setDays(levelTemplateReq.getDays());
        recruitmentLevelTemplateRepository.save(recruitmentLevelTemplate);
    }

    public Page<RecruitmentLevelTemplate> getLevelTemplate(String code, String name, Pageable pageable) {
        return recruitmentLevelTemplateRepository.getLevelTemplate(code, name, pageable);
    }

    public List<RecruitmentLevelTemplate> getLevelTemplateList() {
        return recruitmentLevelTemplateRepository.findAll();
    }

    public RecruitmentLevelTemplate getLevelTemplateDetail(Long id) {
        return recruitmentLevelTemplateRepository.findById(id).get();
    }

    public void deleteLevelTemplate(Long id) {
        recruitmentLevelTemplateRepository.deleteById(id);
    }
}
