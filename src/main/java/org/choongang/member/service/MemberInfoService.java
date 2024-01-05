package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username) // 이메일 조회
                .orElseGet(() -> memberRepository.findByUserId(username) // 아이디 조회
                        .orElseThrow(() -> new UsernameNotFoundException(username))); // 둘 다 없으면 던진다.

        List<SimpleGrantedAuthority> authorities = null;
        List<Authorities> tmp = member.getAuthorities(); // DB에 기록된 권한 데이터
        if (tmp != null) {
            // DB에 기록된 권한 데이터를 문자열로 바꿔서 저장
            authorities = tmp.stream().map(s -> new SimpleGrantedAuthority(s.getAuthority().name())).toList();
            // .name() : 상수의 이름
            // .toList() : 변환된 데이터를 리스트형태로
        }

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }
}
