package com.example.miniproject;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class ParticularSignup extends AppCompatActivity implements LocationListener {
    Bundle bundle;
    RadioGroup choice;
    Button next;
    int id;
    String type;
    RadioButton selectedbutton;
    double latitude;
    double longitude;
    protected LocationManager locationManager;
    protected LocationListener locationListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_particular);
        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        bundle = this.getIntent().getExtras();
        final String name = bundle.getString("name");
        final String email = bundle.getString("email");
        final String password = bundle.getString("password");
        choice = (RadioGroup) findViewById(R.id.choice);
        next = (Button) findViewById(R.id.next);
        Context context = getApplicationContext();

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


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*
                Get max value of customer id from customer table and store the value in an int var
                increment the var to get current customer id as id
                send type = 0 for trader and type = 1 for customer to generateid.php to receive last unique id
                increment the id to generate new id for user
                 */
                Criteria criteria = new Criteria();
                criteria.setPowerRequirement(Criteria.POWER_LOW);
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);

                String bestprovider = locationManager.getBestProvider(criteria, false);

                if (ActivityCompat.checkSelfPermission(ParticularSignup.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ParticularSignup.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ParticularSignup.this,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            11);
                    return;
                }
                if (ContextCompat.checkSelfPermission(ParticularSignup.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ParticularSignup.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            12);
                }
                Location lastknownlocation = locationManager.getLastKnownLocation(bestprovider);

                if(lastknownlocation!=null){
                    longitude=lastknownlocation.getLongitude()*1e6;
                    latitude =lastknownlocation.getLatitude()*1e6;
                }

                boolean flag = true;
                int selectedid = choice.getCheckedRadioButtonId();
                selectedbutton = (RadioButton) findViewById(selectedid);
                String typestr = selectedbutton.getText().toString();
                if (typestr.equals("Customer")) {
                    type = "1";
                } else {
                    type = "0";
                }
                String res="0";
                try {
                    res = new GenerateId().execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                id = Integer.parseInt(res);
                id++;
                LocationManager lm = (LocationManager) getApplication().getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled = false;
                boolean network_enabled = false;


              Log.v("latitude:"+latitude,"longitude:"+longitude);
                if (flag) {
                    if (type.equals("1")) {
                        if (!(latitude==0.0) && !(longitude==0.0)) {
                            //send name,id,password,email,latitude,longitude and type=1;
                            //Call signupUser with customer parameters only after intializing location variables
                            Log.v("Name",name);
                            Log.v("id",Integer.toString(id));
                            Log.v("Password",password);
                            Log.v("email",email);
                            Log.v("latitude",Double.toString(latitude));
                            Log.v("longitude",Double.toString(longitude));
                            new SignupUser().execute(name,Integer.toString(id),password,email,Double.toString(latitude),Double.toString(longitude),type);
                          //  Intent stact = new Intent(getApplicationContext(), FirstActivity.class);
                           // stact.putExtra("flag", 1);
                            //put extras (all details) + a flag indicating the user is a customer
                        } else {
                            Toast.makeText(getApplicationContext(), "Location error, try again later", Toast.LENGTH_LONG).show();
                        }

                     } else {
                        if (!(latitude==0.0) && !(longitude==0.0)) {
                            //get store id of last store and increment to get current store id
                            //get trader id of last trader and increment to get current trader id
                            //insert into trader name,id,store id,email, password;
                            //intent to store registration form and putextra(store id)
                            //think if details like id should be stored in phone memory or get from db
                            Log.v("Name",name);
                            Log.v("id",Integer.toString(id));
                            Log.v("Password",password);
                            Log.v("email",email);
                            Log.v("Storeid",Integer.toString(id));
                            new TraderSignupUser().execute(name,Integer.toString(id),password,email,Integer.toString(id),type);

                            //put extras (all details) + a flag indicating the user is a trader
                        } else {
                            Toast.makeText(getApplicationContext(), "Location error, try again later", Toast.LENGTH_LONG).show();
                        }

                    }
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

    public class GenerateId extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://192.168.43.233/phps/generateid.php?type='" + type + "'"));
                response = client.execute(request);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v( "Response: " , responseStr);
                    return responseStr;// you can add an if statement here and do other actions based on the response
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.v("Response:", result);
            if(result.equals(null)){
                id=1;
            }else{
                try {
                    int lastid = Integer.parseInt(result);
                    id = ++lastid;
                }catch(NumberFormatException e){
                    id = 1;
                }
            }

            Log.v("Generated id:", Integer.toString(id));
        }

    }

    public class SignupUser extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            String name,id,pass,email,latitude1,longitude1,typeflag;
            name = arg0[0];
            id = arg0[1];
            pass = arg0[2];
            email = arg0[3];
            latitude1 = arg0[4];
            longitude1 = arg0[5];
            typeflag = arg0[6];

            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://192.168.43.233/phps/signup.php?type='" + typeflag + "'&name='"+name+"'&id='"+id+"'&password='"+pass+"'&email='"+email+"'&latitude='"+latitude1+"'&longitude='"+longitude1+"'")); //edit to include all parameters
                response = client.execute(request);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v( "Response: " , responseStr);
                    return responseStr;// you can add an if statement here and do other actions based on the response
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.v("Response:", result);
            if(result.equals("success")){
                Toast.makeText(getApplicationContext(),"Welcome...",Toast.LENGTH_SHORT).show();
            }else if(result.equals("failed")){
                Toast.makeText(getApplicationContext(),"DB error",Toast.LENGTH_SHORT).show();
            }else if(result.equals("Exists")){
                Toast.makeText(getApplicationContext(),"Account already exists! Please Login",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Invalid Server Response", Toast.LENGTH_LONG).show();
            }
        }

    }

    public class TraderSignupUser extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            String name,id,pass,email,storeid,typeflag;
            name = arg0[0];
            id = arg0[1];
            pass = arg0[2];
            email = arg0[3];
            storeid = arg0[4];
            typeflag = arg0[5];

            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://192.168.43.233/phps/tradersignup.php?type='" + typeflag + "'&name='"+name+"'&id='"+id+"'&password='"+pass+"'&email='"+email+"'&storeid='"+storeid+"'")); //edit to include all parameters
                response = client.execute(request);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v( "Response: " , responseStr);
                    return responseStr;// you can add an if statement here and do other actions based on the response
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.v("Response:", result);
            if(result.equals("success")){
                Toast.makeText(getApplicationContext(),"Welcome...",Toast.LENGTH_SHORT).show();
            }else if(result.equals("failed")){
                Toast.makeText(getApplicationContext(),"DB error",Toast.LENGTH_SHORT).show();
            }else if(result.equals("Exists")){
                Toast.makeText(getApplicationContext(),"Account already exists! Please Login",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),"Invalid Server Response", Toast.LENGTH_LONG).show();
            }
        }

    }

}
