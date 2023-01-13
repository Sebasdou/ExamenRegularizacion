DROP PROCEDURE IF EXISTS usp_boleto_valida;

delimiter $$

create procedure usp_boleto_valida(
	in p_usuario_id int
)
begin

	declare v_num_a int;
	declare v_num_b int;
	declare v_num_c int;
	declare v_num_d int;
	declare v_num_e int;
	
	set v_num_a = LPAD(FLOOR(RAND() * 6), 1, '0');
	set v_num_b = LPAD(FLOOR(RAND() * 6), 1, '0');
	set v_num_c = LPAD(FLOOR(RAND() * 6), 1, '0');
	set v_num_d = LPAD(FLOOR(RAND() * 6), 1, '0');
	set v_num_e = LPAD(FLOOR(RAND() * 6), 1, '0');

	select 
		b.id,
		CONCAT(v_num_a, " ", v_num_b, " ", v_num_c, " ", v_num_d, " ", v_num_e)
	from boletos b
	where
		b.usuario_id = p_usuario_id
		and b.estado not in ('inactivo', 'GANADOR')
		and b.num_a = v_num_a
		and b.num_b = v_num_b
		and b.num_c = v_num_c
		and b.num_d = v_num_d
		and b.num_e = v_num_e
	ORDER BY b.fecha DESC
	LIMIT 1;
end$$

delimiter ;