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