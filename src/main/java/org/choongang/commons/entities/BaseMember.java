package org.choongang.commons.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseMember extends Base{

    @CreatedBy
    @Column(length = 40, updatable = false) // updatable = false : update를 막는다
    private String createdBy; // 데이터 추가한 사용자

    @LastModifiedDate
    @Column(length = 40, insertable = false) // insertable = false : insert를 막는다.
    private String modifiedBy; // 데이터 수정한 사용자
}
