package com.example.mysqltest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class Background extends AsyncTask<String, Void, String> {

    Context context;

//    public Boolean login = false;

    public Background(Context context) {
        this.context = context;
    }


    @Override
    protected void onPostExecute(String s) {

        System.out.println(s);
        if (s.contains("Authorized")) {
            Intent proceedToDashboard = new Intent();
            proceedToDashboard.setClass(context.getApplicationContext(), SecondActivity.class);
            context.startActivity(proceedToDashboard);
            Toast.makeText(context, "Authorization Granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Incorrect Username or Password", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(String... voids) {
        System.out.print("Execution Started");
        String result = "";
        String user = voids[0];
        String pass = voids[1];

//        String connstr = "http://yourIPhere:8080/login.php";
        String connstr = "http://192.168.18.17:80/Practice/AndroidMySQL/androidTest.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8")
                    + "&&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
            result = e.getMessage();
        } catch (IOException e) {
            result = e.getMessage();
        }

        return result;
    }
}

