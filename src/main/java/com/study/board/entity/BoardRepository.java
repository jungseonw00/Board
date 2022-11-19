package com.study.board.entity;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *  MyBatis의 Mapper를 JPA에서는 Repository라고 부른다.
 *  Entity 클래스와 Repository 인터페이스는 꼭! 같은 패키지에 위치해야 한다.
 */

// 클래스 타입과 데이터 타입을 선언하면 매핑된 board 테이블의 CRUD 기능을 사용할 수 있다.
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 게시글 리스트 조회 - (삭제 여부 기준)
    List<Board> findAllByDeleteYn(final char deleteYn, final Sort sort);
}
