<?php

require "../bd.php";

header("Content-type: application/json");

$response = array(
    'exito' => false,
    'mensaje' => 'Peticion fallida.'
);

$post = json_decode(file_get_contents("php://input"), true);

if(empty($post)){
    $response['mensaje'] = 'El id del boleto es requerido.';
    echo json_encode($response);
    return;
}

$boleto_id = $post['boleto_id'];

$call = mysqli_prepare($con, 'CALL usp_boleto_d(?)');
mysqli_stmt_bind_param($call, 'i', $boleto_id);
mysqli_stmt_execute($call);

$response['exito'] = true;
$response['mensaje'] = 'Boleto eliminado correctamente';

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>