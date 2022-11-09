package com.jpaBoard.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;

// MyBatis의 Mapper를 JPA에서는 Repository라고 부른다.
public interface BoardRepository  extends JpaRepository<Board, Long> {
}
