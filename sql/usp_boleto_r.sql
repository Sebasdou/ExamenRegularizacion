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