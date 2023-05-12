package com.study.board.controller;

import com.study.board.dto.Board;
import com.study.board.dto.BoardRequestDto;
import com.study.board.dto.BoardResponseDto;
import com.study.board.model.BoardService;
import com.study.exception.CustomException;
import com.study.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @RestController = @Controller + @ResponseBody
 * 주 용도는 JSON 형태로 객체 데이터를 반환하는 것이며 REST API를 개발할 때 주로 사용한다.
 * 객체를 ResponseEntity로 감싸서 반환한다.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final BoardService boardService;

    // 게시글 생성
    @PostMapping("/boards")
    public Long save(@RequestBody final BoardRequestDto params) {
        log.info("params.toString() = " + params.toString());
        return boardService.save(params);
    }

    // 게시글 수정
    @PatchMapping("/boards/{id}")
    public Long update(@PathVariable final Long id, @RequestBody final BoardRequestDto params) {
        return boardService.update(id, params);
    }

    // 게시글 삭제
    @DeleteMapping("/boards/{id}")
    public Long delete(@PathVariable final Long id) {
        return boardService.delete(id);
    }

    // 게시글 상세정보 조회
    @GetMapping("/boards/{id}")
    public BoardResponseDto findById(@PathVariable final Long id) {
        return boardService.findById(id);
    }

    @GetMapping("/test")
    public String test() {
        System.out.println("BoardApiController.test");
        throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
    }

    @PostMapping("/boards/test")
    public String test(@RequestBody final Board params) {
        log.info("test method Start...");
        log.info("params = {} ", params.toString());
        return "ok";
    }
}
