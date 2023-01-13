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

$call = mysqli_prepare($con, 'CALL usp_boleto_r(?)');
mysqli_stmt_bind_param($call, 'i', $usuario_id);
mysqli_stmt_execute($call);

$result = mysqli_stmt_get_result($call);
$rows = mysqli_fetch_all($result, MYSQLI_ASSOC);

$boletos = array();
$i = 0;

foreach($rows as $row) {
    $boletos[$i]['id'] = $row['id'];
    $boletos[$i]['num_a'] = $row['num_a'];
    $boletos[$i]['num_b'] = $row['num_b'];
    $boletos[$i]['num_c'] = $row['num_c'];
    $boletos[$i]['num_d'] = $row['num_d'];
    $boletos[$i]['num_e'] = $row['num_e'];
    $boletos[$i]['fecha'] = $row['fecha'];
    $boletos[$i]['estado'] = $row['estado'];

    $i++;
}

$response['exito'] = true;
$response['mensaje'] = 'Boletos obtenidos correctamente';
$response['respuesta'] = $boletos;

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>