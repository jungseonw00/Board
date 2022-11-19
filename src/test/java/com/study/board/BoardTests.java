package com.study.board;

import com.study.board.entity.Board;
import com.study.board.entity.BoardRepository;
import com.study.board.model.BoardMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardTests {

    // 스프링 컨테이너에 등록된 BoardRepository 객체를 주입받는다.
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardMapper boardMapper;

    @Test
    void save() {
        // 1. 게시글 파라미터 생성
        // 생성자로 객체를 생성했을 경우
        // Board params = new Board("1번 게시글 제목", "1번 게시글 내용", "정선우", 0, "N");
        Board params = Board.builder()
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .writer("정선우")
                .hits(0)
                .deleteYn('N')
                .build();

        // 2. 게시글 저장
        boardRepository.save(params);

        // 3. 1번 게시글 정보 조회
        // Board entity = boardRepository.findById((long) 1).get();
        // 최상단 ROW를 SELECT한다.
        Board entity = boardMapper.findTopContent();
        System.out.println("entity.getTitle() => " + entity.getTitle());
        System.out.println("entity.getContent() => " + entity.getContent());

        assertThat(entity.getTitle()).isEqualTo("1번 게시글 제목");
        assertThat(entity.getContent()).isEqualTo("1번 게시글 내용");
        assertThat(entity.getWriter()).isEqualTo("정선우");
    }

    @Test
    void findAll() {

        // 1. 전체 게시글 수 조회
        long boardsCount = boardRepository.count();

        // 2. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
    }

    @Test
    void delete() {

        // 1. 게시글 조회
        Board entity = boardRepository.findById((long) 1).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }
}
