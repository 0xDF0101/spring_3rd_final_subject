package nhnacademy.springfinal.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String KEY_NAME = "LoginCounter";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

//        Long cnt = redisTryLogin.opsForValue().get(KEY_NAME);
//        if(cnt==null) {
//            redisTryLogin.opsForValue().set(KEY_NAME, 0L); // null일 경우 0으로 초기화
//        }
//        cnt++;
//        if(cnt>5) { // 로그인 시도 횟수 5회 넘음
//            log.warn("로그인 시도 횟수가 5번을 초과했습니다.");
//             ----> 여기다가 로직 넣어야 함
//        }
//        redisTryLogin.opsForValue().set(KEY_NAME, cnt);


        response.sendRedirect("/auth/login?error=true");
    }
}