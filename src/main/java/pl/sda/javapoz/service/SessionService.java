package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.repository.UserRepository;

@Service
public class SessionService {


    UserRepository userRepository;

    @Autowired
    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public pl.sda.javapoz.model.User getCurrentUser() {
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            pl.sda.javapoz.model.User user = userRepository.findByEmail(email);

            return user;
        }

        return null;
    }

}
