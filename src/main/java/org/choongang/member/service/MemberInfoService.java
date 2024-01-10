package org.choongang.member.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ListData;
import org.choongang.commons.Pagination;
import org.choongang.commons.Utils;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileInfoService;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Authorities;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
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
    private final FileInfoService fileInfoService;
    private final HttpServletRequest request;
    private final EntityManager em;
    // EntityManager : 데이터의 상태 변화를 감지하고 필요한 쿼리를 자동으로 수행

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

        /* 프로필 이미지 처리 S */
        List<FileInfo> files = fileInfoService.getListDone(member.getGid());
        if (files != null && !files.isEmpty()) {
            member.setProfileImage(files.get(0));
        }

        /* 프로필 이미지 처리 E */

        return MemberInfo.builder()
                .email(member.getEmail())
                .userId(member.getUserId())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }

    /**
     *  회원 목록
     *
     * @param search
     * @return
     */
    public ListData<Member> getList(MemberSearch search) { // 목록 데이터를 가져온다.
        int page = Utils.onlyPositiveNumber(search.getPage(), 1); // 페이지 번호
        int limit = Utils.onlyPositiveNumber(search.getLimit(), 20); // 1페이지당 레코드 갯수
        int offset = (page -1) * limit; // 레코드 시작 위치 번호

        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;
        // 쿼리 빌더 : 쿼리를 편리하게 작성하기 위해 사용

        PathBuilder<Member> pathBuilder = new PathBuilder<>(Member.class, "member");

        List<Member> items = new JPAQueryFactory(em)
                .selectFrom(member)
                .leftJoin(member.authorities) // 지연로딩이라
                .fetchJoin() //바로 가져올 수있게 fetchJoin
                .where(andBuilder)
                .limit(limit)
                .offset(offset)
                .orderBy(new OrderSpecifier(Order.DESC, pathBuilder.get("createdAt")))
                .fetch();

        // 페이징 처리 S
        int total = (int) memberRepository.count(andBuilder); // 총 레코드 갯수
        total = 123456;
        Pagination pagination = new Pagination(page, total, 10, limit, request);

        // 페이징 처리 E

        return new ListData<>(items, pagination);
    }
}
