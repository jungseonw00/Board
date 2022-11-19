package com.study.board.dto;

import com.study.board.entity.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
/**
 * 요청 DTO 클래스
 * (Entity 클래스는 요청이나 응답에 사용되서는 안된다.)
 */
public class BoardRequestDto {

    private String title;   // 제목
    private String content; // 내용
    private String writer;  // 작성자
    private char deleteYn;  // 삭제 여부

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .hits(0)
                .deleteYn(deleteYn)
                .build();
    }

    @Override
    public String toString() {
        return "BoardRequestDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", deleteYn=" + deleteYn +
                '}';
    }
}
