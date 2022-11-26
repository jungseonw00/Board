create table board
(
    id            bigint       not null auto_increment comment 'PK',
    title         varchar(100) not null comment '제목',
    content       text         not null comment '내용',
    writer        varchar(20)  not null comment '작성자',
    hits          int          not null comment '조회 수',
    delete_yn     enum('Y', 'N') not null comment '삭제 여부',
    created_date  datetime     not null comment '생성일',
    modified_date datetime comment '수정일',
    primary key (id)
) comment '게시판';

create table user (
      user_id varchar(50) not null primary key comment '아이디',
      password varchar(50) not null comment '비밀번호',
      name varchar(50) not null comment '별명',
      email varchar(50) not null comment '이메일',
      phone varchar(50) not null comment '휴대폰 번호',
      address varchar(50) not null comment '주소',
      detail_address varchar(50) not null comment '상세주소',
      delete_at varchar(50) not null comment '삭제여부',
      created_date datetime comment '생성날짜'
);