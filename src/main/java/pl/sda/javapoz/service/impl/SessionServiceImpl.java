package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.service.SessionService;

@Service
public class SessionServiceImpl implements SessionService {

    private UserRepository userRepository;

    @Autowired
    public SessionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public pl.sda.javapoz.model.User getCurrentUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            pl.sda.javapoz.model.User user = userRepository.findUserByEmail(email);
            return user;
        }

        return null;
    }

}
