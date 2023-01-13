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