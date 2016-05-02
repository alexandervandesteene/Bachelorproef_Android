package com.example.alexandervandesteene.testapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by alexandervandesteene on 21/04/16.
 */
public class MapActivity extends Activity  {

    private Button btnloc;
    private TextView txtLat,txtLong;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},50204);
        }

        btnloc = (Button) findViewById(R.id.btnloc);
        txtLat = (TextView) findViewById(R.id.txtLat);
        txtLong = (TextView) findViewById(R.id.txtLong);

        btnloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long date1= System.currentTimeMillis();
                GPSTracker gps = new GPSTracker(getApplicationContext());
                if(gps.canGetLocation()){

                    long date2= System.currentTimeMillis();
                    int time = (int) (date2-date1);
                    System.out.println("@@@@ get location time miliseconds " + time);

                    txtLat.setText(""+gps.getLatitude());
                    txtLong.setText(""+gps.getLongitude());
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 50204: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btnloc.setEnabled(true);

                } else {
                    btnloc.setEnabled(false);
                }
                return;
            }
        }
    }


}
