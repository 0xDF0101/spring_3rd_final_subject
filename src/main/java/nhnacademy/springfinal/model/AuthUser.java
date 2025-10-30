package nhnacademy.springfinal.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AuthUser implements UserDetails {

    private MemberResponse memberResponse;
    public AuthUser(MemberResponse memberResponse) {
        this.memberResponse = memberResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + memberResponse.getRole().name()));
    }

    @Override
    public String getPassword() {
        return memberResponse.getPassword();
    }

    @Override
    public String getUsername() {
        return memberResponse.getId();
    }
}
