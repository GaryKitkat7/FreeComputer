<?php

include('funcions.php');

$hora=$_GET["hora"];
$dia=$_GET["dia"];

$rows = array();
if ($resultset = getSQLResultSet("SELECT id_clase FROM horario_clase WHERE hora_inici <= '$hora' and hora_final >= '$hora' and id_dia = '$dia'")) {
    while ($r = $resultset->fetch_row()) {
        array_push($rows, $r);
    }
}
echo json_encode($rows);
?>
