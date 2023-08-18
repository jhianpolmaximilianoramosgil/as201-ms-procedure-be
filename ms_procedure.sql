drop table procedure;

-- CREAR TABLA procedure
CREATE TABLE procedure (
    id SERIAL PRIMARY KEY NOT NULL,
	unique_identifier INTEGER NOT NULL,
	procedure_config_id INTEGER NOT NULL,
	phase_id INTEGER NOT NULL,
	person_id INTEGER NOT NULL,
	student_id INTEGER NOT NULL,
	institute_id INTEGER NOT NULL,
	link  VARCHAR(100),
	batch INTEGER
);

INSERT INTO procedure
(unique_identifier,procedure_config_id,phase_id,person_id,student_id,institute_id,link,batch)
VALUES
(1,1,9,1,1,1,null,1);

select * from procedure;