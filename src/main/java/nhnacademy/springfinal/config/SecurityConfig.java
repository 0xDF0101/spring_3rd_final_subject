package nhnacademy.springfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
//                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                );
        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable); // (테스트용) CSRF 비활성화
//        http.formLogin((formLogin) ->
//                formLogin.loginPage("/auth/login") // 인증이 없으면 튕길 페이지
//                        .usernameParameter("id")
//                        .passwordParameter("pwd")
//                        .loginProcessingUrl("/login/process") // 로그인 프로세싱 URI 라고 security한테 알려줌
//                        .successHandler(customAuthenticationSuccessHandler) // 로그인 성공 시 핸들러
//                        .failureHandler(customAuthenticationFailureHandler) //
//        );

        http.formLogin(Customizer.withDefaults());
        // ----> 여기서부터 시잒!!

        return http.build();
    }
}
