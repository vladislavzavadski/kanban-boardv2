package by.bsuir.kanban.service.config;

import by.bsuir.kanban.domain.User;
import by.bsuir.kanban.domain.to.UserDTO;
import by.bsuir.kanban.service.converter.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by ulza1116 on 4/7/2017.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;
    private final Converter<UserDTO, User> userConverter;


    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService, JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder, Converter<UserDTO, User> userConverter) {
        this.userDetailsService = userDetailsService;
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
        this.userConverter = userConverter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().anonymous().disable().formLogin().loginPage("/login").
                usernameParameter("username").passwordParameter("password").
                failureHandler(authenticationFailureHandler()).successHandler(authenticationSuccessHandler()).and().logout().logoutUrl("/logout").
                clearAuthentication(true).invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHandler()).and().
                authorizeRequests().antMatchers("/currentuser").authenticated().and().rememberMe().rememberMeParameter("remember")
        .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(Integer.MAX_VALUE).and().exceptionHandling().
                accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return (httpServletRequest, httpServletResponse, authentication) -> {
            ObjectMapper objectMapper = objectMapper();
            byte[]  serializedObject = objectMapper.writeValueAsBytes(userConverter.convert((User)authentication.getPrincipal()));

            httpServletResponse.getOutputStream().write(serializedObject);
            httpServletResponse.setContentLength(serializedObject.length);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
        };
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){

        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setCreateTableOnStartup(false);
        jdbcTokenRepository.setJdbcTemplate(jdbcTemplate);

        return jdbcTokenRepository;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return ((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN));
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return (httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.sendError(HttpServletResponse.SC_OK);
    }

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
