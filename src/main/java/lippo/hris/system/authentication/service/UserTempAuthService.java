package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.TemplateAuth;
import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserRole;
import lippo.hris.system.authentication.entity.UserTemplateAuth;
import lippo.hris.system.authentication.repository.TemplateAuthRepository;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.authentication.repository.UserTemplateAuthRepository;
import lippo.hris.system.authentication.request.UserTempAuthReq;
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

    public void modifyUserTempAuth(UserTempAuthReq userTempAuthReq) {
        User user = userRepository.findByusername(userTempAuthReq.getUsername()).get();
        List<TemplateAuth> templateAuthList = userTemplateAuthRepository.findByUser(user).stream().map(UserTemplateAuth::getTemplateAuth).toList();

        List<Long> added = new ArrayList<>(userTempAuthReq.getTempAuthId());
        added.removeAll(templateAuthList.stream().map(TemplateAuth::getId).toList());
        List<Long> removed = new ArrayList<>(templateAuthList.stream().map(TemplateAuth::getId).toList());
        removed.removeAll(userTempAuthReq.getTempAuthId());

        for(Long addedTemplate: added) {
            UserTemplateAuth userTemplateAuth = new UserTemplateAuth();
            userTemplateAuth.setUser(user);
            userTemplateAuth.setTemplateAuth(templateAuthRepository.findById(addedTemplate).get());
            userTemplateAuthRepository.save(userTemplateAuth);
        }

        for(Long removedTemplate: removed) {
            UserTemplateAuth userTemplateAuth = userTemplateAuthRepository.findByUserAndTemplateAuth(user, templateAuthRepository.findById(removedTemplate).get());
            userTemplateAuthRepository.delete(userTemplateAuth);
        }
    }

    public List<TemplateAuth> getTemplateAuthList(@RequestParam("username") String username) {
        User user = userRepository.findByusername(username).get();
        return userTemplateAuthRepository.findByUser(user).stream().map(UserTemplateAuth::getTemplateAuth).toList();
    }
}
