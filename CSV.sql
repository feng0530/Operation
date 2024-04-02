select csv_id, count(*) from csv_detail group by csv_id;

select * from csv_detail where csv_id = 3;

delete from csv_detail;

-- csv_list

select count(*) from csv_list;

select * from csv_list;

delete from csv_list;



-- DROP table tab;
-- CREATE TEMP TABLE tab (id int, name varchar(20), gender char(1), age int);
-- COPY tab FROM 'C:\file2.csv' DELIMITER ',' CSV HEADER;
-- select * from tab;
