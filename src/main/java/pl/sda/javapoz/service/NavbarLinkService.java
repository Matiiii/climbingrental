package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.NavbarLink;
import pl.sda.javapoz.repository.NavbarLinkRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class NavbarLinkService {

    private NavbarLinkRepository navbarLinkRepository;

    @Autowired
    public NavbarLinkService(NavbarLinkRepository navbarLinkRepository) {
        this.navbarLinkRepository = navbarLinkRepository;
    }

    public List<NavbarLink> fetchLinks() {
        List<NavbarLink> navbarLinks = new LinkedList<>();
        navbarLinkRepository.findAll().forEach(navbarLinks::add);
        return navbarLinks;
    }

    public void save(NavbarLink navbarLink) {
        navbarLinkRepository.save(navbarLink);
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
