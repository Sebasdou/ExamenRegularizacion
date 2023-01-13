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