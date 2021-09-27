package com.example.a1.whereami;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BusStopActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BusStopAdapter adapter;
    ArrayList<StopBusVO> stopBusVOs = new ArrayList<>();
    TextView busstopName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_stop_activity_layout);
        Drawable alpha = findViewById(R.id.bus_metro_layout_background).getBackground();
        alpha.setAlpha(50);
        recyclerView = findViewById(R.id.reamin_bus_recycler);
        busstopName = findViewById(R.id.busstopName);
        Intent intent = getIntent();

        String stationid =intent.getExtras().getString("stationId");
        String stationName = intent.getExtras().getString("stationName");

        Log.i("stationId 는:", stationid);
        Log.i("stationName 는 ", stationName);
        busstopName.setText(stationName);
        setRecyclerView();
        StationParsing stationParsing = new StationParsing(stationid,adapter);
        stationParsing.searchStopBus(stopBusVOs);

    }



    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BusStopAdapter(this,stopBusVOs);
        Log.i("아이템갯수 : ", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }
}
