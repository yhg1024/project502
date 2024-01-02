package org.choongang.member.service;

import lombok.RequiredArgsConstructor;
import org.choongang.member.controllers.JoinValidator;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final JoinValidator validator;
    private final PasswordEncoder encoder;

    public void process(RequestJoin form, Errors errors) {
        validator.validate(form, errors); // 검증
        if (errors.hasErrors()) {
            return;
        }

        // 비밀번호 BCrypt로 해시화
        String hash = encoder.encode(form.getPassword());

        // Member member = new ModelMapper().map(form, Member.class);
        // 동일한 패턴을 가지고 데이터 치환
        Member member = new Member();
        member.setEmail(form.getEmail());
        member.setName(form.getName());
        member.setUserId(form.getUserId());

        member.setPassword(hash);

        process(member);
    }

    public void process(Member member) {
        memberRepository.saveAndFlush(member); // 커맨드 객체 형태로 들어온다.
    }
}
