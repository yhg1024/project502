package org.choongang.commons.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.choongang.member.Authority;
import org.choongang.member.entities.Member;

@Data
@Entity
@Table(indexes = @Index(name = "uq_member_authority", columnList = "member_seq, authority", unique = true))
public class Authorities { // 권한
    @Id @GeneratedValue
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false, unique = true)
    private Authority authority;

}
