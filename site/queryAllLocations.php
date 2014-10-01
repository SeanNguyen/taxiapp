<?php
//include config
require_once('includes/config.php');
$stmt = $db->query("SELECT memberID, locationName, address, postalCode, status FROM members");
$row = $stmt->fetchAll(PDO::FETCH_ASSOC);

if (count($row) > 0) {
    $response["locations"] = array();
	foreach ($row as $member) {
		$location = array();
        $location["memberID"] = $member["memberID"];
		$location["locationName"] = $member["locationName"];
		$location["address"] = $member["address"];
		$location["postalCode"] = $member["postalCode"];
        $location["status"] = $member["status"];
		array_push($response["locations"], $location);
	}
    $response["success"] = 1;
} else {
    $response["success"] = 0;
    $response["message"] = "No products found";
}
echo json_encode($response);
?>