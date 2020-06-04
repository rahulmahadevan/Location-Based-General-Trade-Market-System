package com.example.miniproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment {
    EditText search;
    ListView lv;
    String item;
    ImageButton go;
    ArrayAdapter<String> adapter;
    ArrayList<String> stores;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        search = (EditText) view.findViewById(R.id.searchbar);
        lv = (ListView) view.findViewById(R.id.listview);
        go = (ImageButton) view.findViewById(R.id.go);
        context = getActivity();

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = search.getText().toString().trim();
                Toast.makeText( getContext(),"Searching..." , Toast.LENGTH_SHORT).show();
                if(item.equalsIgnoreCase("maggie")){
                    stores = new ArrayList<String>();
                    stores.add("XYZ General Store");
                    stores.add("ABC Market");
                    stores.add("PQR Shopping Place");
                    adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,stores);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.392495, 78.598085);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                            if(i==1){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.391786, 78.613125);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                            if(i==2){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.393899, 78.596196);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                        }
                    });
                    Toast.makeText(getContext(),"3 Stored found near by",Toast.LENGTH_LONG).show();
                }else if(item.equalsIgnoreCase("paracitmol")){
                    stores = new ArrayList<String>();
                    stores.add("XYZ General Store");
                    stores.add("PQR Shopping Place");
                    adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,stores);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.392495, 78.598085);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                            if(i==1){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.391786, 78.613125);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                        }
                    });
                    Toast.makeText(getContext(),"2 Stored found near by",Toast.LENGTH_LONG).show();
                }else if(item.equalsIgnoreCase("table fan")){
                    stores = new ArrayList<String>();
                    stores.add("PQR Shopping Place");
                    adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,stores);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i==0){
                                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 17.393899, 78.596196);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                context.startActivity(intent);
                            }
                        }
                    });
                    Toast.makeText(getContext(),"1 Store found near by",Toast.LENGTH_LONG).show();
                }else {
                    lv.setAdapter(null);
                    Toast.makeText(getContext(),"No Stored with "+item+" near by",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
