package com.example.alexandervandesteene.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListViewAdapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.listUsers);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> ourListViewAkaAdapterView, View itemClickedView, int arrayIdx, long uniqueId) {


              User user =  (User) ourListViewAkaAdapterView.getAdapter().getItem(arrayIdx);
                System.out.println("gevonden "+user.getId());
                String id = user.getId();

                Intent i = new Intent(getApplicationContext(), EditUserActivity.class );
                i.putExtra("id",id);
                startActivity(i);


                /*TextView test = (TextView) itemClickedView.findViewById(R.id.textView1);
                String topic = (String) test.getText();
                System.out.println(topic);
                VoteTopic vote = new VoteTopic();
                vote.execute(topic);
                //populateTopicsList();*/

            }
        });

        new get100Users().execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addUser) {
            long date1= System.currentTimeMillis();
            Intent i = new Intent(getApplicationContext(), AddUserActivity.class );
            i.putExtra("date1",date1);
            startActivity(i);
            return true;
        }else if(id == R.id.showLoc){
            Intent i = new Intent(getApplicationContext(), MapActivity.class );
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    class get100Users extends AsyncTask<Void, Void, List<User>> {

        long date1= System.currentTimeMillis();

        @Override
        protected List<User> doInBackground(Void... params) {

            String urlStr = "http://phpapialex.azurewebsites.net/getContacts.php";
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
                        System.out.println("@@@@ time miliseconds " + time);

                        BufferedReader r = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

                        StringBuffer buffer = new StringBuffer();
                        String line ="";

                        while((line=r.readLine()) !=null){
                            buffer.append(line);
                        }

                        String finalJson = buffer.toString();

                        JSONArray parentArray = new JSONArray(finalJson);

                        List<User> userList = new ArrayList<>();

                        for (int i=0;i<parentArray.length();i++){
                            JSONObject finalobject = parentArray.getJSONObject(i);
                            User user = new User();
                            user.setFirst_name(finalobject.getString("first_name"));
                            user.setLast_name(finalobject.getString("last_name"));
                            user.setEmail(finalobject.getString("email"));
                            user.setGender(finalobject.getString("gender"));
                            user.setIpaddress(finalobject.getString("ip_address"));
                            user.setId(finalobject.getString("id"));
                            userList.add(user);
                        }

                        return userList;

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
        protected void onPostExecute(List<User> results) {
            super.onPostExecute(results);

            ListView listView = (ListView) findViewById(R.id.listUsers);
            listViewAdapter = new ListViewAdapter(MainActivity.this,R.layout.row_layout,results);
            listView.setAdapter(listViewAdapter);
        }
    }
}
