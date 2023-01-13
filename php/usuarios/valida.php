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

$nombre = $post['nombre'];
$contrasena = $post['contrasena'];

$call = mysqli_prepare($con, 'CALL usp_usuario_valida(?, ?, @usuario_id)');
mysqli_stmt_bind_param($call, 'ss', $nombre, $contrasena);
mysqli_stmt_execute($call);

$select = mysqli_query($con, 'SELECT @usuario_id');
$result = mysqli_fetch_assoc($select);

$response['exito'] = true;
$response['mensaje'] = 'Peticion procesada correctamente';
$response['respuesta'] = (int) $result['@usuario_id'];

echo json_encode($response);

mysqli_stmt_close($call);
mysqli_close($con);

?>