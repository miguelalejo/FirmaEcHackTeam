create table alumno (
	id varchar(36) not null,
	nombre varchar(255) not null,
	curso varchar(36),
	facultad varchar(36),
	estado boolean,
	primary key (id)
);