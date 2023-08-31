package com.highright.highcare.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


/*
모든 entity의 상위크래스에서  createDate, updateData 를 자동으로 관리해주는 역할
Date 자료형을 지양하고 8버전에 추가된 LocalData, LocalDateTime을 사용하는 것을 추천한다고 한다.
BaseTimeyEntity 추상클래스를 구현하고 Entity 클래스들에게 상속시켜 사용한다.
* */
@Getter
@MappedSuperclass // jpa entity들이 BaseTimeEntity를 상속할 경우 createDate, modifiedDate 두 필드도 컬럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate; // 생성시 날짜 자동 생성

    @LastModifiedDate
    private LocalDateTime modifiedDate; // 수정시 날짜 자동 갱신
}
