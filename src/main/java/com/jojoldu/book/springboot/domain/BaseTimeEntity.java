package com.jojoldu.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate //Entity가 생성되어 저장될때 시간이 자동 저장됩니다.
    private LocalDateTime createDate;

    @LastModifiedDate //Entity의 값이 변경되어 저장될 때 시간이 자동 저장됩니다.
    private LocalDateTime modifiedDate;

}
