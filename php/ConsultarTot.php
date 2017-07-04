<?php
include('funcions.php');

$rows = array();
if ($resultset = getSQLResultSet("SELECT estado FROM pcs WHERE sala in(008,010,011,012,017,018)")) {
    while ($r = $resultset->fetch_row()) {
        array_push($rows, $r[0]);
    }
}
echo json_encode($rows);
?>
