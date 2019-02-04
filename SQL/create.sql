USE absentReport;

DROP TABLE if exists Employes;
DROP TABLE if exists Managers;
DROP TABLE if exists SpecificRole;
DROP TABLE if exists Office;
DROP TABLE if exists Status;
DROP TABLE if exists Person;


DROP VIEW if exists manager_info;
DROP VIEW if exists employes_info;

CREATE TABLE Person (
	id int auto_increment PRIMARY KEY,
    username varchar(30) UNIQUE,
	full_name varchar(30),
	password varchar(100)
);

CREATE TABLE Office (
	id int auto_increment PRIMARY KEY,
    city_location varchar(30)
);

CREATE TABLE SpecificRole (
	id int auto_increment PRIMARY KEY,
    role varchar(40)
);

CREATE TABLE Status(
	id int auto_increment PRIMARY KEY,
    status varchar(50)
);

CREATE TABLE Managers (
	id int auto_increment PRIMARY KEY,
	person_id int not NULL,
	office_id int not NULL,
    
    FOREIGN KEY(person_id) references Person(id),
    FOREIGN KEY(office_id) references Office(id)
);


CREATE TABLE Employes (
	id int auto_increment PRIMARY KEY,
	person_id int not NULL,
    status_id int not NULL,
    returnDate date default null,
    specificRole_id int not NULL,
    manager_id int not NULL,
    office_id int not NULL,
    
    FOREIGN KEY(person_id) references Person(id),
    FOREIGN KEY(specificRole_id) references SpecificRole(id),
    FOREIGN KEY(manager_id) references Managers(id),
    FOREIGN KEY(office_id) references Office(id),
    FOREIGN KEY(status_id) references Status(id)

);

CREATE VIEW manager_info as 
SELECT 
	m.id as 'ID',
    p.full_name as 'Full_name',
    o.city_location as 'Office'
FROM
	managers as m
	JOIN
		person AS p ON m.person_id = p.id
	JOIN
        office AS o ON m.office_id = o.id;
	

CREATE VIEW employes_info AS
SELECT 
	e.person_id as 'ID',
    p.username as 'Username',
	p.full_name AS 'Full_name',
	sr.role as 'Role',
    s.status as 'Status',
    e.returnDate as 'Return_date',
    mi.Full_name as 'Manager',
    o.city_location as 'Office'
FROM
	employes as e
    JOIN
		person as p ON e.person_id = p.id
	JOIN
		manager_info as mi ON e.manager_id = mi.ID
	JOIN
		specificRole as sr ON e.specificRole_id = sr.id
	JOIN
		office as o ON e.office_id = o.id
	JOIN
		status as s ON e.status_id = s.id;
