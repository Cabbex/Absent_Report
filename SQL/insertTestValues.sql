USE absentReport;

-- Specific Roles -- 
INSERT INTO specificrole (specificrole.role)
VALUE ("Programmer");

INSERT INTO specificrole (specificrole.role)
VALUE ("Designer");

INSERT INTO specificrole (specificrole.role)
VALUE ("Project Leader");

-- Office -- 
INSERT INTO office (office.city_location) 
VALUE ("Karlskrona");

-- Reason -- 

INSERT INTO status (status.status)
VALUE ("Närvarande");

INSERT INTO status (status.status)
VALUE ("Vård av barn");

INSERT INTO status (status.status)
VALUE ("Sjuk");

-- People --
INSERT INTO person (person.username, person.full_name, person.password)
VALUES ("joe","Joe Doh", "test123");

INSERT INTO person (person.username, person.full_name, person.password)
VALUES ("dennis","Dennis Andersson", "test123");

INSERT INTO person (person.username, person.full_name, person.password)
VALUES ("matilda", "Matilda Damsson", "test123");

INSERT INTO person (person.username, person.full_name, person.password)
VALUES ("fransis","Fransis Awaysson", "test123");

INSERT INTO person (person.username, person.full_name, person.password)
VALUES ("felica","Felica Persson", "test123");

-- Manager -- 
INSERT INTO managers (managers.person_id, managers.office_id)
VALUE (3, 1);

-- Employes --

INSERT INTO employes (employes.person_id, employes.specificRole_id, employes.manager_id, employes.office_id, employes.status_id)
VALUE (1,1,1,1,1);

INSERT INTO employes (employes.person_id, employes.specificRole_id, employes.manager_id, employes.office_id, employes.status_id)
VALUE (2,2,1,1,1);

INSERT INTO employes (employes.person_id, employes.specificRole_id, employes.manager_id, employes.office_id, employes.status_id, employes.returnDate)
VALUE (4,2,1,1,2, 20181202);

INSERT INTO employes (employes.person_id, employes.specificRole_id, employes.manager_id, employes.office_id, employes.status_id, employes.returnDate)
VALUE (5,1,1,1,3, 20181120);

SELECT * FROM employes_info;