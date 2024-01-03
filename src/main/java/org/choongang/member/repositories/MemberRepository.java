package org.choongang.member.repositories;

import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, QuerydslPredicateExecutor<Member> {

    // Optional : null인 값을 참조해도 NullPointerException이 발생하지 않도록 값을 래퍼로 감싸주는 타입입니다
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByEmail (String email);
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findByUserId(String userId);

    // QuerydslPredicateExecutor<Member> 이걸써야 조건식(predicate)을 추가로 할수있다.

    default boolean existsByEmail(String email) {
        QMember member = QMember.member;

        return exists(member.email.eq(email)); // 이메일이 같은지 확인
    }

    default boolean existByUserId(String userId) {
        QMember member = QMember.member;

        return exists(member.userId.eq(userId)); // 아이디가 같은지 확인
    }
}
