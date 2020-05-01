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
		return substr("$string",0,-1);
		
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
	else if($type == "updateaccount"){
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
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="update user set Name = '$name', Email_Address = '$email', House_Number = '$houseno',Street = '$street',Locality = '$locality',Postal_Code = '$postalcode',Landmark = '$landmark',City = '$city', State = '$state' where User_ID = '$username';";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result == FALSE){
			echo "detail update failure";
		}

		$mysql_qry="update user_phone_number set Phone_Number = '$mobileno' where User_ID = '$username';";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result == FALSE){
			echo "phone number update failure";
		}

		$mysql_qry="select * from user as u, user_phone_number as upn where u.User_ID = '$username' and u.User_ID = upn.User_ID;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo singlequery($result);
		}
		else{
			echo "user details access failure";
		}	
	}
	else if($type == "pendingrequests"){
		$username=$_POST["username"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from request as r, book as b, user as u , my_books as mb where r.ISBN = b.ISBN and r.Requested_User_ID = '$username' and u.User_ID = r.User_ID and r.ISBN = mb.ISBN and mb.User_ID = '$username' and r.completion_flag = '0' order by r.Date_of_Request;";
		$result=mysqli_query($conn ,$mysql_qry);
		if(mysqli_num_rows($result) > 0){
			$incoming = multiplequery($result);
		}
		else{
			$incoming = "";
		}

		$mysql_qry="select * from request as r, book as b, user as u, my_books as mb where r.ISBN = b.ISBN and r.User_ID = '$username' and u.User_ID = r.Requested_User_ID and r.ISBN = mb.ISBN and mb.User_ID = r.Requested_User_ID and r.completion_flag = '0' order by r.Date_of_Request;";
		$result=mysqli_query($conn ,$mysql_qry);
		if(mysqli_num_rows($result) > 0){
			$outgoing = multiplequery($result);
		}
		else{
			$outgoing = "";
		}

		$requests = "";


		if($incoming == "") {
			if($outgoing == ""){
				echo "No request found!";
				
			}
			else{
				echo "$outgoing";
			}
		}
		else{
			if($outgoing == ""){
				echo "$incoming";
				
			}
			else{
				$requests = $incoming.";";
				$requests = $requests.$outgoing;
				echo "$requests";
			}
		}
	}
	else if($type == "search"){
		$query=$_POST["query"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from book where Book_Name like '%$query%' or Author like '%$query%' or ISBN like '%$query%';";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo multiplequery($result);
		}
		else{
			echo "No Result!";
		}
	}
	else if($type == "bookdetail"){
		$isbn=$_POST["isbn"];
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="select * from my_books as mb, user as u where mb.User_ID = u.User_ID and mb.ISBN = '$isbn';";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			$users = multiplequery($result);

			$mysql_qry="select * from book where ISBN = '$isbn'";
			$result=mysqli_query($conn ,$mysql_qry);
			$book = singlequery($result);
			
			$book = $book.">";
			$result2 = $book.$users;
			echo "$result2";
			
		}
		else{
			echo "No user with this book available!";
		}
	}
	else if($type == "requestpage"){
		$isbn=$_POST["isbn"];
		$username=$_POST["username"];
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="select * from user as u, book as b, my_books as mb where u.User_ID = '$username' and u.User_ID = mb.User_ID and mb.ISBN = '$isbn' and mb.ISBN = b.ISBN;";
		$result=mysqli_query($conn ,$mysql_qry);

		if(mysqli_num_rows($result) > 0){
			echo singlequery($result);	
		}
		else{
			echo "Error is book access!";
		}
	}
	else if($type == "makerequest"){
		$isbn=$_POST["isbn"];
		$userid=$_POST["userid"];
		$message=$_POST["message"];
		$borrowduration=$_POST["borrowduration"];
		$requesteduserid=$_POST["requesteduserid"];
		$flag="0";
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="insert into request(Date_of_Request,	Request_Message,	borrow_time_duration,	completion_flag,	User_ID,	ISBN,	Requested_User_ID	) values (now(), '$message', '$borrowduration', '$flag', '$userid', '$isbn', '$requesteduserid');";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result){
			echo "Book requested submitted";	
		}
		else{
			echo "Error in requesting";
		}
	}
	else if($type == "acceptrequest"){
		$requestid=$_POST["requestid"];
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="update request set completion_flag = '1' where Request_ID = '$requestid';";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result){
			echo "Request Accepted";	
		}
		else{
			echo "Error in accepting request!";
		}
	}	
	else if($type == "declinerequest"){
		$requestid=$_POST["requestid"];
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="update request set completion_flag = '-1' where Request_ID = '$requestid';";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result){
			echo "Request Declined";	
		}
		else{
			echo "Error in declining request!";
		}
	}
	else if($type == "cancelrequest"){
		$requestid=$_POST["requestid"];
		//$user_name="u1";
		//$user_pass="123";

		$mysql_qry="update request set completion_flag = '-2' where Request_ID = '$requestid';";
		$result=mysqli_query($conn ,$mysql_qry);

		if($result){
			echo "Request Canceled";	
		}
		else{
			echo "Error in canceling request!";
		}
	}	
	else if($type == "history"){
		$username=$_POST["username"];
		//$user_name="u1";
		//$user_pass="123";
		$mysql_qry="select * from request as r, book as b, user as u , my_books as mb, user_phone_number as upn where r.ISBN = b.ISBN and r.Requested_User_ID = '$username' and u.User_ID = r.User_ID and r.ISBN = mb.ISBN and mb.User_ID = '$username' and r.completion_flag <> '0' and upn.User_ID = u.User_ID order by r.Date_of_Request;";
		$result=mysqli_query($conn ,$mysql_qry);
		if(mysqli_num_rows($result) > 0){
			$incoming = multiplequery($result);
		}
		else{
			$incoming = "";
		}

		$mysql_qry="select * from request as r, book as b, user as u, my_books as mb, user_phone_number as upn where r.ISBN = b.ISBN and r.User_ID = '$username' and u.User_ID = r.Requested_User_ID and r.ISBN = mb.ISBN and mb.User_ID = r.Requested_User_ID and r.completion_flag <> '0' and upn.User_ID = u.User_ID order by r.Date_of_Request;";
		$result=mysqli_query($conn ,$mysql_qry);
		if(mysqli_num_rows($result) > 0){
			$outgoing = multiplequery($result);
		}
		else{
			$outgoing = "";
		}

		$requests = "";


		if($incoming == "") {
			if($outgoing == ""){
				echo "No request found!";
				
			}
			else{
				echo "$outgoing";
			}
		}
		else{
			if($outgoing == ""){
				echo "$incoming";
				
			}
			else{
				$requests = $incoming.";";
				$requests = $requests.$outgoing;
				echo "$requests";
			}
		}
	}
?>