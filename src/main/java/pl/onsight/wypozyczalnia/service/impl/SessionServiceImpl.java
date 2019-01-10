package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.repository.UserRepository;

@Service
public class SessionServiceImpl implements SessionService {

    private UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity getCurrentUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            UserEntity user = userRepository.findUserByEmail(email);
            return user;
        }

        return null;
    }

}
