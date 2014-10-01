<?php
// configuration
require('includes/config.php'); 

// new data
$title = 'PHP Security';
$author = 'Jack Hijack';

$timeOut = intval($_GET['timeOut']);

$status = 0;
if ($timeOut > 0)
	$status = 1;

$username = $_SESSION['username'];

$timeOut = '"' + "$timeOut" + '"';
$status = '"' + "$status" + '"';
$username = '"' + "$username" + '"';

// query
$sql = "UPDATE members SET status = $status, requestTimeout = $timeOut WHERE username = $username;";
$q = $db->query($sql);
?>