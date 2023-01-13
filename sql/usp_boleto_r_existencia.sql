DROP PROCEDURE IF EXISTS usp_boleto_r_existencia;

delimiter $$

create procedure usp_boleto_r_existencia(
	in p_usuario_id int,
	out existencia int
)
begin
	select 
		case when count(b.id) > 0
			then 1
			else 0
		end into existencia
	from boletos b
	where
		b.usuario_id = p_usuario_id
		and b.estado in ('activo');
end$$

delimiter ;