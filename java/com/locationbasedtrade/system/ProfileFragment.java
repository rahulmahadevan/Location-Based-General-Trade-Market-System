package com.example.miniproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    TextView email;
    Button changepassword, changelocation, store,logout,item;
    String emailstr,pass,type,storeid,m_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        email = (TextView) view.findViewById(R.id.email);
        changepassword = (Button) view.findViewById(R.id.changepass);
        changelocation = (Button) view.findViewById(R.id.changeloc);
        store = (Button) view.findViewById(R.id.storebtn);
        logout = (Button) view.findViewById(R.id.logout);
        item = (Button) view.findViewById(R.id.additem);
        if(getArguments()!=null){
            emailstr = getArguments().getString("email");
            pass = getArguments().getString("pass");
            type = getArguments().getString("type");
        }
        if(type!=null){
            if(type.equals("0")){
                item.setVisibility(View.VISIBLE);
                store.setVisibility(View.VISIBLE);
                storeid = getArguments().getString("storeid");
            }
        }
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),StoreForm.class));
            }
        });

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddItem.class);
                intent.putExtra("storeid",storeid);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Are you sure?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                intent.putExtra("password",m_text);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        changelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Location Updated to Current Location",Toast.LENGTH_LONG).show();
            }
        });


        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Enter new password");
                // I'm using fragment here so I'm using getView() to provide ViewGroup
                // but you can provide here any other instance of ViewGroup from your Fragment / Activity
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_inpu_password, (ViewGroup) getView(), false);
                // Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

                // Set up the buttons
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        m_text = input.getText().toString();
                        Toast.makeText(getContext(),"Your Password has been Updated",Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                }
        });

        String temp = email.getText().toString();
        email.setText(temp+emailstr);

        return view;
    }
}
