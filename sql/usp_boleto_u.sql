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