package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.User;
import lippo.hris.system.authentication.entity.UserHRBP;
import lippo.hris.system.authentication.repository.UserHRBPRepository;
import lippo.hris.system.authentication.repository.UserRepository;
import lippo.hris.system.authentication.request.UserHRBPRequest;
import lippo.hris.system.recruitment.entity.HRBP;
import lippo.hris.system.recruitment.repository.HRBPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserHRBPService {

    @Autowired
    UserHRBPRepository userHRBPRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HRBPRepository hrbpRepository;

    public void updateHRBP(UserHRBPRequest userHRBPRequest) {
        User user = userRepository.findByusername(userHRBPRequest.getUsername()).orElse(null);
        List<String> existingHBRP = userHRBPRepository.findByUser(user.getUsername()).stream().toList();
        List<String> added = new ArrayList<>(userHRBPRequest.getHrbp());
        added.removeAll(existingHBRP);
        List<String> removed = new ArrayList<>(existingHBRP);
        removed.removeAll(userHRBPRequest.getHrbp());
        removed = removed.stream().filter(Objects::nonNull).toList();

        for(String addedHRBP: added) {
            UserHRBP userHRBP = new UserHRBP();
            HRBP hrbp = hrbpRepository.findByCode(addedHRBP);
            userHRBP.setHrbp(hrbp);
            userHRBP.setUser(user);
            userHRBPRepository.save(userHRBP);
        }

        for(String removedHRBP: removed) {
            UserHRBP userHRBP = findByUserAndHRBP(user, hrbpRepository.findByCode(removedHRBP));
            userHRBPRepository.delete(userHRBP);
        }
    }

    public List<String> getUserHRBP(String username){
        return userHRBPRepository.findByUser(username);
    }

    private UserHRBP findByUserAndHRBP(User user, HRBP hrbp){
        return userHRBPRepository.findByUserAndHrbp(user, hrbp);
    }
}
