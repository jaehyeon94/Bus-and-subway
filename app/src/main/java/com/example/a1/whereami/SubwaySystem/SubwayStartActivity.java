package com.example.a1.whereami.SubwaySystem;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.a1.whereami.DataBaseHelper;
import com.example.a1.whereami.R;
import com.example.a1.whereami.TypeChoice;

import java.util.ArrayList;
import java.util.Locale;

public class SubwayStartActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    private int type;
    private EditText editText;
    private SubwayAdapter myAdapter;
    private ArrayList<SubwayInfo> subwayInfoArrayList, list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subway_start );
        mRecyclerView = findViewById( R.id.recycler_view );
        mRecyclerView.setHasFixedSize( true );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );
        Button m1, m2, m3, m4;
        Intent intent = getIntent();

        final Subway subway = new Subway();
        subway.end_k_name = intent.getExtras().getString("end_k_name");
        subway.end_no = intent.getExtras().getInt( "end_no" );
        type = intent.getExtras().getInt( "Type" );
        final int modify = intent.getExtras().getInt("Modify");

        dataBaseHelper = new DataBaseHelper( this );
        m1 = findViewById( R.id.metro_1 );
        m2 = findViewById( R.id.metro_2 );
        m3 = findViewById( R.id.metro_3 );
        m4 = findViewById( R.id.metro_4 );

        list = new ArrayList<>();
        subwayInfoArrayList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM " + "metro;";
        db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery( SELECT_ALL, null );      // ????????? ???????????? ?????? ???????????? ???????????? ??????

        if (cursor.moveToFirst()) {
            do {
                SubwayInfo subwayInfo = new SubwayInfo();
                subwayInfo.setK_name( cursor.getString( 0 ) );
                subwayInfo.setE_name( cursor.getString( 1 ) );
                subwayInfo.setMetro_no( Integer.parseInt( cursor.getString( 2 ) ) );
                subwayInfo.setType( Integer.parseInt( cursor.getString( 3 ) ) );
                subwayInfoArrayList.add( subwayInfo );
            } while (cursor.moveToNext());
        }

        int count = 0;                                          // ????????? ?????? 2????????? ???????????? ?????? & 1????????? 2?????? 3?????? 4?????? ?????? ?????? ????????? ????????? ????????? ?????? ??????
        for (int i = 0; i < subwayInfoArrayList.size(); i++) {
            if (subwayInfoArrayList.get(i).getType() == type) {
                list.add(count++,subwayInfoArrayList.get(i));
            }
        }
        myAdapter = new SubwayAdapter(this, list, 1, modify, subway);
        mRecyclerView.setAdapter( myAdapter );

        db.close();

        m1.setOnClickListener( new View.OnClickListener() { // 1?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayStartActivity.this, SubwayStartActivity.class );
                intent.putExtra( "end_k_name", subway.end_k_name);
                intent.putExtra( "end_no", subway.end_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 1 );
                startActivity( intent );
            }
        } );

        m2.setOnClickListener( new View.OnClickListener() { // 2?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayStartActivity.this, SubwayStartActivity.class );
                intent.putExtra( "end_k_name", subway.end_k_name);
                intent.putExtra( "end_no", subway.end_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 2 );
                startActivity( intent );
            }
        } );

        m3.setOnClickListener( new View.OnClickListener() { // 3?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayStartActivity.this, SubwayStartActivity.class );
                intent.putExtra( "end_k_name", subway.end_k_name);
                intent.putExtra( "end_no", subway.end_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 3 );
                startActivity( intent );
            }
        } );

        m4.setOnClickListener( new View.OnClickListener() { // 4?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayStartActivity.this, SubwayStartActivity.class );
                intent.putExtra( "end_k_name", subway.end_k_name);
                intent.putExtra( "end_no", subway.end_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 4 );
                startActivity( intent );
            }
        } );

        editText = findViewById(R.id.edit_text_filter);

        editText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) { // ?????? ?????? ??????

                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                search(text);
            }
        } );
    }
    public void search(String charText) {

        // ?????? ??????????????? ???????????? ????????? ?????? ????????????.
        list.clear();
        // ?????? ????????? ???????????? ?????? ???????????? ????????????.
        if (charText.length() == 0) {
            int count = 0;
            for (int i = 0; i < subwayInfoArrayList.size(); i++) {
                if (subwayInfoArrayList.get(i).getType() == type) {
                    list.add(count++,subwayInfoArrayList.get(i));
                }
            }
        }
        // ?????? ????????? ??????..
        else
        {
            // ???????????? ?????? ???????????? ????????????.
            for(int i = 0;i < subwayInfoArrayList.size(); i++)
            {
                // arraylist??? ?????? ???????????? ???????????? ??????(charText)??? ???????????? ????????? true??? ????????????.
                if (subwayInfoArrayList.get(i).k_name.toLowerCase().contains(charText))
                {
                    // ????????? ???????????? ???????????? ????????????.
                    list.add(subwayInfoArrayList.get(i));
                }
            }
        }
        // ????????? ???????????? ????????????????????? ???????????? ???????????? ????????? ???????????? ????????? ????????????.
        myAdapter.notifyDataSetChanged();
    }
    @Override

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubwayStartActivity.this, TypeChoice.class);
        startActivity(intent);
    }
}