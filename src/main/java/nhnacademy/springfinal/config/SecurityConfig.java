package nhnacademy.springfinal.config;

import nhnacademy.springfinal.config.handler.CustomAuthenticationFailureHandler;
import nhnacademy.springfinal.config.handler.CustomAuthenticationSuccessHandler;
import nhnacademy.springfinal.config.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/member/**").hasRole("MEMBER")
//                        .requestMatchers("/google/**").hasRole("GOOGLE")
//                        .requestMatchers("/members").permitAll()
//                        .requestMatchers("/auth/login").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .anyRequest().authenticated()
                );
        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable); // (테스트용) CSRF 비활성화


        http.formLogin((formLogin) ->
                formLogin.loginPage("/auth/login") // 인증이 없으면 튕길 페이지
                        .usernameParameter("id")
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/process") // 로그인 프로세싱 URI 라고 security한테 알려줌
                        .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 핸들러
                        .failureHandler(customAuthenticationFailureHandler) //
        );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
//                .invalidateHttpSession(true) // 세션 무효화
//                .deleteCookies("SESSIONID") // 쿠키 삭제
                // --> 이렇게 하면 더 편한데 굳이 로그아웃 핸들러에서 삭제하는 이유는 모르겠음
        );

//        http.formLogin(Customizer.withDefaults()); // 기본 폼
        // ----> 여기서부터 시잒!!

        // Username어쩌구 필터보다 먼저 수행되는 필터 추가
        http.addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/403")
        ); // 403 에러 처리

        return http.build();
    }
}
