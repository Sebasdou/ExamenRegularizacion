<?php

require "../bd.php";

header("Content-type: application/json");

$response = array(
    'exito' => false,
    'mensaje' => 'Peticion fallida.'
);

$post = json_decode(file_get_contents("php://input"), true);

if(empty($post)){
    $response['mensaje'] = 'Los datos del boleto son requeridos.';
    echo json_encode($response);
    return;
}

$usuario_id = $post['usuario_id'];
$num_a = $post['num_a'];
$num_b = $post['num_b'];
$num_c = $post['num_c'];
$num_d = $post['num_d'];
$num_e = $post['num_e'];

$call = mysqli_prepare($con, 'CALL usp_boleto_c(?,?,?,?,?,?)');
mysqli_stmt_bind_param($call, 'iiiiii', $usuario_id,$num_a,$num_b,$num_c,$num_d,$num_e);
mysqli_stmt_execute($call);

$response['exito'] = true;
$response['mensaje'] = 'Boleto creado correctamente';

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>