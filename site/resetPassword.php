<?php require('includes/config.php'); 

//if logged in redirect to members page
if( $user->is_logged_in() ){ header('Location: memberpage.php'); } 

$stmt = $db->prepare('SELECT resetToken, resetComplete FROM members WHERE resetToken = :token');
$stmt->execute(array(':token' => $_GET['key']));
$row = $stmt->fetch(PDO::FETCH_ASSOC);

//if no token from db then kill the page
if(empty($row['resetToken'])){
	$stop = 'Invalid token provided, please use the link provided in the reset email.';
} elseif($row['resetComplete'] == 'Yes') {
	$stop = 'Your password has already been changed!';
}

//if form has been submitted process it
if(isset($_POST['submit'])){

	//basic validation
	if(strlen($_POST['password']) < 3){
		$error[] = 'Password is too short.';
	}

	if(strlen($_POST['passwordConfirm']) < 3){
		$error[] = 'Confirm password is too short.';
	}

	if($_POST['password'] != $_POST['passwordConfirm']){
		$error[] = 'Passwords do not match.';
	}

	//if no errors have been created carry on
	if(!isset($error)){

		//hash the password
		$hashedpassword = $user->password_hash($_POST['password'], PASSWORD_BCRYPT);

		try {

			$stmt = $db->prepare("UPDATE members SET password = :hashedpassword, resetComplete = 'Yes'  WHERE resetToken = :token");
			$stmt->execute(array(
				':hashedpassword' => $hashedpassword,
				':token' => $row['resetToken']
			));

			//redirect to index page
			header('Location: login.php?action=resetAccount');
			exit;

		//else catch the exception and show the error.
		} catch(PDOException $e) {
		    $error[] = $e->getMessage();
		}

	}

}
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
</head>

<body>

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
            <li class="active"><a href="index.php">Home</a></li>
            <li><a href="memberpage.php">Members Area</a></li>
            <li><a href="#">Contact</a></li>
          </ul>
          
          <ul class="nav navbar-nav navbar-right">
          	<li><a href="fonts"><button type="button" class="btn btn-default navbar-btn">Sign in</button></a></li>
          </ul>
        </div><!-- /.navbar-collapse -->
      </div><!-- /.container-fluid -->
    </nav>

    <div class="container">
    
        <div class="row">
    
            <div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
    
    
                <?php if(isset($stop)){
    
                    echo "<p class='bg-danger'>$stop</p>";
    
                } else { ?>
    
                    <form role="form" method="post" action="" autocomplete="off">
                        <h2>Change Password</h2>
                        <hr>
    
                        <?php
                        //check for any errors
                        if(isset($error)){
                            foreach($error as $error){
                                echo '<p class="bg-danger">'.$error.'</p>';
                            }
                        }
    
                        //check the action
                        switch ($_GET['action']) {
                            case 'active':
                                echo "<h2 class='bg-success'>Your account is now active you may now log in.</h2>";
                                break;
                            case 'reset':
                                echo "<h2 class='bg-success'>Please check your inbox for a reset link.</h2>";
                                break;
                        }
                        ?>
    
                        <div class="row">
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="password" id="password" class="form-control input-lg" placeholder="Password" tabindex="1">
                                </div>
                            </div>
                            <div class="col-xs-6 col-sm-6 col-md-6">
                                <div class="form-group">
                                    <input type="password" name="passwordConfirm" id="passwordConfirm" class="form-control input-lg" placeholder="Confirm Password" tabindex="1">
                                </div>
                            </div>
                        </div>
                        
                        <hr>
                        <div class="row">
                            <div class="col-xs-6 col-md-6"><input type="submit" name="submit" value="Change Password" class="btn btn-primary btn-block btn-lg" tabindex="3"></div>
                        </div>
                    </form>
    
                <?php } ?>
            </div>
        </div>
    
    
    </div>

	<!-- Latest compiled and minified JavaScript -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>