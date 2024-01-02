package org.choongang.member.service;

import lombok.Builder;
import lombok.Data;
import org.choongang.member.entities.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {

    private String email;
    private String userId;
    private String password;
    private Member member;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 계정이 갖고 있는 권한 목록을 리턴한다.
        return authorities;
    }

    @Override
    public String getPassword() { // 계정의 패스워드를 리턴한다.
        return password;
    }

    @Override
    public String getUsername() { // 계정의 이름을 리턴한다.
        return StringUtils.hasText(email) ? email : userId;
    }

    @Override
    public boolean isAccountNonExpired() { // 계정이 만료되지 않았는지를 리턴한다.(true를 리턴하면 만료되지 않음을 의미)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있지 않은지를 리턴한다.(true를 리턴하면 계정이 잠겨있지 않음을 의미)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 계정의 패스워드가 만료되지 않았는지를 리턴한다.(true를 리턴하면 사용가능한 계정인지 의미)
        return true;
    }

    @Override
    public boolean isEnabled() { // 탈퇴 기능
        return true;
    }
}
