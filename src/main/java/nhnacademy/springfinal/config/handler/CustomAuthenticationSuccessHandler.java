package nhnacademy.springfinal.config.handler;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final String KEY_NAME = "LoginCounter";
    private RedisTemplate<String, Object> redisTemplate;
    public CustomAuthenticationSuccessHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

//         0 으로 초기화
//        redisTryLogin.opsForValue().set(KEY_NAME, 0L);

        // 로그인 성공시 세션에 로그인한 사용자 저장

        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setHttpOnly(true); // 보안 설정
        sessionCookie.setMaxAge(60 * 60);
        sessionCookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
        response.addCookie(sessionCookie); // response에 담아서 클라이언트한테 선물함
        redisTemplate.opsForValue().set(sessionId, authentication.getName()); // 그 세션ID + 인증받은 사용자ID를 저장함

        super.onAuthenticationSuccess(request, response, authentication);

    }
}