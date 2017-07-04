<?php

include('funcions.php');

$fila=$_GET["fila"];
$columna=$_GET["columna"];
$sala=$_GET["sala"];


if($resultset=getSQLResultSet("SELECT * FROM `pcs` WHERE fila='$fila'
	and columna = '$columna' and sala = '$sala'"
   )){
	while ($row = $resultset->fetch_array(MYSQLI_NUM)){
		echo json_encode($row);
	}
}

?>
