<?php

$host = "localhost";
$user = "sebastian";
$pass = "s1stemaS2!";
$db = "examen_reg";

$con = mysqli_connect($host, $user, $pass, $db);

if(!$con) {
    exit(mysqli_connect_error());
}

mysqli_set_charset($con, 'utf8');

?>