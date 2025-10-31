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
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String KEY_NAME = "LoginCounter";
    private final String LOCK = "Lock";
    private RedisTemplate<String, Object> redisTemplate;
    public CustomAuthenticationFailureHandler(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        Object o = redisTemplate.opsForValue().get(KEY_NAME);
        if(o == null) {
            redisTemplate.opsForValue().set(KEY_NAME, 0);
        } else {
            long cnt = (Long) o;
            if(cnt > 5) {
                redisTemplate.expire(LOCK, 5, TimeUnit.MINUTES);
            } else {
                redisTemplate.opsForValue().set(KEY_NAME, ++cnt);
            }
        }

        response.sendRedirect("/auth/login?error=true");
    }
}