package pl.onsight.wypozyczalnia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserDetailService myUserDetailService;

    @Autowired
    public SecurityConfig(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/static/**").permitAll()
                .antMatchers("/user").authenticated()
                .antMatchers("/admin-page").hasRole("ADMIN")
                .antMatchers("/shop", "/shop/**", "/").permitAll()
                .antMatchers("/register", "/news/**").permitAll()
                .antMatchers("/cart", "/contact", "/product/**").permitAll()
                .antMatchers("/login*", "/signin/**", "/signup/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
