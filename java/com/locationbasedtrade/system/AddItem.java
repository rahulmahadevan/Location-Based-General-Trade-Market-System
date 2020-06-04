package com.example.miniproject;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
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

public class AddItem extends AppCompatActivity {
    Button submit;
    EditText name, type, price;
    String itemname, itemtype, itemprice, storeid;
    boolean availability;
    Switch avail;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        Bundle bundle = this.getIntent().getExtras();
        storeid = bundle.getString("storeid", "0");
        submit = (Button) findViewById(R.id.submititem);
        name = (EditText) findViewById(R.id.iname);
        type = (EditText) findViewById(R.id.type);
        price = (EditText) findViewById(R.id.price);
        avail = (Switch) findViewById(R.id.avail);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemname = name.getText().toString();
                itemprice = price.getText().toString();
                itemtype = type.getText().toString();
                if (avail.getText().toString().equals(avail.getTextOff())) {
                    availability = false;
                } else {
                    availability = true;
                }
                if (itemname.equals("") || itemtype.equals("") || itemprice.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_LONG).show();
                }
                try {
                    if (Integer.parseInt(itemprice) <= 0) {
                        Toast.makeText(getApplicationContext(), "Enter valid MRP", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
                if (!itemname.equals("") && !itemtype.equals("") && !itemprice.equals("") && Integer.parseInt(itemprice) > 0) {
                    new PostAddItem().execute(itemname, itemtype, itemprice, storeid);
                }

            }
        });
    }

    public class PostAddItem extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {


            String name, avail, type, price, storeid;
            name = arg0[0];
            type = arg0[1];
            price = arg0[2];
            storeid = arg0[3];


            HttpResponse response = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI("http://192.168.43.233/phps/additem.php?" + "name='" + name + "'&id='" + storeid + "'&type='" + type + "'&price='" + price + "'")); //edit to include all parameters
                response = client.execute(request);

                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {

                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.v("Response: ", responseStr);
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
            Toast.makeText(getApplicationContext(), "Item Updated in Your Store", Toast.LENGTH_LONG).show();
        }
    }

}