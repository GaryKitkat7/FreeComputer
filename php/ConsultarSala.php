<?php

include('funcions.php');

$sala=$_GET["sala"];

$rows = array();
if ($resultset = getSQLResultSet("SELECT estado FROM pcs WHERE sala = '$sala'")) {
    while ($r = $resultset->fetch_row()) {
        array_push($rows, $r);
    }
}
echo json_encode($rows);
?>
