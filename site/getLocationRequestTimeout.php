<?php
//include config
require_once('includes/config.php');
$username = '"'.$_SESSION['username'].'"';
$stmt = $db->query("SELECT requestTimeout, status FROM members WHERE username = $username");
$row = $stmt->fetchAll(PDO::FETCH_ASSOC);

$status = $row[0][status];
if ($status == 1)
	echo $row[0][requestTimeout];
else 
	echo '-1';
?>