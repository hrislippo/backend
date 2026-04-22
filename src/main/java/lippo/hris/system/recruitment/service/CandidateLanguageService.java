package lippo.hris.system.recruitment.service;

import lippo.hris.system.ocrengine.response.Language;
import lippo.hris.system.recruitment.entity.Candidate;
import lippo.hris.system.recruitment.entity.CandidateLanguage;
import lippo.hris.system.recruitment.repository.CandidateLanguageRepository;
import lippo.hris.system.recruitment.request.CandidateLanguageReq;
import lippo.hris.system.recruitment.request.CandidateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CandidateLanguageService {

    @Autowired
    CandidateLanguageRepository candidateLanguageRepository;

    public void save(Candidate candidate, List<CandidateLanguageReq> languages){
        for(CandidateLanguageReq language : languages){
            CandidateLanguage candidateLanguage = new CandidateLanguage();
            candidateLanguage.setCandidate(candidate);
            candidateLanguage.setLanguageName(language.getLanguageName());
            candidateLanguage.setProficiency(language.getProficiency());
            candidateLanguageRepository.save(candidateLanguage);
        }
    }

    public void upload(Candidate candidate, List<Language> languages){
        for(Language language : languages){
            CandidateLanguage candidateLanguage = new CandidateLanguage();
            candidateLanguage.setCandidate(candidate);
            candidateLanguage.setLanguageName(language.getLanguageName());
            candidateLanguage.setProficiency(language.getProficiency());
            candidateLanguageRepository.save(candidateLanguage);
        }
    }

    public void modify(Candidate candidate, List<CandidateLanguageReq> languages){
        List<CandidateLanguage> candidateLanguages = candidateLanguageRepository.findByCandidate(candidate);
        for(CandidateLanguageReq language : languages){
            CandidateLanguage candidateLanguage = new CandidateLanguage();
            if(language.getId() != null){
                candidateLanguage = candidateLanguages.stream()
                        .filter(l -> l.getId().equals(language.getId())).findFirst().get();
                candidateLanguages.removeIf(user -> user.getId().equals(language.getId()));
            } else{
                candidateLanguage.setCandidate(candidate);
            }
            candidateLanguage.setLanguageName(language.getLanguageName());
            candidateLanguage.setProficiency(language.getProficiency());
            candidateLanguageRepository.save(candidateLanguage);
        }

        for(CandidateLanguage language : candidateLanguages){
            candidateLanguageRepository.delete(language);
        }
    }

    public void setLanguage(Candidate candidate, CandidateReq candidateReq){
        List<CandidateLanguage> candidateLanguages = candidateLanguageRepository.findByCandidate(candidate);
        List<CandidateLanguageReq> languages = new ArrayList<>();
        for(CandidateLanguage candidateLanguage : candidateLanguages){
            CandidateLanguageReq language = new CandidateLanguageReq();
            language.setId(candidateLanguage.getId());
            language.setLanguageName(candidateLanguage.getLanguageName());
            language.setProficiency(candidateLanguage.getProficiency());
            languages.add(language);
        }
        candidateReq.setLanguage(languages);
    }

    public void delete(Candidate candidate){
        candidateLanguageRepository.deleteAllByCandidate(candidate);
    }
}
