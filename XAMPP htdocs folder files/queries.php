<?php
	require "conn.php";
	$type=$_POST["type"];

	if($type == "login"){
		$username=$_POST["username"];
		$password=$_POST["password"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from login_credential where User_ID = '$username' and Password = '$password';";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo "login success";
		}
		else{
			echo "login failure";
		}
	}
	else if($type == "signup"){
		$username=$_POST["username"];
		$email=$_POST["email"];
		$name=$_POST["name"];
		$houseno=$_POST["houseno"];
		$street=$_POST["street"];
		$locality=$_POST["locality"];
		$postalcode=$_POST["postalcode"];
		$landmark=$_POST["landmark"];
		$city=$_POST["city"];
		$state=$_POST["state"];
		$mobileno=$_POST["mobileno"];
		$password=$_POST["password"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="insert into user values('$username', '$name', '$email', '$houseno', '$street', '$locality', '$postalcode', '$landmark', '$city', '$state');";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result == FALSE){
			echo "signup failure";
		}

		$mysql_qry="insert into user_phone_number values('$mobileno', '$username', '1');";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result == FALSE){
			echo "signup failure";
		}

		$mysql_qry="insert into login_credential values('$password', '$username');";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result == TRUE){
			echo "signup success";
		}
		else{
			echo "signup failure";
		}
	}
		
?>