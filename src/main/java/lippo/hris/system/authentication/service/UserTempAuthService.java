package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.*;
import lippo.hris.system.authentication.repository.*;
import lippo.hris.system.authentication.request.UserTempAuthReq;
import lippo.hris.system.authentication.response.UserAuthResponse;
import lippo.hris.system.organization.entity.HRBP;
import lippo.hris.system.organization.repository.HRBPRepository;
import lippo.hris.system.recruitment.entity.CandidatePool;
import lippo.hris.system.recruitment.repository.CandidatePoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserTempAuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTemplateAuthRepository userTemplateAuthRepository;

    @Autowired
    TemplateAuthRepository templateAuthRepository;

    @Autowired
    CandidatePoolRepository candidatePoolRepository;

    @Autowired
    UserCandidatePoolRepository userCandidatePoolRepository;

    @Autowired
    UserHRBPRepository userHRBPRepository;

    @Autowired
    HRBPRepository hrbpRepository;

    public void modifyUserTempAuth(UserTempAuthReq userTempAuthReq) {
        User user = userRepository.findByusername(userTempAuthReq.getUsername()).get();
        List<TemplateAuth> templateAuthList = userTemplateAuthRepository.findByUser(user).stream().map(UserTemplateAuth::getTemplateAuth).toList();
        List<CandidatePool> candidatePoolList = userCandidatePoolRepository.findByUser(user).stream().map(UserCandidatePool::getCandidatePool).toList();
        List<HRBP> hrbpAuthList = userHRBPRepository.findByUser(user).stream().map(UserHRBP::getHrbp).toList();

        List<Long> addedTemplateList = new ArrayList<>(userTempAuthReq.getTempAuthId());
        addedTemplateList.removeAll(templateAuthList.stream().map(TemplateAuth::getId).toList());
        List<Long> removedTemplateList = new ArrayList<>(templateAuthList.stream().map(TemplateAuth::getId).toList());
        removedTemplateList.removeAll(userTempAuthReq.getTempAuthId());

        List<Long> addedCandidatePoolList = new ArrayList<>(userTempAuthReq.getCanPoolId());
        addedCandidatePoolList.removeAll(candidatePoolList.stream().map(CandidatePool::getId).toList());
        List<Long> removedCandidatePoolList = new ArrayList<>(candidatePoolList.stream().map(CandidatePool::getId).toList());
        removedCandidatePoolList.removeAll(userTempAuthReq.getCanPoolId());

        List<Long> addedHrbpList = new ArrayList<>(userTempAuthReq.getHrbpAuthId());
        addedHrbpList.removeAll(hrbpAuthList.stream().map(HRBP::getId).toList());
        List<Long> removedHrbpList = new ArrayList<>(hrbpAuthList.stream().map(HRBP::getId).toList());
        removedHrbpList.removeAll(userTempAuthReq.getHrbpAuthId());

        for(Long addedTemplate: addedTemplateList) {
            UserTemplateAuth userTemplateAuth = new UserTemplateAuth();
            userTemplateAuth.setUser(user);
            userTemplateAuth.setTemplateAuth(templateAuthRepository.findById(addedTemplate).get());
            userTemplateAuthRepository.save(userTemplateAuth);
        }

        for(Long removedTemplate: removedTemplateList) {
            UserTemplateAuth userTemplateAuth = userTemplateAuthRepository.findByUserAndTemplateAuth(user, templateAuthRepository.findById(removedTemplate).get());
            userTemplateAuthRepository.delete(userTemplateAuth);
        }

        for(Long addedCandidatePool: addedCandidatePoolList) {
            UserCandidatePool userCandidatePool = new UserCandidatePool();
            userCandidatePool.setUser(user);
            userCandidatePool.setCandidatePool(candidatePoolRepository.findById(addedCandidatePool).get());
            userCandidatePoolRepository.save(userCandidatePool);
        }

        for(Long removedCandidatePool: removedCandidatePoolList) {
            UserCandidatePool userCandidatePool = userCandidatePoolRepository.findByUserAndCandidatePool(user, candidatePoolRepository.findById(removedCandidatePool).get());
            userCandidatePoolRepository.delete(userCandidatePool);
        }

        for(Long addedHrbp: addedHrbpList) {
            UserHRBP userHRBP = new UserHRBP();
            userHRBP.setUser(user);
            userHRBP.setHrbp(hrbpRepository.findById(addedHrbp).get());
            userHRBPRepository.save(userHRBP);
        }

        for(Long removedHrbp: removedHrbpList) {
            UserHRBP userHRBP = userHRBPRepository.findByUserAndHrbp(user, hrbpRepository.findById(removedHrbp).get());
            userHRBPRepository.delete(userHRBP);
        }
    }

    public UserAuthResponse getTemplateAuthList(@RequestParam("username") String username) {
        User user = userRepository.findByusername(username).get();
        List<TemplateAuth> templateAuthList = userTemplateAuthRepository.findByUser(user).stream().map(UserTemplateAuth::getTemplateAuth).toList();
        List<CandidatePool> candidatePoolList = userCandidatePoolRepository.findByUser(user).stream().map(UserCandidatePool::getCandidatePool).toList();
        List<HRBP> hrbpList = userHRBPRepository.findByUser(user).stream().map(UserHRBP::getHrbp).toList();

        UserAuthResponse userAuthResponse = new UserAuthResponse();
        userAuthResponse.setTemplateAuthList(templateAuthList);
        userAuthResponse.setCandidatePoolList(candidatePoolList);
        userAuthResponse.setHrbpAuthList(hrbpList);
        return userAuthResponse;
    }
}
