<?php

require "../bd.php";

header("Content-type: application/json");

$response = array(
    'exito' => false,
    'mensaje' => 'Peticion fallida.',
    'respuesta' => null
);

$post = json_decode(file_get_contents("php://input"), true);

if(empty($post)){
    $response['mensaje'] = 'El id del usuario es requerido.';
    echo json_encode($response);
    return;
}

$usuario_id = $post['usuario_id'];

$call = mysqli_prepare($con, 'CALL usp_boleto_valida(?, @ganador_id)');
mysqli_stmt_bind_param($call, 'i', $usuario_id);
mysqli_stmt_execute($call);

$select = mysqli_query($con, 'SELECT @ganador_id');
$result = mysqli_fetch_assoc($select);

$ganador_id = $result['@ganador_id'];

$response['exito'] = true;
$response['mensaje'] = 'Peticion procesada correctamente';
$response['respuesta'] = (int) $ganador_id;

if($ganador_id!=null) {
    mysqli_stmt_close($call);

    $call = mysqli_prepare($con, 'CALL usp_boleto_u_ganador(?)');
    mysqli_stmt_bind_param($call, 'i', $ganador_id);
    mysqli_stmt_execute($call);  
} 

mysqli_stmt_close($call);

$call = mysqli_prepare($con, 'CALL usp_boleto_d_ganador(?)');
mysqli_stmt_bind_param($call, 'i', $usuario_id);
mysqli_stmt_execute($call);

mysqli_stmt_close($call);

$call = mysqli_prepare($con, 'CALL usp_boleto_r_ganador(?, @boleto_ganador)');
mysqli_stmt_bind_param($call, 'i', $ganador_id);
mysqli_stmt_execute($call);

$select = mysqli_query($con, 'SELECT @boleto_ganador');
$result = mysqli_fetch_assoc($select);

$response['respuesta'] = (string) $result['@boleto_ganador'];

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>