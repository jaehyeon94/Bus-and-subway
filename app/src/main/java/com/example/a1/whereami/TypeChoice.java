package com.example.a1.whereami;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.a1.whereami.SubwaySystem.Subway;
import com.example.a1.whereami.SubwaySystem.SubwayStartActivity;


public class TypeChoice extends AppCompatActivity {

    Button bus,metro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_choice);
        bus = findViewById(R.id.bus);
        metro = findViewById(R.id.metro);

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeChoice.this,StatinListActivity.class);
                startActivity(intent);
            }
        });

        metro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypeChoice.this,SubwayStartActivity.class);
                intent.putExtra("Type",1);
                startActivity(intent);
            }
        });

    }
}
