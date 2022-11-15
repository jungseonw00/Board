package com.jpaBoard.board.controller;

import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardPageController {

    /**
     * 게시글 리스트 페이지
     */
    @GetMapping
    public String openBoardList() {
        System.out.println("openBoardList() Start... ");
        return "board/list";
    }
}
