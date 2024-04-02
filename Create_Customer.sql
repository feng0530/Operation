CREATE TABLE customer(
	id			SERIAL PRIMARY KEY,
	name		VARCHAR(10)	NOT NULL,
	phone		VARCHAR(12),
	location	VARCHAR(20)
)

DROP TABLE customer;


CREATE TABLE users(
	id			SERIAL PRIMARY KEY,
	email		VARCHAR(50)	NOT NULL,
	mima		VARCHAR(256) NOT NULL,
	role_name	VARCHAR(10) NOT NULL,
	create_time timestamp
)

DROP TABLE users;


CREATE TABLE csv_list(
	id			SERIAL PRIMARY KEY,
	file_name	VARCHAR(100) NOT NULL,
	upload_time	TIMESTAMP NOT NULL,
	update_time	TIMESTAMP NOT NULL
)

DROP TABLE csv_list;


CREATE TABLE csv_detail(
	id			SERIAL PRIMARY KEY,
	csv_id		INT NOT NULL,
	name		VARCHAR(30) NOT NULL,
	gender 		CHAR(1) NOT NULL,
	age			INT NOT NULL
	,
	FOREIGN KEY (csv_id) REFERENCES csv_list(id)
)

DROP TABLE csv_detail;






-- 和欄位做綁定，該欄位被刪除時同時刪除序列
ALTER SEQUENCE table_id OWNED BY customer.id;

ALTER SEQUENCE table_id RESTART WITH 1;-- 重置序列當前的值
DROP SEQUENCE table_id;-- 刪除序列