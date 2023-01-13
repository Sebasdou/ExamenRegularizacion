drop database if exists examen_reg;
CREATE DATABASE examen_reg;
USE examen_reg;

drop table if exists boletos;
drop table if exists usuarios;

CREATE TABLE usuarios (
	id int primary key auto_increment not null,
	nombre varchar(100) not null,
	contrasena varchar(100) not null
);

CREATE TABLE boletos (
	id int primary key auto_increment not null,
	usuario_id int not null,
	num_a int not null,
	num_b int not null,
	num_c int not null,
	num_d int not null,
	num_e int not null,
	fecha datetime not null,
	estado enum('activo', 'inactivo', 'GANADOR'),
	foreign key (usuario_id) references usuarios(id)
);

DROP PROCEDURE IF EXISTS usp_usuario_valida;

delimiter $$

create procedure usp_usuario_valida(
	in p_nombre varchar(100),
	in p_contrasena varchar(100),
	out usuario_id int
)
begin
	select 
		u.id into usuario_id
	from usuarios u
	where
		u.nombre = p_nombre
		and u.contrasena = p_contrasena;
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_r;

delimiter $$

create procedure usp_boleto_r(
	in p_usuario_id int
)
begin
	select 
		b.id ,
		b.num_a, 
		b.num_b, 
		b.num_c, 
		b.num_d,
		b.num_e,
		b.fecha,
		b.estado 
	from boletos b
	where
		b.usuario_id = p_usuario_id
		and b.estado in ('activo', 'GANADOR');
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_c;

delimiter $$

create procedure usp_boleto_c(
	in p_usuario_id int,
	in p_num_a int,
	in p_num_b int,
	in p_num_c int,
	in p_num_d int,
	in p_num_e int
)
begin
	insert into boletos(
		usuario_id,
		num_a,
		num_b,
		num_c,
		num_d,
		num_e,
		fecha,
		estado
	) values (
		p_usuario_id,
		p_num_a,
	    p_num_b,
	    p_num_c,
	    p_num_d,
		p_num_e,
		now(),
		'activo'
	);
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_u;

delimiter $$

create procedure usp_boleto_u(
	in p_id int,
	in p_num_a int,
	in p_num_b int,
	in p_num_c int,
	in p_num_d int,
	in p_num_e int
)
begin
	update boletos b
	set
		b.num_a = p_num_a,
		b.num_b = p_num_b,
		b.num_c = p_num_c,
		b.num_d = p_num_d,
		b.num_e = p_num_e,
		b.fecha = now()
	where 
		b.id = p_id;
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_d;

delimiter $$

create procedure usp_boleto_d(
	in p_id int
)
begin
	update boletos b
	set
		b.estado = 'inactivo'
	where 
		b.id = p_id;
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_d_ganador;

delimiter $$

create procedure usp_boleto_d_ganador(
	in p_usuario_id int
)
begin
	update boletos b
	set
		b.estado = 'inactivo'
	where 
		b.usuario_id = p_usuario_id
		and b.estado not in ('inactivo', 'GANADOR');
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_valida;

delimiter $$

create procedure usp_boleto_valida(
	in p_usuario_id int,
	out p_ganador_id int
)
begin
	select 
		b.id into p_ganador_id
	from boletos b
	where
		b.usuario_id = p_usuario_id
		and b.estado not in ('inactivo', 'GANADOR')
		and b.num_a = LPAD(FLOOR(RAND() * 6), 1, '0')
		and b.num_b = LPAD(FLOOR(RAND() * 6), 1, '0')
		and b.num_c = LPAD(FLOOR(RAND() * 6), 1, '0')
		and b.num_d = LPAD(FLOOR(RAND() * 6), 1, '0')
		and b.num_e = LPAD(FLOOR(RAND() * 6), 1, '0')
	ORDER BY b.fecha DESC
	LIMIT 1;
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_u_ganador;

delimiter $$

create procedure usp_boleto_u_ganador(
	in p_id int
)
begin
	update boletos b
	set
		b.estado = 'GANADOR'
	where 
		b.id = p_id;
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_r_existencia;

delimiter $$

create procedure usp_boleto_r_existencia(
	in p_usuario_id int,
	out existencia int
)
begin
	select 
		case when count(b.id) > 0
			then 1
			else 0
		end into existencia
	from boletos b
	where
		b.usuario_id = p_usuario_id
		and b.estado in ('activo');
end$$

delimiter ;

DROP PROCEDURE IF EXISTS usp_boleto_r_ganador;

delimiter $$

create procedure usp_boleto_r_ganador(
	in p_ganador_id int,
	out boleto_ganador varchar(15)
)
begin
	select 
		CONCAT(b.num_a, " ", b.num_b, " ", b.num_c, " ", b.num_d, " ", b.num_e) into boleto_ganador 
	from boletos b
	where
		b.id = p_ganador_id;
end$$

delimiter ;



