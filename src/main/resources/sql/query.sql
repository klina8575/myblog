DROP TABLE MEMBER cascade constraints;
DROP SEQUENCE member_seq;

CREATE TABLE member (
    member_id NUMBER PRIMARY KEY,
    email varchar2(255) not null,
    password  varchar2(255) not null,
    name varchar2(255) not null
);

CREATE SEQUENCE member_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999
       NOCYCLE
       NOCACHE
       NOORDER;

insert into member values(member_seq.nextval, 'test1@google.com', '1234', '이지은');
insert into member values(member_seq.nextval, 'test2@google.com', '1234', '정찬성');
insert into member values(member_seq.nextval, 'test3@naver.com', '1234', '하니');

select * from member;


DROP TABLE category cascade constraints;

CREATE TABLE category (
    category_code varchar2(255) PRIMARY KEY,
    category_name varchar2(255) not null
);


insert into category values('001', '여행');
insert into category values('002', '쇼핑');
insert into category values('003', '인테리어');

SELECT * FROM category;



DROP TABLE post cascade constraints;
DROP SEQUENCE post_seq;

CREATE TABLE post (
    post_id NUMBER PRIMARY KEY,
    subject varchar2(255) not null,
    content  clob not null,
    member_id NUMBER not null,
    write_date DATE not NULL,
    update_date DATE,
    views NUMBER not null,
    category_code varchar2(255) NOT NULL,
    CONSTRAINT FK_MEMBER_ID FOREIGN KEY(member_id) REFERENCES member(member_id),
    CONSTRAINT FK_category_code FOREIGN KEY(category_code) REFERENCES category(category_code)
);



CREATE SEQUENCE post_seq
       INCREMENT BY 1
       START WITH 1
       MINVALUE 1
       MAXVALUE 99999
       NOCYCLE
       NOCACHE
       NOORDER;


insert into post values(post_seq.nextval, '제목입니당', '내용입니다.', 1, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '두번째입니다', '내용입니다.', 1, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '세번째입니다', '내용입니다.', 1, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '네번째입니다', '내용입니다.', 1, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '다섯번째입니다', '내용입니다.', 1, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '제목입니당', '내용입니다.', 2, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '두번째입니다', '내용입니다.', 2, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '세번째입니다', '내용입니다.', 2, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '네번째입니다', '내용입니다.', 2, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '다섯번째입니다', '내용입니다.', 2, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '제목입니당', '오늘.', 3, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '두번째입니다', '내일.', 3, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '세번째입니다', '어제.', 3, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '네번째입니다', '모레.', 3, sysdate, NULL, 0, '001');
insert into post values(post_seq.nextval, '다섯번째입니다', '한달뒤.', 3, sysdate, NULL, 0, '001');

SELECT * FROM post;

COMMIT;

--DROP TABLE post_img;
--DROP SEQUENCE post_img_seq;

--CREATE TABLE post_img (
--    post_img_id NUMBER PRIMARY KEY,
--    img_name varchar2(255) not null, --서버에 저장할 이미지명
--    ori_img_name  varchar2(255) not null, --원본 이미지명
--    img_url varchar2(255) not null, --이미지 경로
--    rep_img_yn varchar2(255) not NULL, --대표이미지 여부(첫번째 이미지)
--    post_id NUMBER,
--    CONSTRAINT FK_POST_ID FOREIGN KEY(post_id) REFERENCES post(post_id)
--);
--
--
--CREATE SEQUENCE post_img_seq
--       INCREMENT BY 1
--       START WITH 1
--       MINVALUE 1
--       MAXVALUE 99999
--       NOCYCLE
--       NOCACHE
--       NOORDER;
--
--SELECT * FROM post_img;


select * from (
     select rownum rnum, data.* from (
          SELECT p.post_id, p.subject, p.content, p.MEMBER_id,
				to_char(p.WRITE_date, 'YYYY-MM-DD') WRITE_date, to_char(p.UPDATE_date, 'YYYY-MM-DD') UPDATE_date,
				p.views, c.category_code, c.category_name FROM post p, category c
                WHERE p.category_code = c.category_code
                and subject LIKE '%입%' ORDER BY p.post_id DESC
          ) data
) where rnum >= 1 and rnum <= 2;


select nvl(count(*), 0) from post WHERE subject LIKE '%''%';

select p.post_id, p.subject, p.content, p.member_id, p.write_date, p.update_date, p.views, p.category_code, m.name, m.email
from post p, MEMBER m where p.MEMBER_ID = m.MEMBER_ID AND p.POST_ID = 7;



SELECT * FROM MEMBER WHERE EMAIL = 'test1@google.com' AND PASSWORD = '1234';


SELECT * FROM MEMBER;



--getPostList--


SELECT * FROM (
	SELECT rownum rnum, DATA.* from
	(SELECT p.POST_ID, p.SUBJECT, p.CONTENT, p.MEMBER_ID,
	TO_CHAR(p.WRITE_DATE, 'YYYY-MM-DD') WRITE_DATE,
	TO_CHAR(p.UPDATE_DATE, 'YYYY-MM-DD') UPDATE_DATE,
	p.VIEWS, c.CATEGORY_CODE, c.CATEGORY_NAME
	FROM post p, category c
	WHERE p.CATEGORY_CODE = c.CATEGORY_CODE
	AND p.MEMBER_ID = 1
	AND subject LIKE '%%'
	ORDER BY p.POST_ID DESC) DATA
) WHERE rnum >= 11 AND rnum <= 15;



SELECT NVL(count(*), 0) FROM post
WHERE MEMBER_ID = 1
AND subject LIKE '%%';


SELECT p.POST_ID, p.SUBJECT, p.CONTENT,
p.MEMBER_ID, p.WRITE_DATE, p.UPDATE_DATE, p.VIEWS,
p.CATEGORY_CODE, m.MEMBER_ID, m.NAME, m.EMAIL
FROM post p, MEMBER m
WHERE p.MEMBER_ID = m.MEMBER_ID
AND p.POST_ID = 51;














insert into
post values(post_seq.nextval, '제목입니당', '내용입니다.', 1, sysdate, NULL, 0, '001');


UPDATE post SET SUBJECT = '수정한 제목',
CONTENT = '수정한 내용', UPDATE_DATE =sysdate, CATEGORY_CODE = '002'
WHERE POST_ID = 1;

SELECT * FROM POST;


SELECT * FROM MEMBER;


create user shopmax identified by shopmax1234;
grant connect, resource, dba to shopmax;
COMMIT;

SELECT * FROM HTE_CART;
SELECT * FROM HTE_ORDERS;


DROP USER shopmax CASCADE;












