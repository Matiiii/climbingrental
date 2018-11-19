package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.repository.UserRepository;

/**
 * Created by pablo on 27.03.17.
 */
@Service
public class SessionService {


    @Autowired
    UserRepository userRepository;


    public pl.sda.javapoz.model.User getCurrentUser(){



        if(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            pl.sda.javapoz.model.User user = userRepository.findByEmail(email);

            return user;
        }else
            return null;
    }

}
