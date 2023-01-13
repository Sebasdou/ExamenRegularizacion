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