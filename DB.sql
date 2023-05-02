#DB 생성
DROP DATABASE IF EXISTS SB_AM_04;
CREATE DATABASE SB_AM_04;
USE SB_AM_04;

#---------------------------------------------------------------------------------------
SELECT * FROM reactionPoint;
SELECT * FROM board;
SELECT * FROM article;
SELECT * FROM `member`;
SELECT * FROM reply;
#---------------------------------------------------------------------------------------
# 좋아요 테이블 생성
SELECT * FROM reactionPoint;

CREATE TABLE reactionPoint(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `point` INT(10) NOT NULL
);

# reactionPoint 테스트 데이터
# 1번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 1번 회원이 2번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 1,
relTypeCode = 'article',
relId = 2,
`point` = 1;

# 2번 회원이 1번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 1,
`point` = -1;

# 2번 회원이 2번 글에 싫어요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'article',
relId = 2,
`point` = -1;

# 3번 회원이 1번 글에 좋아요
INSERT INTO reactionPoint
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'article',
relId = 1,
`point` = 1;

# 게시물 반응 개수 확인해서 각 article vo 인스턴스 필드에 저장
SELECT A.*,
M.nickname AS extra__writer,
IFNULL(SUM(RP.point), 0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)), 0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)), 0) AS extra__badReactionPoint
FROM article AS A
INNER JOIN `member` AS M ON A.memberId = M.id
LEFT JOIN reactionPoint AS RP ON A.id = RP.relId AND RP.relTypeCode = 'article'
GROUP BY A.id
ORDER BY A.id DESC;

## 서브쿼리 버전 참고
SELECT A.*, 
IFNULL(SUM(RP.point),0) AS extra__sumReactionPoint,
IFNULL(SUM(IF(RP.point > 0, RP.point, 0)),0) AS extra__goodReactionPoint,
IFNULL(SUM(IF(RP.point < 0, RP.point, 0)),0) AS extra__badReactionPoint
FROM (
	SELECT A.*, M.nickname AS extra__writerName
	FROM article AS A
	LEFT JOIN `member` AS M
	ON A.memberId= M.id 
) AS A
LEFT JOIN reactionPoint AS RP
ON RP.relTypeCode = 'article'
AND A.id = RP.relId
GROUP BY A.id
ORDER BY id DESC;


#---------------------------------------------------------------------------------------
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

#---------------------------------------------------------------------------------------
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
# 게시물 테이블 구조 변경 - hitCount 컬럼 추가
ALTER TABLE article ADD COLUMN hitCount INT(10) UNSIGNED NOT NULL;
# 게시물 테이블 구조 변경 - 추천 관련 컬럼 추가
ALTER TABLE article ADD COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL;
ALTER TABLE article ADD COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL;
# 게시물 테이블 변경 - 글 쓸 때 hitCount와 reactionPoint 디폴트 0으로
ALTER TABLE article MODIFY COLUMN hitCount INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE article MODIFY COLUMN goodReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;
ALTER TABLE article MODIFY COLUMN badReactionPoint INT(10) UNSIGNED NOT NULL DEFAULT 0;

# 기존 게시물의 good,bad ReactionPoint 필드의 값을 채운다
INNER JOIN (
    SELECT RP.relTypeCode, RP.relId,
    SUM(IF(RP.point > 0, RP.point, 0)) AS goodReactionPoint,
    SUM(IF(RP.point < 0, RP.point * -1, 0)) AS badReactionPoint
    FROM reactionPoint AS RP
    GROUP BY RP.relTypeCode, RP.relId
) AS RP_SUM
ON A.id = RP_SUM.relId
SET A.goodReactionPoint = RP_SUM.goodReactionPoint,
A.badReactionPoint = RP_SUM.badReactionPoint;

# ---------------------------------
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

# 게시물 개수 늘리기
INSERT INTO article
(regDate, updateDate, memberId, boardId, title, `body`)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 2, FLOOR(RAND() * 2) + 1, CONCAT('제목_', RAND()), CONCAT('내용_', RAND())
FROM article;



# URL
# http://localhost:8081/usr/member/doLogin?loginId=admin&loginPw=admin

#---------------------------------------------------------------------------------------
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
		
# 검색 기능 관련 임시 쿼리문
SELECT A.*, M.nickname AS extra__writer
FROM article AS A
INNER JOIN
`member` AS M
ON A.memberId = M.id
WHERE title LIKE '%제목%'
ORDER BY A.id DESC
LIMIT 0, 10;

SELECT COUNT(*) AS cnt
FROM article AS A
WHERE 1
AND A.boardId = 1
AND title LIKE CONCAT('%', '제목', '%');

# 좋아요 기능 관련 임시 쿼리문
SELECT *
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

SELECT IF(RP.point > 0, '큼','작음')
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

## 각 게시물 별 좋아요, 싫어요의 갯수
SELECT RP.relTypeCode, RP.relId,
SUM(IF(RP.point > 0, RP.point,0)) AS goodReactionPoint,
SUM(IF(RP.point < 0, RP.point * -1,0)) AS badReactionPoint
FROM reactionPoint AS RP
GROUP BY RP.relTypeCode, RP.relId;

SELECT IFNULL(SUM(RP.point),0)
FROM reactionPoint AS RP
WHERE RP.relTypeCode = 'article'
AND RP.relId = 3
AND RP.memberId = 2;

#--------
UPDATE article
SET goodReactionPoint = 1
WHERE id = 124;
UPDATE article
SET badReactionPoint = 0
WHERE id = 124;

#---------------------------------------------------------------------------------------
# 댓글 테이블 생성
CREATE TABLE reply (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    relTypeCode CHAR(50) NOT NULL COMMENT '관련 데이터 타입 코드',
    relId INT(10) NOT NULL COMMENT '관련 데이터 번호',
    `body`TEXT NOT NULL
);

# 2번 회원이 1번 글에 
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'reply',
relId = 1,
`body` = '댓글 1';

# 2번 회원이 1번 글에 
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'reply',
relId = 1,
`body` = '댓글 2';

# 3번 회원이 1번 글에 
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 3,
relTypeCode = 'reply',
relId = 1,
`body` = '댓글 3';

# 3번 회원이 1번 글에 
INSERT INTO reply
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
relTypeCode = 'reply',
relId = 2,
`body` = '댓글 4';
