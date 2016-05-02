package com.example.alexandervandesteene.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by alexandervandesteene on 21/04/16.
 */
public class EditUserActivity extends Activity{

    Button btnRemoveUser,btnEditUser;
    EditText txtEditFirstname,txtEditLastname,txtEditEmail,txtEditGender,txtEditIpAddress,txtEditId;
    List<String> listparams;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edituser);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        System.out.println("id ontvangen " + id );

        new get1Users().execute(id);

        btnEditUser = (Button) findViewById(R.id.btnUpdateUser);
        btnRemoveUser = (Button) findViewById(R.id.btnRemoveUser);
        txtEditFirstname = (EditText) findViewById(R.id.txtEditFirstname);
        txtEditLastname = (EditText) findViewById(R.id.txtEditLastname);
        txtEditEmail = (EditText) findViewById(R.id.txtEditEmail);
        txtEditGender = (EditText) findViewById(R.id.txtEditGender);
        txtEditIpAddress = (EditText) findViewById(R.id.txtEditIpAddress);
        txtEditId = (EditText) findViewById(R.id.txtEditId);
        listparams = new ArrayList<>();

        btnRemoveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listparams.clear();
                new removeuser().execute(txtEditId.getText().toString());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });

        btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listparams.clear();
                listparams.add(txtEditFirstname.getText().toString());
                listparams.add(txtEditLastname.getText().toString());
                listparams.add(txtEditEmail.getText().toString());
                listparams.add(txtEditGender.getText().toString());
                listparams.add(txtEditIpAddress.getText().toString());
                listparams.add(txtEditId.getText().toString());
                System.out.println(listparams);

                new edituser().execute(listparams);

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }

    class edituser extends AsyncTask<List<String>, Void, Void> {

        long date1= System.currentTimeMillis();

        @Override
        protected Void doInBackground(List<String>... params) {

            System.out.println("@@@@@@@@@@@@@@@@@@ " + params[0]);

            try {

                URL url = new URL("http://phpapialex.azurewebsites.net/updateContact.php");

                JSONObject postDataParams = new JSONObject();

                postDataParams.put("first_name", params[0].get(0));
                postDataParams.put("last_name", params[0].get(1));
                postDataParams.put("email", params[0].get(2));
                postDataParams.put("gender", params[0].get(3));
                postDataParams.put("ip_address", params[0].get(4));
                postDataParams.put("id", params[0].get(5));
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
                    System.out.println("@@@@ edit user time miliseconds " + time);

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



                }
                else {

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
            return null;
        }


    }
    class removeuser extends AsyncTask<String, Void, Void> {

        long date1= System.currentTimeMillis();

        @Override
        protected Void doInBackground(String... params) {

            System.out.println("id " + params.toString());

            try {

                URL url = new URL("http://phpapialex.azurewebsites.net/verwijderContact.php");

                JSONObject postDataParams = new JSONObject();

                //// TODO: 21/04/16 id aanpassen
                postDataParams.put("id", params[0]);
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
                    System.out.println("@@@@ remove user time miliseconds " + time);

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



                }
                else {

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

            return null;
        }
    }
    class get1Users extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... params) {

            long date1= System.currentTimeMillis();

            String urlStr = "http://phpapialex.azurewebsites.net/getContact.php?id="+params[0];
            int resCode = -1;


            try {
                URL url = new URL(urlStr);
                URLConnection urlConn = url.openConnection();

                if (!(urlConn instanceof HttpURLConnection)) {
                    throw new IOException("URL is not an Http URL");
                }
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                try{
                    resCode = httpConn.getResponseCode();

                    if (resCode == HttpURLConnection.HTTP_OK) {

                        long date2= System.currentTimeMillis();
                        int time = (int) (date2-date1);
                        System.out.println("@@@@ get 1 user time miliseconds " + time);

                        BufferedReader r = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String line ="";

                        while((line=r.readLine()) !=null){
                            buffer.append(line);
                        }

                        String finalJson = buffer.toString();

                        JSONArray parentArray = new JSONArray(finalJson);

                        List<User> userList = new ArrayList<>();

                        JSONObject finalobject = parentArray.getJSONObject(0);
                        User user = new User();
                        user.setFirst_name(finalobject.getString("first_name"));
                        user.setLast_name(finalobject.getString("last_name"));
                        user.setEmail(finalobject.getString("email"));
                        user.setGender(finalobject.getString("gender"));
                        user.setIpaddress(finalobject.getString("ip_address"));
                        user.setId(finalobject.getString("id"));
                        userList.add(user);

                        return user;

                    } else {
                        System.out.println("----------------------- niet gevonden");
                    }
                }catch (ProtocolException e)
                {
                    String error = "try again";

                    System.out.println("@@@@@@@@@@" + e.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        }
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            txtEditId.setText(user.getId());
            txtEditFirstname.setText(user.getFirst_name());
            txtEditLastname.setText(user.getLast_name());
            txtEditEmail.setText(user.getEmail());
            txtEditGender.setText(user.getGender());
            txtEditIpAddress.setText(user.getIpaddress());

        }
    }
}
