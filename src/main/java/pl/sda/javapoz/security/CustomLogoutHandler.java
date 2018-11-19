package pl.sda.javapoz.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pl.sda.javapoz.service.NavbarLinkService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pablo on 28.03.17.
 */
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private NavbarLinkService navbarLinkService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        navbarLinkService.afterLogout();
    }
}