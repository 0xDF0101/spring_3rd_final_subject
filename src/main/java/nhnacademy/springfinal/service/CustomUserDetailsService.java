package nhnacademy.springfinal.service;

import nhnacademy.springfinal.model.AuthUser;
import nhnacademy.springfinal.model.MemberEntity;
import nhnacademy.springfinal.model.MemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberResponse memberResponse = memberService.getMember(username);
        return new AuthUser(memberResponse);
    }
}
