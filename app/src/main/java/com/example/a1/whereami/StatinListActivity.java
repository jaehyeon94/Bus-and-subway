package com.example.a1.whereami;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class StatinListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StatinListAdapter adapter;
    TextView searchText;
    EditText stationNameEt;
    Button search_button, search_icon;
    LinearLayout searchbar;
    ArrayList<Station> stations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_metro_station_list);
        LinearLayout backgroundlinear = findViewById(R.id.background);
        search_button = findViewById(R.id.searchbutton);
        stationNameEt = findViewById(R.id.stationName);
        recyclerView = findViewById(R.id.stationList);
        search_icon = findViewById(R.id.search_icon);
        searchbar = findViewById(R.id.searchbarLinear);
        searchText = findViewById(R.id.startsearchtext);

        Drawable alpha = backgroundlinear.getBackground();
        alpha.setAlpha(50);
        setRecyclerView();

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils.loadAnimation(StatinListActivity.this,R.anim.thansmaller);
                anim.setDuration(1000);
                v.startAnimation(anim);

                searchText.setVisibility(View.GONE);
                search_icon.setVisibility(View.GONE);
                searchbar.setVisibility(View.VISIBLE);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stations.clear();
                String stname = stationNameEt.getText().toString();
                StationParsing stationParsing = new StationParsing(stname, adapter);
                stationParsing.searchStation(stations);

            }
        });
    }

    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StatinListAdapter(this,stations);
        Log.e("카운트", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }
}
