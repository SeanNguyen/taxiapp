<?php
$db_name="seannguy_taxi123"; // Database name 
$db_username="seannguy_admin"; // Mysql username 
$db_password="admin"; // Mysql password 
$tbl_name="locations"; // Table name 

$con = mysqli_connect(localhost,"$db_username","$db_password","$db_name");
// Check connection
if (mysqli_connect_errno()) {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

$result = mysqli_query($con,"SELECT * FROM $tbl_name");

$response = array();
if (mysqli_num_rows($result) > 0) {
    // looping through all results
    // products node
    $response["locations"] = array();
    
    while ($row = mysqli_fetch_array($result)) {
        // temp user array
        $location = array();
        $location["lid"] = $row["lid"];
		$location["locationName"] = $row["locationName"];
		$location["address"] = $row["address"];
		$location["postalCode"] = $row["postalCode"];
        $location["status"] = $row["status"];

        // push single product into final response array
        array_push($response["locations"], $location);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No products found";

    // echo no users JSON
    echo json_encode($response);
}
mysqli_close($con);
?>