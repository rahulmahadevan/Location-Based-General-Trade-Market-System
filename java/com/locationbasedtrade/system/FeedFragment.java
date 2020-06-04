package com.example.miniproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class FeedFragment extends Fragment {
    ListView recent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed,container,false);
        recent = (ListView) view.findViewById(R.id.pastsearchlist);

        /*Get searched item list from SharedPref
        *parse through the sharedpref obj and add into arraylist
        * covert al into array
        * use arrayadapter to adapt the array to the listview
        * show item name, date of search, no. of results found
        * on click show dialog asking search again, then call search algorithm in SearchFragment
        * on long press ask for deletion of the item, then remove from sharedprefs
        */



        return view;
    }
}
