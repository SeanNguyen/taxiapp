<?php

$host="mysql4.000webhost.com"; // Your server
$db_name="a9556932_taxi"; // Database name 
$db_username="a9556932_sean"; // Mysql username 
$db_password="prince746362"; // Mysql password 
$tbl_name="clients"; // Table name 

// Connect to server and select databse.
mysql_connect("$host", "$db_username", "$db_password")or die(mysql_error()); 
mysql_select_db("$db_name")or die(mysql_error());

// username and password sent from form 
$username=$_POST['username']; 
$password=$_POST['password']; 

// To protect MySQL injection
$username = stripslashes($username);
$password = stripslashes($password);
$username = mysql_real_escape_string($username);
$password = mysql_real_escape_string($password);
$sql="SELECT * FROM $tbl_name WHERE username='$username' and password='$password'";
$result=mysql_query($sql);

// Mysql_num_row is counting table row
$count=mysql_num_rows($result);

// If result matched $username and $password, table row must be 1 row
if($count==1){

// Register $myusername, $mypassword and redirect to file "customer_area.php"
session_register("username");
session_register("password"); 
header("location:customerArea.html");
}
else {
header("location:loginError.html");
}
?>