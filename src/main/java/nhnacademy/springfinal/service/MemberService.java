package nhnacademy.springfinal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nhnacademy.springfinal.model.Member;
import nhnacademy.springfinal.model.MemberEntity;
import nhnacademy.springfinal.model.MemberRequest;
import nhnacademy.springfinal.model.MemberResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final String HASH_NAME = "Member";
    private final RedisTemplate redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public void createMember(MemberRequest memberRequest) {
        List<Object> allValues = redisTemplate.opsForHash().values(HASH_NAME);

        MemberEntity member;
        for(Object obj : allValues) {
            member = (MemberEntity) obj;
            if(memberRequest.getId().equals(member.getId())) {
                log.info("[등록 실패] 중복된 아이디입니다. : {}", member.getId());
                return;
            }
        }


        // 비밀번호 해싱
        String encodedPassword = passwordEncoder.encode(memberRequest.getPassword());
        MemberEntity memberEntity = new MemberEntity(memberRequest, encodedPassword);

        redisTemplate.opsForHash().put(
                HASH_NAME,
                memberEntity.getId(),
                memberEntity
        );
        log.info("등록 완료 : {}", memberEntity.getId());
    }

    public MemberResponse getMember(String id) {
        // id로 memberResponse 만들어서 반환하는 로직

        Object o = redisTemplate.opsForHash().get(HASH_NAME, id);
        if(o == null) {
            log.info("해당하는 멤버가 없습니다. : {}", id);
            throw new RuntimeException("해당 멤버가 없습니다.");
        }
        MemberEntity memberEntity = (MemberEntity) o;

        return new MemberResponse(memberEntity.getId(), memberEntity.getName(), memberEntity.getAge(), memberEntity.getRole());
    }
}
