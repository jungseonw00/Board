package com.study.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Board {

    private String title;
    private String content;
    private String writer;
    private String deleteYn;

    @Override
    public String toString() {
        return "Board{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", deleteYn='" + deleteYn + '\'' +
                '}';
    }
}
