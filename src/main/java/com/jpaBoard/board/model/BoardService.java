package com.jpaBoard.board.model;

import com.jpaBoard.board.dto.BoardRequestDto;
import com.jpaBoard.board.entity.Board;
import com.jpaBoard.board.entity.BoardRepository;
import com.jpaBoard.board.entity.BoardResponseDto;
import com.jpaBoard.exception.CustomException;
import com.jpaBoard.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 게시글 생성
    @Transactional // 각각의 실행, 조ㅓㅇ료, 예외를 자동으로 처리한다.
    public Long save(final BoardRequestDto params) {
        Board entity = boardRepository.save(params.toEntity());
        return entity.getId();
    }

    // 게시글 리스트 조회
    public List<BoardResponseDto> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdDate");
        List<Board> list = boardRepository.findAll(sort);
        return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    // 게시글 수정
    @Transactional
    public Long update(final Long id, final BoardRequestDto params) {
        Board entity = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        entity.update(params.getTitle(), params.getContent(), params.getWriter());
        return id;
    }
}
