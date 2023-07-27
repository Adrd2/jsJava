package ru.kata.spring.boot_security.demo.configs;

import ch.qos.logback.core.joran.action.NOPAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import ru.kata.spring.boot_security.demo.service.SecurityUserService;

import javax.servlet.ServletContext;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final SecurityUserService securityUserService;


    public WebSecurityConfig(SuccessUserHandler successUserHandler, SecurityUserService securityUserService) {
        this.successUserHandler = successUserHandler;
        this.securityUserService = securityUserService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/users/", "/users/new").hasRole("admin")
                .antMatchers("/", "/error", "/auth/registration").permitAll()
                .anyRequest().hasAnyRole("user", "admin")
                .and()
                .formLogin().successHandler(successUserHandler)
                .defaultSuccessUrl("/users/", true)
                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .permitAll();
    }

    // аутентификация inMemory
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() { return NoOpPasswordEncoder.getInstance(); }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}