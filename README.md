# BookBay_Android_App
 
 ## Steps to follow:
 * Open BookBayAndroid App in Android Studio
 * Connect your smartphone to your laptop
 * Connect your laptop to your mobile hotspot (Turn on Data)
 * Open BookBay_Android_App\BookBayAndroid\app\src\main\res\xml\network_security_config.xml
 * add your laptop ip address
 * open XAMPP control panel and start MySQL and Apache Server
 * open mysql from cmd using command:mysql -u 'your_username' -p
 * import database schema (book_bay) using command : source <path to schema.sql provided>
 * Open BookBay_Android_App\XAMPP htdocs folder files\conn.php
 * replace username and password with your mysql credentials
 * open htdocs folder in your XAMPP installation directoy
 * copy php files from BookBay_Android_App\XAMPP htdocs folder files to above mentioned directory
 * Run app using Android Studio
