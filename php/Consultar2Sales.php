<?php

include('funcions.php');

$sala1=$_GET["sala1"];
$sala2=$_GET["sala2"];

$rows = array();
if ($resultset = getSQLResultSet("SELECT estado FROM pcs WHERE sala in ('$sala1','$sala2') and fila!=0 ORDER BY sala ASC")) {
    while ($r = $resultset->fetch_row()) {
        array_push($rows, $r);
    }
}
echo json_encode($rows);
?>
