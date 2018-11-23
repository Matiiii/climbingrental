package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.NavbarLink;
import pl.sda.javapoz.repository.NavbarLinkRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NavbarLinkService {

    private NavbarLinkRepository navbarLinkRepository;

    @Autowired
    public NavbarLinkService(NavbarLinkRepository navbarLinkRepository) {
        this.navbarLinkRepository = navbarLinkRepository;
    }

    public List<NavbarLink> fetchLinks() {
        List<NavbarLink> navbarsLinks = new ArrayList<NavbarLink>();
        Iterable<NavbarLink> iterable = navbarLinkRepository.findAll();
        iterable.forEach(navbarsLinks::add);
        return navbarsLinks;
    }

    public void save(NavbarLink navbarLink) {
        navbarLinkRepository.save(navbarLink);
    }

    public void afterLogin() {

        NavbarLink loginLink = navbarLinkRepository.findByName("Login");
        NavbarLink registerLink = navbarLinkRepository.findByName("Register");
        boolean isLoginLinkActive = loginLink.isActive();
        boolean isRegisterLinkActive = registerLink.isActive();

        if (isLoginLinkActive || isRegisterLinkActive) {
            loginLink.setActive(false);
            registerLink.setActive(false);
        }
        navbarLinkRepository.save(loginLink);
        navbarLinkRepository.save(registerLink);

    }

    public void afterLogout() {
        NavbarLink loginLink = navbarLinkRepository.findByName("Login");
        NavbarLink registerLink = navbarLinkRepository.findByName("Register");
        loginLink.setActive(true);
        registerLink.setActive(true);
        navbarLinkRepository.save(loginLink);
        navbarLinkRepository.save(registerLink);
    }


}
