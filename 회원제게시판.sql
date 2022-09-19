-- 계정 생성 및 권한 부여

-- SYS 관리자 계정
ALTER SESSION SET "_ORACLE_SCRIPT" = TRUE;

-- 사용자 계정 생성
CREATE USER member_cys IDENTIFIED BY member1234;

-- 생성한 사용자 계정에 권한 부여 (DCL)
GRANT CONNECT, RESOURCE, CREATE VIEW TO member_cys;

-- 테이블 스페이스 할당
ALTER USER member_cys DEFAULT
TABLESPACE SYSTEM QUOTA UNLIMITED ON SYSTEM;

---------------------------------------------------------------

-- 회원 테이블
-- 회원 번호, 아이디, 비밀번호, 이름, 성별, 가입일, 탈퇴여부
CREATE TABLE "MEMBER"(
	MEMBER_NO NUMBER PRIMARY KEY,
	MEMBER_ID VARCHAR2(30) NOT NULL,
	MEMBER_PW VARCHAR2(30) NOT NULL,
	MEMBER_NM VARCHAR2(30) NOT NULL,
	MEMBER_GENDER CHAR(1) CHECK(MEMBER_GENDER IN ('M','F') ),
	ENROLL_DATE DATE DEFAULT SYSDATE,
	SECESSION_FL CHAR(1) DEFAULT 'N' CHECK( SECESSION_FL IN ('Y', 'N') )
);

-------------------------------------------------------------
-- 테이블 컬럼에 주석 달기
COMMENT ON COLUMN "MEMBER".MEMBER_NO IS '회원 번호';
COMMENT ON COLUMN "MEMBER".MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN "MEMBER".MEMBER_PW IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER".MEMBER_NM IS '회원 이름';
COMMENT ON COLUMN "MEMBER".MEMBER_GENDER IS '회원 성별(M/F)';
COMMENT ON COLUMN "MEMBER".ENROLL_DATE IS '회원 가입일';
COMMENT ON COLUMN "MEMBER".SECESSION_FL IS '탈퇴여부(Y/N)';

-- 회원 번호 시퀀스 생성
CREATE SEQUENCE  SEQ_MEMBER_NO
START WITH 1
INCREMENT BY 1
NOCYCLE 
NOCACHE;

COMMIT;

-------------------------------------------------------------
-- 회원 가입 구문
INSERT INTO "MEMBER" VALUES( SEQ_MEMBER_NO.NEXTVAL, 'user01', 'pass01', '유저일', 'M', DEFAULT, DEFAULT);
INSERT INTO "MEMBER" VALUES( SEQ_MEMBER_NO.NEXTVAL, 'user02', 'pass02', '유저이', 'F', DEFAULT, DEFAULT);
INSERT INTO "MEMBER" VALUES( SEQ_MEMBER_NO.NEXTVAL, 'user03', 'pass03', '유저삼', 'F', DEFAULT, DEFAULT);

SELECT * FROM MEMBER;

COMMIT;

------------------------------------------------------------
-- 아이디 중복 확인
-- 중복되는 아이디가 입력되어도, 탈퇴한 계정이면 중복 X
SELECT COUNT(*) FROM "MEMBER" WHERE MEMBER_ID = 'user01' AND SECESSION_FL = 'N';
-- 아이디 = user01 이면서 탈퇴하지 않는 회원
SELECT COUNT(*) FROM "MEMBER" WHERE MEMBER_ID = 'user05' AND SECESSION_FL = 'N';

-- 중복이면 COUNT 결과가 1, 중복 아니면 COUNT 결과가 0 조회됨














