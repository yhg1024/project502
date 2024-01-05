package org.choongang;

import org.choongang.member.Authority;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.AuthoritiesRepository;
import org.choongang.member.repositories.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
class ProjectApplicationTests {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	// 관리자 계정 만들기
	@Test //@Disabled
	void contextLoads() {
		Member member = memberRepository.findByUserId("user02").orElse(null);

		Authorities authorities = new Authorities(); // 권한 추가
		authorities.setMember(member);
		authorities.setAuthority(Authority.ADMIN); // 관리자 권한 추가

		authoritiesRepository.saveAndFlush(authorities);
	}

}
