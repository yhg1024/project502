package org.choongang.member.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.choongang.commons.entities.Base;
import org.choongang.file.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "MEMBER")
@DiscriminatorColumn(name = "MTYPE") // 회원 구분
public abstract class AbstractMember extends Base{
    @Id @GeneratedValue
    private Long seq;

    @Column(length = 65, nullable = false)
    private String gid;

    @Column(length = 80, nullable = false, unique = true) // 글자는 80자, 빈칸x, 중복x
    private String email;

    @Column(length = 40, nullable = false, unique = true)
    private String userId;

    @Column(length = 65, nullable = false)
    private String password;

    @Column(length = 40, nullable = false)
    private String username;

    @Column(length = 40, nullable = false)
    private String nickname;

    @Column(length = 40, nullable = false)
    private String tel;

    @ToString.Exclude // 순환 참조 방지
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Authorities> authorities = new ArrayList<>();

    @Transient
    private FileInfo profileImage;
}