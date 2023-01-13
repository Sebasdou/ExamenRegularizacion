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
    $response['mensaje'] = 'Las credenciales son requeridas.';
    echo json_encode($response);
    return;
}

$usuario_id = $post['usuario_id'];

$call = mysqli_prepare($con, 'CALL usp_boleto_r_existencia(?, @existencia)');
mysqli_stmt_bind_param($call, 'i', $usuario_id);
mysqli_stmt_execute($call);

$select = mysqli_query($con, 'SELECT @existencia');
$result = mysqli_fetch_assoc($select);

$response['exito'] = true;
$response['mensaje'] = 'Peticion procesada correctamente';
$response['respuesta'] = (boolean) $result['@existencia'];

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>