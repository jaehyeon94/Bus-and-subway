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

import java.util.ArrayList;
import java.util.Locale;

public class SubwayEndActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    private int type, strat_no;
    private  String start_k_name;
    private EditText editText;
    private SubwayAdapter myAdapter;
    private ArrayList<SubwayInfo> metroInfoArrayList, list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subway_end );

        mRecyclerView = findViewById( R.id.recycler_end_view );
        mRecyclerView.setHasFixedSize( true );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );
        Button m1, m2, m3, m4;
        Intent intent = getIntent();

        dataBaseHelper = new DataBaseHelper( this );
        m1 = findViewById( R.id.metro_end1 );
        m2 = findViewById( R.id.metro_end2 );
        m3 = findViewById( R.id.metro_end3 );
        m4 = findViewById( R.id.metro_end4 );

        list = new ArrayList<>();
        metroInfoArrayList = new ArrayList<>();
        type = intent.getExtras().getInt( "Type" );
        final Subway subway = new Subway();
        subway.start_k_name = intent.getExtras().getString("start_k_name");
        subway.start_no = intent.getExtras().getInt( "start_no" );
        final int modify = intent.getExtras().getInt("Modify");

        String SELECT_ALL = "SELECT * FROM " + "metro;";
        db = dataBaseHelper.getWritableDatabase();

        final Cursor cursor = db.rawQuery( SELECT_ALL, null );      // ????????? ???????????? ?????? ???????????? ???????????? ??????
        if (cursor.moveToFirst()) {
            do {
                SubwayInfo subwayInfo = new SubwayInfo();
                subwayInfo.setK_name( cursor.getString( 0 ) );
                subwayInfo.setE_name( cursor.getString( 1 ) );
                subwayInfo.setMetro_no( Integer.parseInt( cursor.getString( 2 ) ) );
                subwayInfo.setType( Integer.parseInt( cursor.getString( 3 ) ) );
                metroInfoArrayList.add( subwayInfo );
            } while (cursor.moveToNext());
        }

        int count = 0;                                          // ????????? ?????? 2????????? ???????????? ?????? & 1????????? 2?????? 3?????? 4?????? ?????? ?????? ????????? ????????? ????????? ?????? ??????
        for (int i = 0; i < metroInfoArrayList.size(); i++) {
            if (metroInfoArrayList.get(i).getType() == type) {
                list.add(count++,metroInfoArrayList.get(i));
            }
        }
        myAdapter = new SubwayAdapter(this, list,2, modify ,subway);
        mRecyclerView.setAdapter( myAdapter );

        db.close();
        m1.setOnClickListener( new View.OnClickListener() { // 1?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayEndActivity.this, SubwayEndActivity.class );
                intent.putExtra( "start_k_name", subway.start_k_name);
                intent.putExtra( "start_no", subway.start_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 1 );
                startActivity( intent );
            }
        } );

        m2.setOnClickListener( new View.OnClickListener() { // 2?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayEndActivity.this, SubwayEndActivity.class );
                intent.putExtra( "start_k_name", subway.start_k_name);
                intent.putExtra( "start_no", subway.start_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 2 );
                startActivity( intent );
            }
        } );

        m3.setOnClickListener( new View.OnClickListener() { // 3?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayEndActivity.this, SubwayEndActivity.class );
                intent.putExtra( "start_k_name", subway.start_k_name);
                intent.putExtra( "start_no", subway.start_no);
                intent.putExtra( "Modify", modify);
                intent.putExtra( "Type", 3 );
                startActivity( intent );
            }
        } );

        m4.setOnClickListener( new View.OnClickListener() { // 4?????? ?????? ????????????
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwayEndActivity.this, SubwayEndActivity.class );
                intent.putExtra( "start_k_name", subway.start_k_name);
                intent.putExtra( "start_no", subway.start_no);
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
            for (int i = 0; i < metroInfoArrayList.size(); i++) {
                if (metroInfoArrayList.get(i).getType() == type) {
                    list.add(count++,metroInfoArrayList.get(i));
                }
            }
        }
        // ?????? ????????? ??????..
        else
        {
            // ???????????? ?????? ???????????? ????????????.
            for(int i = 0;i < metroInfoArrayList.size(); i++)
            {
                // arraylist??? ?????? ???????????? ???????????? ??????(charText)??? ???????????? ????????? true??? ????????????.
                if (metroInfoArrayList.get(i).k_name.toLowerCase().contains(charText))
                {
                    // ????????? ???????????? ???????????? ????????????.
                    list.add(metroInfoArrayList.get(i));
                }
            }
        }
        // ????????? ???????????? ????????????????????? ???????????? ???????????? ????????? ???????????? ????????? ????????????.
        myAdapter.notifyDataSetChanged();
    }
    @Override

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubwayEndActivity.this, SubwayStartActivity.class);
        intent.putExtra( "Type" ,1 );
        startActivity(intent);
    }
}