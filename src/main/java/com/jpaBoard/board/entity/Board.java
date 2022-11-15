package com.jpaBoard.board.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
// 해당 클래스의 기본 생성자를 생성해 준다. (access 속성을 이용하여 동일 패키지에서만 객체를 생성할 수 있도록 설정)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 해당 클래스가 테이블과 매핑되는 JPA의 엔티티 클래스임을 의미함
@Entity
public class Board {

    // PK 설정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;        // PK
    private String title;   // 제목
    private String content; // 내용
    private String writer;  // 작성자
    private int hits;       // 조회수
    private char deleteYn;  // 삭제 여부
    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일
    private LocalDateTime modifiedDate; // 수정일

    @Builder
    public Board(String title, String content, String writer, int hits, char deleteYn) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.modifiedDate = LocalDateTime.now();
    }
}