package nhnacademy.springfinal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginLockFilter  extends OncePerRequestFilter {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String LOCK = "Lock";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String requestUri = request.getRequestURI();
        if(requestUri.equals("/locked")) {
            filterChain.doFilter(request, response);
        }


        if(redisTemplate.hasKey(LOCK)) {
            response.sendRedirect("/locked");
            log.warn("[로그인 잠금 상태]");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
