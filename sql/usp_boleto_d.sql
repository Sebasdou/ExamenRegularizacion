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