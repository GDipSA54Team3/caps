CREATE TABLE IF NOT EXISTS caps.student(
student_id INT NOT NULL auto_increment,
first_name VARCHAR(45),
last_name VARCHAR(45),
username VARCHAR(45),
password VARCHAR(45),
primary key (student_id));

CREATE TABLE IF NOT EXISTS caps.lecturer(
lecturer_id INT NOT NULL auto_increment,
first_name VARCHAR(45),
last_name VARCHAR(45),
username VARCHAR(45),
password VARCHAR(45),
primary key (lecturer_id));

