package com.example.a1.whereami;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DestinationStationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DestinationAdapter adapter;
    ArrayList<LineInfo> lineinfos = new ArrayList<>();
    String carno;
    String lineid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_layout);
        Drawable alpha = findViewById(R.id.destinationlayout).getBackground();
        alpha.setAlpha(50);
        recyclerView = findViewById(R.id.destinationrecycler);
        setRecyclerView();

        Intent intent = getIntent();
        carno = intent.getStringExtra("carno");
        lineid = intent.getStringExtra("lineid");
        setRecyclerView();
       // Toast.makeText( DestinationStationActivity.this,String.valueOf( lineid ),Toast.LENGTH_LONG ).show();

        StationParsing stationParsing = new StationParsing(lineid, adapter);
        stationParsing.searchDestinationStop(lineinfos);
    }

    void setRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DestinationAdapter(this,lineinfos,carno,lineid);
        Log.e("카운트", String.valueOf(adapter.getItemCount()));
        recyclerView.setAdapter(adapter);
    }
}
