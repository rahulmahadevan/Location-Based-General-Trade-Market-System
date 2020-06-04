package com.example.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FirstActivity extends AppCompatActivity {
    String name,type,id,latitude,longitude,storeid,email,password;
    boolean check = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getString("type");
        email = bundle.getString("email");
        name = bundle.getString("name");
        id = bundle.getString("id");
        password = bundle.getString("password");
        if(type.equals("1")){
            latitude = bundle.getString("latitude");
            longitude = bundle.getString("longitude");
        }else{
            storeid = bundle.getString("storeid");
        }
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
       //sending type value to fragment which was recieved from login


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new SearchFragment();
                            break;
                        case R.id.nav_profile:
                            Bundle vals = new Bundle();
                            vals.putString("email",email);
                            vals.putString("pass",password);
                            vals.putString("type",type);
                            if(type.equals("0")){
                                vals.putString("storeid",storeid);
                            }
                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(vals);
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

}
