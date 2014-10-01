<?php require('includes/config.php'); 

//if not logged in redirect to login page
if(!$user->is_logged_in()){ header('Location: login.php'); } 
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Taxi123</title>
   <!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="css/bootstrap-theme.min.css">
    
    <!-- Optional theme -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
	
    <link rel="stylesheet" href="style/main.css">
    
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    
<script>
	var isTaxiNeeded = false;
	var defaultTimeOut = 300;
	var timeOut = defaultTimeOut;
	var timer;
	var increaseGap = 120;
	var chnangeStatusButton_GreenText = "Start Ordering Taxi";
	var chnangeStatusButton_RedText = "Stop Ordering Taxi";
	
	
	//events
	function onIncreaseButtonClick() {
		if (isTaxiNeeded)
			return
		timeOut += increaseGap;
		updateTimerText();
	}
	
	function onDecreaseButtonClick() {
		if (isTaxiNeeded)
			return
		if (timeOut >= increaseGap) {
			timeOut -= increaseGap;
		} else {
			timeOut = 0;	
		}
		updateTimerText();
	}
	
	function onChageStatusButtonClick() {
		isTaxiNeeded = !isTaxiNeeded;
		updateTimerThread();
		updateButtonStatus();
		updateDatabase();
	}
	
	function tick() {
		if (timeOut < 1) {
			onChageStatusButtonClick();
		} else if (timeOut > 0) {
			timeOut -= 1;
		}
		updateTimerText();
		
		if (timeOut % 10 == 0) {
			updateDatabase();	
		}
	}
	
	
	//helper methods
	function updateTimerThread() {
		if (isTaxiNeeded) {
			if (timer <= 0)
				timer = defaultTimeOut;
			timer = setInterval(function(){tick()}, 1000);
		} else if (!isTaxiNeeded) {
			clearInterval(timer);
		}
	}
	
	function updateButtonStatus() {
		if (isTaxiNeeded) {
			$("#changeStageButton").removeClass("btn-success");
			$("#changeStageButton").addClass("btn-danger");
			document.getElementById("changeStageButton").innerHTML = chnangeStatusButton_RedText;
		} else {
			$("#changeStageButton").removeClass("btn-danger");
			$("#changeStageButton").addClass("btn-success");
			document.getElementById("changeStageButton").innerHTML = chnangeStatusButton_GreenText;
		}
	}
	
	function updateTimerText () {
		var minute = Math.floor(timeOut / 60);
		var second = timeOut % 60;
		if (minute < 10) 
			minute = "0" + minute;
		if (second < 10)
			second = "0" + second;
		document.getElementById("timerText").innerHTML = minute + ":" + second;
	}
	
	function updateDatabase() {
		xmlhttp1=new XMLHttpRequest();
		if (isTaxiNeeded)
		 	xmlhttp1.open("GET","updateLocationStatus.php?timeOut=" + timeOut,true);	
		else 
			xmlhttp1.open("GET","updateLocationStatus.php?timeOut=" + "-1",true);	
	  	xmlhttp1.send();
	}
</script>
</head>

<body>
	<script>
		
		//get location name
		xmlhttp1=new XMLHttpRequest();
		xmlhttp1.onreadystatechange=function() {
			if (xmlhttp1.readyState==4 && xmlhttp1.status==200) {
				document.getElementById("locationName").innerHTML = xmlhttp1.responseText;
			}
		}
	 	xmlhttp1.open("GET","getLocationName.php",true);
	  	xmlhttp1.send();
		
		//get location address
		xmlhttp2=new XMLHttpRequest();
		xmlhttp2.onreadystatechange=function() {
			if (xmlhttp2.readyState==4 && xmlhttp2.status==200) {
				document.getElementById("address").innerHTML = xmlhttp2.responseText;
			}
		}
	 	xmlhttp2.open("GET","getLocationAddress.php",true);
	  	xmlhttp2.send();
		
		//get location postal code
		xmlhttp3=new XMLHttpRequest();
		xmlhttp3.onreadystatechange=function() {
			if (xmlhttp3.readyState==4 && xmlhttp3.status==200) {
				document.getElementById("postalCode").innerHTML = '(' + xmlhttp3.responseText + ')';
			}
		}
	 	xmlhttp3.open("GET","getLocationPostalCode.php",true);
	  	xmlhttp3.send();
		
		//get request time out
		xmlhttp4=new XMLHttpRequest();
		xmlhttp4.onreadystatechange=function() {
			if (xmlhttp4.readyState==4 && xmlhttp4.status==200) {
				var time = parseInt(xmlhttp4.responseText);
				if (time > -1) {
					timeOut = time;
					onChageStatusButtonClick();	
				}
			}
		}
	 	xmlhttp4.open("GET","getLocationRequestTimeout.php",true);
	  	xmlhttp4.send();
	</script>

	<nav class="navbar navbar-default" role="navigation">
      <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.php">Taxi123</a>
        </div>
    
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul class="nav navbar-nav">
            <li><a href="index.php">Home</a></li>
            <li class="active"><a href="memberpage.php">Members Area</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
          	<li><a href="logout.php" class="navbar-link">Sign Out</a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>
    
    <div class="container">
    	
        <address>
        	<font size="+2">
        	<strong id="locationName">Location Name</strong>     <a id="postalCode">000000</a><br>
			<p id="address"></p>
            </font>
        </address>
        
	    <div class="row">
    		<div class="col-lg-5 col-md-5 col-sm-5"><button id="changeStageButton" type="button" class="btn btn-success" 
            											onClick="onChageStatusButtonClick()">Start Ordering Taxi</button></div>
       		<div class="col-lg-5 col-md-5 col-sm-5"><h4 id="timerText">--:--</h4></div>
       		<div class="col-lg-1 col-md-1 col-sm-1"><img src="resources/glyphicons_432_plus.png" 
            											onClick="onIncreaseButtonClick()"/></div>                      
       		<div class="col-lg-1 col-md-1 col-sm-1"><img src="resources/glyphicons_433_minus.png" 
            											onClick="onDecreaseButtonClick()"/></div>        
    	</div>
	    
    </div>
    
    
	<!-- Latest compiled and minified JavaScript -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>