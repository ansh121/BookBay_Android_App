<?php
	require "conn.php";
	$type=$_POST["type"];

	function singlequery($result){
		$row=mysqli_fetch_row($result);
		$string = implode(':', $row);
		return "$string";
	}

	function multiplequery($result){
		$string="";
		$seperate=";";
		while($row=mysqli_fetch_row($result)){
			$temp = implode(':', $row);
			$string = $string.$temp;
			$string = $string.$seperate;
		}
		return "$string";
		
	}


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
	else if($type == "home"){
		$username=$_POST["username"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from user as u, user_phone_number as upn where u.User_ID = '$username' and u.User_ID = upn.User_ID;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo singlequery($result);
		}
		else{
			echo "user details access failure";
		}
	}
	else if($type == "addbook"){
		$username=$_POST["username"];
		$isbn = $_POST["isbn"];
		$repaymethod = $_POST["repaymethod"];
		$availability = $_POST["availability"];
		$otherspec = $_POST["otherspec"];
		$securitymoney = $_POST["securitymoney"];

		$url="https://www.googleapis.com/books/v1/volumes?q=isbn:";
		$url=$url.$isbn;

		$str = file_get_contents($url);	
		$json = json_decode($str, true);

		if($json['totalItems'] == 0){
			echo "Book not available ISBN database";
		}
		else{
			if(array_key_exists("title",$json['items'][0]['volumeInfo'])){
				$bookname = $json['items'][0]['volumeInfo']['title'];
			}
			else{
				$bookname = "";
			}
			if(array_key_exists("publishedDate",$json['items'][0]['volumeInfo'])){
				$year = $json['items'][0]['volumeInfo']['publishedDate'];
			}
			else{
				$year = "";
			}
			if(array_key_exists("authors",$json['items'][0]['volumeInfo'])){
				$author = join(", ",$json['items'][0]['volumeInfo']['authors']);
			}
			else{
				$author = "";
			}
			if(array_key_exists("language",$json['items'][0]['volumeInfo'])){
				$language = $json['items'][0]['volumeInfo']['language'];
			}
			else{
				$language = "";
			}		
			
			//$user_name="u1";
			//$user_pass="123";
			$mysql_qry="insert into book values('$isbn','$bookname','$year','$author','$language');";
			mysqli_query($conn ,$mysql_qry);

			//if($result == FALSE){
			//	echo "insert into book failed";
			//}
			
			$mysql_qry="insert into my_books values('$repaymethod','$availability','$otherspec','$securitymoney','$username','$isbn');";
			$result=mysqli_query($conn ,$mysql_qry);

			if($result == FALSE){
				echo "Book already added!";
			}
			else{
				echo "Book Added Successfully";
			}	
		}	
	}
	else if($type == "mybooks"){
		$username=$_POST["username"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from my_books as mb, book as b where mb.User_ID = '$username' and mb.ISBN = b.ISBN;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo multiplequery($result);
		}
		else{
			echo "No Book Added!";
		}
	}
	else if($type == "updatebook"){
		$username=$_POST["username"];
		$isbn = $_POST["isbn"];
		$repaymethod = $_POST["repaymethod"];
		$availability = $_POST["availability"];
		$otherspec = $_POST["otherspec"];
		$securitymoney = $_POST["securitymoney"];

		$mysql_qry="update my_books set Repayment_Policy='$repaymethod', Availability = '$availability',Other_Specifications = '$otherspec', Security_Money_of_Book = '$securitymoney' where User_ID = '$username' and ISBN = '$isbn';";
		$result=mysqli_query($conn ,$mysql_qry);

		$mysql_qry="select * from my_books as mb, book as b where mb.User_ID = '$username' and mb.ISBN = b.ISBN;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo multiplequery($result);
		}
		else{
			echo "No Book Added!";
		}
	}
	else if($type == "deletebook"){
		$username=$_POST["username"];
		$isbn = $_POST["isbn"];

		$mysql_qry="delete from my_books where User_ID = '$username' and ISBN = '$isbn';";
		$result=mysqli_query($conn ,$mysql_qry);

		$mysql_qry="select * from my_books as mb, book as b where mb.User_ID = '$username' and mb.ISBN = b.ISBN;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo multiplequery($result);
		}
		else{
			echo "No Book Added!";
		}
	}
		
?>