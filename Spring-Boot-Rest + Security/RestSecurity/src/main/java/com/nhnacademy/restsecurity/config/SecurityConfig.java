package com.nhnacademy.restsecurity.config;

import com.nhnacademy.restsecurity.service.CustomOAuth2UserService;
import com.nhnacademy.restsecurity.service.LoginService;
import com.nhnacademy.restsecurity.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class SecurityConfig {
    private final LoginService loginService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(LoginService loginService, CustomOAuth2UserService customOAuth2UserService) {
        this.loginService = loginService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
        .formLogin((formLogin) ->
                formLogin.loginPage("/main")
                        .usernameParameter("id")
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/process")
                        .successHandler(new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                String id = request.getParameter("id");
                                loginService.initializeLoginFailCount(id);
                                response.sendRedirect("/home");
                            }
                        })
                        .failureHandler(new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                String id = request.getParameter("id");
                                loginService.incrementLoginFailCount(id);

                                if (loginService.loginLockMemberCheck(id)) {
                                    log.info("id: {} (Login Failed Count = 5)", id);
                                }

                                response.sendRedirect("/auth/login");
                            }
                        })
        );

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2Login ->
                oauth2Login
                        .defaultSuccessUrl("/home", true)
                        .failureUrl("/main?error=true")
                        .userInfoEndpoint(userInfo ->
                                userInfo.userService(customOAuth2UserService)
                        )

        );

        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/auth/login/**", "/members/**", "/main").permitAll()
                .requestMatchers("/admin/**").hasAuthority("admin")
                .requestMatchers("/member/**").hasAnyAuthority("admin", "member")
                .requestMatchers("/google/**").hasAnyAuthority("admin", "google")
                .requestMatchers("/**").authenticated()
                .anyRequest().authenticated()
        );

        http.logout((logout) ->
                logout.deleteCookies("A-COOKIE", "B-COOKIE")
                        .invalidateHttpSession(true)
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/main"));

        http.exceptionHandling((exceptionHandling) ->
                exceptionHandling
                        .accessDeniedPage("/error/403")
        );

        return http.build();
    }
}
