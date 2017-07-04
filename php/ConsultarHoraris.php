<?php

include('funcions.php');

$id_clase=$_GET["id_clase"];
$id_dia=$_GET["id_dia"];

$rows = array();
if ($resultset = getSQLResultSet("SELECT assignatura, hora_inici, hora_final, id_clase FROM horario_clase WHERE id_dia = '$id_dia' and id_clase = '$id_clase' ORDER BY hora_inici ")) {
    while ($r = $resultset->fetch_row()) {
        array_push($rows, $r);
    }
}
echo json_encode($rows);
?>
