#DB 생성
DROP DATABASE IF EXISTS SB_AM_04;
CREATE DATABASE SB_AM_04;
USE SB_AM_04;

#----------
SELECT * FROM board;
SELECT * FROM article;
SELECT * FROM `member`;
#----------


# 게시판 테이블 생성
SELECT * FROM board;

CREATE TABLE board(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항), free(자유), qna(질의응답), ...',
    `name` CHAR(20) NOT NULL UNIQUE COMMENT '게시판 이름',
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부 (0=삭제 전, 1=삭제 후)',
    delDate DATETIME COMMENT '삭제 날짜'
);

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'notice',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'free',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'qna',
`name` = '질의응답';


# 게시물 테이블 생성
SELECT * FROM article;

CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

# 게시물 테이블 구조 변경 - memberId 컬럼 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;
# 게시물 테이블 구조 변경 - boardId 컬럼 추가
ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER `memberId`;

UPDATE article
SET boardId = 1
WHERE id IN (1,2);

UPDATE article
SET boardId = 2;

# 게시물 테스트데이터 생성
INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목 1',
`body` = '내용 1';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
title = '제목 2',
`body` = '내용 2';

INSERT INTO article 
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
title = '제목 3',
`body` = '내용 3';

# URL
# http://localhost:8081/usr/member/doLogin?loginId=admin&loginPw=admin

# 회원 테이블 생성
DROP TABLE IF EXISTS `member`;

SELECT * FROM `member`;
SELECT LAST_INSERT_ID();

CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨 (3=일반, 7=관리자)', # default가 3이므로 굳이 not null 넣을 필요는 없음
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNum CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴 여부 (0=탈퇴 전, 1=탈퇴 후)',
    delDate DATETIME COMMENT '탈퇴 날짜'
);

# 회원 테스트데이터 생성(관리자)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
authlevel = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNum = '01000000000',
email = 'admin@testSBAM04.com';

# 회원 테스트데이터 생성(일반)
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '테스트이름1',
nickname = '테스트별명1',
cellphoneNum = '01000000001',
email = 'abcde1@testSBAM04.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '테스트이름2',
nickname = '테스트별명2',
cellphoneNum = '01000000002',
email = 'abcde2@testSBAM04.com';

# URL
# localhost:8081/usr/member/doJoin?loginId=abc&loginPw=abc&name=abc&nickname=abc&cellphoneNum=01000000201&email=abc@testSBAM04.com

SELECT nickname
		FROM `article` AS a
		INNER JOIN `member` AS m
		ON a.memberId = m.id
		WHERE m.id = 1;