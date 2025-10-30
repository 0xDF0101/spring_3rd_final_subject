package nhnacademy.springfinal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
                );
        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable); // (테스트용) CSRF 비활성화

        return http.build();
    }
}
