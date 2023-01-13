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