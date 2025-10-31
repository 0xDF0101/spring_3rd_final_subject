package nhnacademy.springfinal.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhnacademy.springfinal.model.AuthUser;
import nhnacademy.springfinal.model.MemberEntity;
import nhnacademy.springfinal.model.MemberResponse;
import nhnacademy.springfinal.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate<String, Object> redisTemplate;
    private MemberService memberService;

    public UserAuthenticationFilter(RedisTemplate<String, Object> redisTemplate, MemberService memberService) {
        this.redisTemplate = redisTemplate;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO 쿠키에서 세션 아이디를 가져오세요.
        String sessionId = getSessionId(request);
        // ---> 이전에 인증을 받았다면, 사용자는 세션ID가 든 쿠키를 같이 보내왔을거임

        if (sessionId != null) {

            Object o = redisTemplate.opsForValue().get(sessionId);
            String id = (String) o;

            MemberResponse member = memberService.getMember(id);
            AuthUser authUser = new AuthUser(member);

            Authentication auth = new PreAuthenticatedAuthenticationToken(authUser, null, authUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
    private String getSessionId(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("SESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
