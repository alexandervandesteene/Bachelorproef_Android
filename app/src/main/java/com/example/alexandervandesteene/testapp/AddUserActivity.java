package com.example.alexandervandesteene.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by alexandervandesteene on 21/04/16.
 */
public class AddUserActivity extends Activity {

    Button btnAddUser;
    EditText txtFirstname,txtLastname,txtEmail,txtGender,txtIpAddress;
    List<String> listparams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adduser);

        Intent i = getIntent();
        long date1 = i.getLongExtra("date1",0);
        long date2= System.currentTimeMillis();
        int time = (int) (date2-date1);
        System.out.println("@@@@ add user time miliseconds " + time);


        btnAddUser = (Button) findViewById(R.id.btnAddUser);
        txtFirstname = (EditText) findViewById(R.id.txtFirstname);
        txtLastname = (EditText) findViewById(R.id.txtLastname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtGender = (EditText) findViewById(R.id.txtGender);
        txtIpAddress = (EditText) findViewById(R.id.txtIpAddress);
        listparams = new ArrayList<>();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listparams.clear();
                listparams.add(txtFirstname.getText().toString());
                listparams.add(txtLastname.getText().toString());
                listparams.add(txtEmail.getText().toString());
                listparams.add(txtGender.getText().toString());
                listparams.add(txtIpAddress.getText().toString());
                System.out.println(listparams);
                new adduser().execute(listparams);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

    }

    class adduser extends AsyncTask<List<String>, Void, String> {
        long date1= System.currentTimeMillis();

        @Override
        protected String doInBackground(List<String>... params) {

            System.out.println("@@@@@@@@@@@@@@@@@@ " + params[0].toString());

            try {

                URL url = new URL("http://phpapialex.azurewebsites.net/addContact.php");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("first_name", params[0].get(0));
                postDataParams.put("last_name", params[0].get(1));
                postDataParams.put("email", params[0].get(2));
                postDataParams.put("gender", params[0].get(3));
                postDataParams.put("ip_address", params[0].get(4));
                Log.e("params",postDataParams.toString());


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8;application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(postDataParams.toString());

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    long date2= System.currentTimeMillis();
                    int time = (int) (date2-date1);
                    System.out.println("@@@@ add user time miliseconds " + time);

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    System.out.println(sb.toString());

                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "dfq";
        }

        @Override
        protected void onPostExecute(String strings) {
            super.onPostExecute(strings);
            /*Toast.makeText(getApplicationContext(), strings,
                    Toast.LENGTH_LONG).show();*/

        }
    }
}
