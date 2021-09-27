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

        final Cursor cursor = db.rawQuery( SELECT_ALL, null );      // 데이터 베이스에 있는 역이름을 리스트에 저장
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

        int count = 0;                                          // 검색을 위해 2가지의 리스트로 복사 & 1호선과 2호선 3호선 4호선 구분 하여 리스트 출력을 위하여 구분 저장
        for (int i = 0; i < metroInfoArrayList.size(); i++) {
            if (metroInfoArrayList.get(i).getType() == type) {
                list.add(count++,metroInfoArrayList.get(i));
            }
        }
        myAdapter = new SubwayAdapter(this, list,2, modify ,subway);
        mRecyclerView.setAdapter( myAdapter );

        db.close();
        m1.setOnClickListener( new View.OnClickListener() { // 1호선 버튼 눌렀을때
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

        m2.setOnClickListener( new View.OnClickListener() { // 2호선 버튼 눌렀을때
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

        m3.setOnClickListener( new View.OnClickListener() { // 3호선 버튼 눌렀을때
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

        m4.setOnClickListener( new View.OnClickListener() { // 4호선 버튼 눌렀을때
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
            public void afterTextChanged(Editable s) { // 문자 입력 받기

                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                search(text);
            }
        } );
    }
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();
        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            int count = 0;
            for (int i = 0; i < metroInfoArrayList.size(); i++) {
                if (metroInfoArrayList.get(i).getType() == type) {
                    list.add(count++,metroInfoArrayList.get(i));
                }
            }
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < metroInfoArrayList.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (metroInfoArrayList.get(i).k_name.toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(metroInfoArrayList.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
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