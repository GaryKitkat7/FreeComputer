<?php

include('funcions.php');

$id_dia = $_GET["id_dia"];

if($resultset=getSQLResultSet("SELECT id_clase, assignatura, hora_inici, hora_final FROM horario_clase WHERE id_dia = '$id_dia' ORDER BY hora_inici;"
   )){
	while ($row = $resultset->fetch_array(MYSQLI_NUM)){
		echo json_encode($row);
	}
}

?>
