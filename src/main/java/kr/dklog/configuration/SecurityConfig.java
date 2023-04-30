package kr.dklog.configuration;

import kr.dklog.service.CustomOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2MemberService customOAuth2MemberService;

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .oauth2Login().loginPage("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2MemberService)
                .and()
                .defaultSuccessUrl("/")
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .logout().logoutSuccessUrl("/");

        return httpSecurity.build();
    }
}
