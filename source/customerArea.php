<?php
session_start();
if(!session_is_registered(username)){
header("location:login.php");
}
?>

<html>
<body>
Login Successful
</body>
</html>