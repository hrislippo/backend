package lippo.hris.system.recruitment.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserCandidatePool;
import lippo.hris.system.authentication.repository.UserCandidatePoolRepository;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.recruitment.entity.CandidatePool;
import lippo.hris.system.recruitment.repository.CandidatePoolRepository;
import lippo.hris.system.recruitment.request.CandidatePoolReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CandidatePoolService {

    @Autowired
    CandidatePoolRepository candidatePoolRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCandidatePoolRepository userCandidatePoolRepository;

    public void addCandidatePool(CandidatePoolReq candidatePoolReq) {
        CandidatePool candidatePool = new CandidatePool();
        candidatePool.setCode(candidatePoolReq.getCode());
        candidatePool.setName(candidatePoolReq.getName());
        candidatePoolRepository.save(candidatePool);
    }

    public void modifyCandidatePool(CandidatePoolReq candidatePoolReq) {
        CandidatePool candidatePool = candidatePoolRepository.findById(candidatePoolReq.getId()).get();
        candidatePool.setCode(candidatePoolReq.getCode());
        candidatePool.setName(candidatePoolReq.getName());
        candidatePoolRepository.save(candidatePool);
    }

    public Page<CandidatePool> getCandidatePool(String code, String name, Pageable pageable) {
        return candidatePoolRepository.findByCodeAndName(code, name, pageable);
    }

    public CandidatePool getCandidatePoolDetail(Long id) {
        return candidatePoolRepository.findById(id).get();
    }

    public List<CandidatePool> getCandidatePoolList() {
        return candidatePoolRepository.findAll();
    }

    public List<CandidatePool> getCandidatePoolUser(String username) {
        User user = userRepository.findByusername(username).get();
        List<CandidatePool> candidatePoolList = userCandidatePoolRepository.findByUser(user).stream().map(UserCandidatePool::getCandidatePool).toList();
        return candidatePoolList;
    }
}
