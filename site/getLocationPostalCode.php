<?php
//include config
require_once('includes/config.php');
$username = '"'.$_SESSION['username'].'"';
$stmt = $db->query("SELECT postalCode FROM members WHERE username = $username");
$row = $stmt->fetchAll(PDO::FETCH_ASSOC);
echo $row[0][postalCode];
?>