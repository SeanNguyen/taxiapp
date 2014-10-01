<?php
// configuration
require('includes/config.php'); 

$timeOut = intval($_GET['timeOut']);

$status = 0;
if ($timeOut > 0)
	$status = 1;

$username = $_SESSION['username'];

echo $username;


$timeOut = '"'.$timeOut.'"';
$status = '"'.$status.'"';
$username = '"'.$username.'"';

// query
$sql = "UPDATE members SET status = $status, requestTimeout = $timeOut WHERE username = $username;";
echo $sql;
$q = $db->query($sql);
?>