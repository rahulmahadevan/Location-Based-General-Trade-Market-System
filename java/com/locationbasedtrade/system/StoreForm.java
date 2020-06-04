package com.example.miniproject;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class StoreForm extends AppCompatActivity implements LocationListener {
    EditText storename, opentime,closetime,contact;
    String name,open,close,contactstr;
    double latitude;
    double longitude;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Button done;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_form);
        storename = (EditText) findViewById(R.id.strname);
        opentime = (EditText) findViewById(R.id.open);
        closetime = (EditText) findViewById(R.id.close);
        contact = (EditText) findViewById(R.id.contact);
        done = (Button) findViewById(R.id.done);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    11);
            return;
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    12);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


        opentime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(StoreForm.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                opentime.setText(hourOfDay + ":" + minute);
                            }
                        },currentHour,currentMinute,false);
                timePickerDialog.show();
            }
        });

        closetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                int currentMinute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(StoreForm.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                closetime.setText(hourOfDay + ":" + minute);
                            }
                        },currentHour,currentMinute,false);
                timePickerDialog.show();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Store Details Updated",Toast.LENGTH_LONG).show();
                name = storename.getText().toString().trim();
                open = opentime.getText().toString();
                close = closetime.getText().toString();
                contactstr = contact.getText().toString();



                Criteria criteria = new Criteria();
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);

                String bestprovider = locationManager.getBestProvider(criteria, false);

                if (ActivityCompat.checkSelfPermission(StoreForm.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(StoreForm.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StoreForm.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            11);
                    return;
                }
                if (ContextCompat.checkSelfPermission(StoreForm.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(StoreForm.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            12);
                }
                Location lastknownlocation = locationManager.getLastKnownLocation(bestprovider);

                if (lastknownlocation != null) {
                    longitude = lastknownlocation.getLongitude() * 1e6;
                    latitude = lastknownlocation.getLatitude() * 1e6;
                }
            }
            });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
