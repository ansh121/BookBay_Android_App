package com.example.bookbayandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;


import static android.content.Context.MODE_PRIVATE;

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String type="";
    BackgroundWorker (Context ctx) {
        context = ctx;
    }

    @Override
    public String doInBackground(String... params) {
        type = params[0];
        String result="";
        String login_url = "http://192.168.43.87/queries.php";//mobile
        //String login_url = "http://192.168.137.1/queries.php";//
        //String login_url = "http://127.0.0.1/queries.php";
        //String login_url = "https://neuropterous-carloa.000webhostapp.com/conn.php";

        try {
            URL url = new URL(login_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            if(type.equals("login")) {
                String username = params[1];
                String password = params[2];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("signup")){
                String username = params[1];
                String email = params[2];
                String name = params[3];
                String houseno = params[4];
                String street = params[5];
                String locality = params[6];
                String postalcode = params[7];
                String landmark = params[8];
                String city = params[9];
                String state = params[10];
                String mobileno = params[11];
                String password = params[12];
                String cnfpassword = params[13];

                if(password.equals(cnfpassword)){
                    String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                            +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                            +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                            +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                            +URLEncoder.encode("houseno","UTF-8")+"="+URLEncoder.encode(houseno,"UTF-8")+"&"
                            +URLEncoder.encode("street","UTF-8")+"="+URLEncoder.encode(street,"UTF-8")+"&"
                            +URLEncoder.encode("locality","UTF-8")+"="+URLEncoder.encode(locality,"UTF-8")+"&"
                            +URLEncoder.encode("postalcode","UTF-8")+"="+URLEncoder.encode(postalcode,"UTF-8")+"&"
                            +URLEncoder.encode("landmark","UTF-8")+"="+URLEncoder.encode(landmark,"UTF-8")+"&"
                            +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"
                            +URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+"&"
                            +URLEncoder.encode("mobileno","UTF-8")+"="+URLEncoder.encode(mobileno,"UTF-8")+"&"
                            +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                    bufferedWriter.write(post_data);
                }
                else {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    httpURLConnection.disconnect();
                    result = "Password did not match";
                    return result;
                }

            }
            else if(type.equals("home")) {
                String username = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("addbook")) {
                SharedPreferences sp1=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                String username=sp1.getString("username", null);

                String isbn = params[1];
                String repaymethod = params[2];
                String availability = params[3];
                String otherspec = params[4];
                String securitymoney = params[5];

                Log.d("myTagavailable", availability);

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(isbn,"UTF-8")+"&"
                        +URLEncoder.encode("repaymethod","UTF-8")+"="+URLEncoder.encode(repaymethod,"UTF-8")+"&"
                        +URLEncoder.encode("availability","UTF-8")+"="+URLEncoder.encode(availability,"UTF-8")+"&"
                        +URLEncoder.encode("otherspec","UTF-8")+"="+URLEncoder.encode(otherspec,"UTF-8")+"&"
                        +URLEncoder.encode("securitymoney","UTF-8")+"="+URLEncoder.encode(securitymoney,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("mybooks")) {
                String username = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("updatebook")) {
                SharedPreferences sp1=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                String username=sp1.getString("username", null);

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"
                        +URLEncoder.encode("repaymethod","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        +URLEncoder.encode("otherspec","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"
                        +URLEncoder.encode("availability","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8")+"&"
                        +URLEncoder.encode("securitymoney","UTF-8")+"="+URLEncoder.encode(params[5],"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("deletebook")) {
                SharedPreferences sp1=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                String username=sp1.getString("username", null);

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("updateaccount")){
                String username = params[1];
                String email = params[2];
                String name = params[3];
                String houseno = params[4];
                String street = params[5];
                String locality = params[6];
                String postalcode = params[7];
                String landmark = params[8];
                String city = params[9];
                String state = params[10];
                String mobileno = params[11];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("houseno","UTF-8")+"="+URLEncoder.encode(houseno,"UTF-8")+"&"
                        +URLEncoder.encode("street","UTF-8")+"="+URLEncoder.encode(street,"UTF-8")+"&"
                        +URLEncoder.encode("locality","UTF-8")+"="+URLEncoder.encode(locality,"UTF-8")+"&"
                        +URLEncoder.encode("postalcode","UTF-8")+"="+URLEncoder.encode(postalcode,"UTF-8")+"&"
                        +URLEncoder.encode("landmark","UTF-8")+"="+URLEncoder.encode(landmark,"UTF-8")+"&"
                        +URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"
                        +URLEncoder.encode("state","UTF-8")+"="+URLEncoder.encode(state,"UTF-8")+"&"
                        +URLEncoder.encode("mobileno","UTF-8")+"="+URLEncoder.encode(mobileno,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("pendingrequests")) {
                String username = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("search")) {
                String query = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("query","UTF-8")+"="+URLEncoder.encode(query,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("bookdetail")) {
                String isbn = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(isbn,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("requestpage")) {
                String isbn = params[1];
                String username = params[2];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(isbn,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("makerequest")) {
                SharedPreferences sp1=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                String username=sp1.getString("username", null);

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(params[2],"UTF-8")+"&"
                        +URLEncoder.encode("borrowduration","UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"
                        +URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("isbn","UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"
                        +URLEncoder.encode("requesteduserid","UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("acceptrequest")) {
                String requestid = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("requestid","UTF-8")+"="+URLEncoder.encode(requestid,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("declinerequest")) {
                String requestid = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("requestid","UTF-8")+"="+URLEncoder.encode(requestid,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("cancelrequest")) {
                String requestid = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("requestid","UTF-8")+"="+URLEncoder.encode(requestid,"UTF-8");
                bufferedWriter.write(post_data);
            }
            else if(type.equals("history")) {
                String username = params[1];

                String post_data = URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                bufferedWriter.write(post_data);
            }

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

            result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            Log.d("myTag", result);
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            if(result.equals("login success")){
                String username = params[1];
                String password = params[2];
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("username",username );
                Ed.putString("password",password);
                Ed.commit();

                Intent accountsIntent = new Intent(context, NavDrawer.class);
                context.startActivity(accountsIntent);
            }
            else if(result.equals("login failure")){
                result = "Invalid Credentials";
                return  result;
            }
            else if(result.equals("signup success")){
                //result = "signup success";
                //return result;
                Intent accountsIntent = new Intent(context, MainActivity.class);
                context.startActivity(accountsIntent);
            }
            else if(result.equals("signup failure")){
                result = "Invalid Data";
                return  result;
            }
            else if(type.equals("home")){
                Log.d("myTag Userdetails", result);
                if(result.equals("user details access failure")){
                    return result;
                }
                String[] details = result.split(":");
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("name",details[1]);
                Ed.putString("email",details[2]);
                Ed.putString("houseno",details[3]);
                Ed.putString("street",details[4]);
                Ed.putString("locality",details[5]);
                Ed.putString("postalcode",details[6]);
                Ed.putString("landmark",details[7]);
                Ed.putString("city",details[8]);
                Ed.putString("state",details[9]);
                Ed.putString("mobileno",details[10]);
                Ed.commit();

                result="not to pop";
                return result;
            }
            else if(type.equals("addbook")){
                return result;
            }
            else if(type.equals("mybooks")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("mybooks",result);
                Ed.commit();
                result="not to pop";
                return result;
            }
            else if(type.equals("updatebook")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("mybooks",result);
                Ed.commit();
                result="Book Updated Successfully";
                return result;

            }
            else if(type.equals("deletebook")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("mybooks",result);
                Ed.commit();
                result="Book Deleted Successfully";
                return result;

            }
            else if(type.equals("updateaccount")){
                Log.d("myTag Userdetailupdated", result);
                if(result.equals("user details access failure")){
                    return result;
                }
                String[] details = result.split(":");
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("name",details[1]);
                Ed.putString("email",details[2]);
                Ed.putString("houseno",details[3]);
                Ed.putString("street",details[4]);
                Ed.putString("locality",details[5]);
                Ed.putString("postalcode",details[6]);
                Ed.putString("landmark",details[7]);
                Ed.putString("city",details[8]);
                Ed.putString("state",details[9]);
                Ed.putString("mobileno",details[10]);
                Ed.commit();

                result="Account Details Updated";
                return result;
            }
            else if(type.equals("pendingrequests")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("pendingrequests",result);
                Ed.commit();
                result="not to pop";
                return result;
            }
            else if(type.equals("search")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("searchresult",result);
                Log.d("myTag search",result);
                Ed.commit();
                result="not to pop";
                return result;
            }
            else if(type.equals("bookdetail")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("bookdetail",result);
                Log.d("myTag bookdetail",result);
                Ed.commit();
                result="not to pop";
                return result;
            }
            else if(type.equals("requestpage")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("requestpage",result);
                Log.d("myTag requestpage",result);
                Ed.commit();
                result="not to pop";
                return result;
            }
            else if(type.equals("makerequest")) {
                return result;
            }
            else if(type.equals("acceptrequest")) {
                return result;
            }
            else if(type.equals("declinerequest")) {
                return result;
            }
            else if(type.equals("cancelrequest")) {
                return result;
            }
            else if(type.equals("history")) {
                SharedPreferences sp=context.getSharedPreferences("userdetails", MODE_PRIVATE);
                SharedPreferences.Editor Ed=sp.edit();
                Ed.putString("history",result);
                Ed.commit();
                result="not to pop";
                return result;
            }

            } catch (MalformedURLException e) {
            e.printStackTrace();
            result="Connection Error";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result="Connection Error";
            return result;
        }

        return null;
    }

    @Override
    public void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        if(type.equals("login")) {
            alertDialog.setTitle("Login Status");
        }
    }

    @Override
    public void onPostExecute(String result) {
        //if(type.equals("login")){
        if(result!=null && !(result.equals("not to pop"))){
            alertDialog.setMessage(result);
            alertDialog.show();
        }

        if(result!=null && result.equals("Book Added Successfully")){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    Intent mybookIntent = new Intent(context, NavDrawer.class);
                    context.startActivity(mybookIntent);
                }
            }, 1000);
        }

        else if(result!=null && (result.equals("Book Deleted Successfully"))){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    Intent MyBooksIntent = new Intent(context, NavDrawer.class);
                    context.startActivity(MyBooksIntent);
                }
            }, 1000);
        }

        else if(result!=null && result.equals("Account Details Updated")){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    alertDialog.dismiss();
                }
            }, 1000);
        }

        else if(result!=null && result.equals("Book requested submitted")){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    alertDialog.dismiss();
                }
            }, 1000);
        }

        else if(result!=null && (result.equals("Request Accepted") || result.equals("Request Declined") || result.equals("Request Canceled"))){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // Actions to do after 5 seconds
                    Fragment fragment = new PendingRequestsFragment();
                    Intent pendingrequestIntent = new Intent(context, NavDrawer.class);
                    context.startActivity(pendingrequestIntent);
                }
            }, 1000);
        }
        //}
    }

    @Override
    public void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}