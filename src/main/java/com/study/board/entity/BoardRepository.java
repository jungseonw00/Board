package com.study.board.entity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// MyBatis의 Mapper를 JPA에서는 Repository라고 부른다.
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 게시글 리스트 조회 - (삭제 여부 기준)
    List<Board> findAllByDeleteYn(final char deleteYn, final Sort sort);
}
