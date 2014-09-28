<?php

$db_name="seannguy_taxi123"; // Database name 
$db_username="seannguy_admin"; // Mysql username 
$db_password="admin"; // Mysql password 
$tbl_name="locations"; // Table name 

// Connect to server and select databse.
mysql_connect(localhost, "$db_username", "$db_password")or die(mysql_error()); 
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
$_SESSION['name'] = $name;
$_SESSION['password'] = $password;
header("location:customerArea.html");
}
else {
header("location:loginError.html");
}
?>