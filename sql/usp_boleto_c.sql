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