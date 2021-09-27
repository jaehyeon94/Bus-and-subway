package com.example.a1.whereami.SubwaySystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a1.whereami.DataBaseHelper;
import com.example.a1.whereami.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class SubwayProcessActivity extends AppCompatActivity {

    TextView s_name, total_time, s_time, e_time, transfer;
    TextView s_info_name, s_info_time;
    Button start_1;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;

    private ArrayList<SubwayInfo> subwayInfoArrayList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subwy_line );

        StrictMode.enableDefaults();
        s_name = findViewById( R.id.s_name );
        s_time = findViewById( R.id.s_time );
        e_time = findViewById( R.id.e_time );
        total_time = findViewById( R.id.total_time );
        s_info_name = findViewById( R.id.s_info_name );
        s_info_time = findViewById( R.id.s_info_time );
        transfer = findViewById( R.id.transfer );
        start_1 = findViewById( R.id.start_1 );

        mRecyclerView = findViewById( R.id.recycler_info_view );
        mRecyclerView.setHasFixedSize( true );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );

        final ArrayList<SubwayRoute> subwayRouteArrayList;

        Intent intent = getIntent();
        String count_day = intent.getExtras().getString("count_day");
        String get_s_name = intent.getExtras().getString( "s_name" );
        String get_e_name = intent.getExtras().getString( "e_name" );
        String get_total_time = intent.getExtras().getString( "total_time" );
        String get_s_time = intent.getExtras().getString( "s_time" );
        String get_e_time = intent.getExtras().getString( "e_time" );
        int get_transfer = intent.getExtras().getInt( "transfer" );
        subwayRouteArrayList = (ArrayList<SubwayRoute>) intent.getExtras().getSerializable( "subway_route" );

        subwayRouteArrayList.get( 0 ).start = get_s_time;
       // Toast.makeText(this,get_e_time,Toast.LENGTH_LONG).show();

        s_name.setText( get_s_name + " -> " + get_e_name );
        s_time.setText( get_s_time );
        e_time.setText( get_e_time );
        total_time.setText( "(걸리는시간 : " + get_total_time );
        s_info_name.setText( get_s_name );
        s_info_time.setText( get_s_time );
        transfer.setText( get_transfer + " 번" );


        ArrayList<SubwayRoute> subwayTansferArrayList = new ArrayList<>();
        SubwayXmlParsing subwayXmlParsing = new SubwayXmlParsing();
        ArrayList<SubwayTimeInfo> subwayTimeInfoArrayList = new ArrayList<>();
        SubwayTime subwayTime1 = new SubwayTime();
        dataBaseHelper = new DataBaseHelper( this );
        subwayInfoArrayList = new ArrayList<>();
        String SELECT_ALL = "SELECT * FROM " + "metro;";
        db = dataBaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery( SELECT_ALL, null );      // 데이터 베이스에 있는 역이름을 리스트에 저장

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

      //  Toast.makeText( this,String.valueOf(subwayRouteArrayList.get(0).direction),Toast.LENGTH_LONG).show();
        int number = 0;
        int count = 1;
        int num =0;
        String ss_time = get_s_time;
        for (int i = 0; i < subwayRouteArrayList.size(); i++) {
            if (subwayRouteArrayList.get( i ).transfer == 1) {
                try {
                    String str = "2018-10-17 " + ss_time + ":00.123";
                    Date date = transforDate( str );
                    SimpleDateFormat sdformat = new SimpleDateFormat( "HHmm" );
                    Calendar cal = Calendar.getInstance();
                    cal.setTime( date );
                    cal.add( Calendar.MINUTE, subwayRouteArrayList.get( i ).count);
                    String end_time = sdformat.format( cal.getTime() );
                    cal.add( Calendar.MINUTE, 2);
                    String end_time1 = sdformat.format( cal.getTime() );
                   // Toast.makeText( SubwayProcessActivity.this,end_time,Toast.LENGTH_LONG ).show();

                    subwayTimeInfoArrayList = subwayXmlParsing.getTimeInfoXmlData( subwayRouteArrayList.get( i ).no, subwayRouteArrayList.get( i ).direction, 1, end_time1, count_day );
                    subwayRouteArrayList.get( i ).start = subwayTimeInfoArrayList.get( 0 ).hour + ":" + subwayTimeInfoArrayList.get( 0 ).time;
                    String cype_time = end_time.substring(0,2) +":" +end_time.substring(2);
                    String time1 = subwayTimeInfoArrayList.get( 0 ).hour + subwayTimeInfoArrayList.get( 0 ).time;
                    if(num == 0) {
                        subwayTime1.time = (Integer.parseInt( time1 ) - Integer.parseInt(end_time)) * 60;
                    } else if (num == 1) {
                        subwayTime1.time1 = (Integer.parseInt( time1 ) - Integer.parseInt(end_time)) * 60;
                    }

                    subwayTansferArrayList.add( new SubwayRoute( subwayRouteArrayList.get( i ).station, subwayRouteArrayList.get( i ).start, cype_time, number ) );
                    num++;
                    number=1;
                    ss_time = subwayRouteArrayList.get( i ).start;
                    } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (subwayRouteArrayList.size() - 1 == i) {
                subwayTansferArrayList.add( new SubwayRoute( subwayRouteArrayList.get( i ).station, "x", get_e_time,number));
            } else {
                number++;
            }
        }
        //Toast.makeText( this, subwayTime.time,Toast.LENGTH_LONG ).show();

        count = 1;
        for (int i = 0; i < subwayRouteArrayList.size(); i++) {
            if (subwayRouteArrayList.get( i ).transfer == 1) {
                if (subwayRouteArrayList.get( i ).no < 200) {
                    if (subwayRouteArrayList.get( i ).direction == 0) {
                        subwayTansferArrayList.get( count).direction_name = "노포방향";
                    } else if (subwayRouteArrayList.get( i ).direction == 1) {
                        subwayTansferArrayList.get( count).direction_name = "신평방향";
                    }
                    subwayTansferArrayList.get( count).direction_no = "1호선";
                } else if (200 < subwayRouteArrayList.get( i ).no && subwayRouteArrayList.get( i ).no < 300) {
                    if (subwayRouteArrayList.get( i ).direction == 0) {
                        subwayTansferArrayList.get( count).direction_name = "양산방향";
                    } else if (subwayRouteArrayList.get( i ).direction == 1) {
                        subwayTansferArrayList.get( count).direction_name = "장산방향";
                    }
                    subwayTansferArrayList.get( count ).direction_no = "2호선";
                } else if (300 < subwayRouteArrayList.get( i ).no && subwayRouteArrayList.get( i ).no < 400) {
                    if (subwayRouteArrayList.get( i ).direction == 0) {
                        subwayTansferArrayList.get( count).direction_name = "대저방향";
                    } else if (subwayRouteArrayList.get( i ).direction == 1) {
                        subwayTansferArrayList.get( count ).direction_name = "수영방향";
                    }
                    subwayTansferArrayList.get( count).direction_no = "3호선";
                } else if (400 < subwayRouteArrayList.get( i ).no) {
                    if (subwayRouteArrayList.get( i ).direction == 0) {
                        subwayTansferArrayList.get( count ).direction_name = "안평방향";
                    } else if (subwayRouteArrayList.get( i ).direction == 1) {
                        subwayTansferArrayList.get( count ).direction_name = "미남방향";
                    }
                    subwayTansferArrayList.get( count ).direction_no = "4호선";
                }
                count++;
            }
        }
        for(int i = 0; i < subwayInfoArrayList.size(); i++) {
            if (subwayInfoArrayList.get( i ).k_name.equals( subwayRouteArrayList.get( 0 ).station )) {
                if (subwayInfoArrayList.get( i ).type == 1) {
                    if (subwayRouteArrayList.get( 0 ).direction == 0) {
                        subwayTansferArrayList.get( 0 ).direction_name = "노포방향";
                    } else if (subwayRouteArrayList.get( 0 ).direction == 1) {
                        subwayTansferArrayList.get( 0 ).direction_name = "신평방향";
                    }
                    subwayTansferArrayList.get( 0 ).direction_no = "1호선";
                } else if (subwayInfoArrayList.get( i ).type == 2) {
                    if (subwayRouteArrayList.get( 0 ).direction == 0) {
                        subwayTansferArrayList.get( 0 ).direction_name = "양산방향";
                    } else if (subwayRouteArrayList.get( 0 ).direction == 1) {
                        subwayTansferArrayList.get( 0 ).direction_name = "장산방향";
                    }
                    subwayTansferArrayList.get( 0 ).direction_no = "2호선";
                } else if (subwayInfoArrayList.get( i ).type == 3) {
                    if (subwayRouteArrayList.get( 0 ).direction == 0) {
                        subwayTansferArrayList.get( 0 ).direction_name = "대저방향";
                    } else if (subwayRouteArrayList.get( 0 ).direction == 1) {
                        subwayTansferArrayList.get( 0 ).direction_name = "수영방향";
                    }
                    subwayTansferArrayList.get( 0 ).direction_no = "3호선";
                } else if (subwayInfoArrayList.get( i ).type == 4) {
                    if (subwayRouteArrayList.get( 0 ).direction == 0) {
                        subwayTansferArrayList.get( 0 ).direction_name = "안평방향";
                    } else if (subwayRouteArrayList.get( 0 ).direction == 1) {
                        subwayTansferArrayList.get( 0 ).direction_name = "미남방향";
                    }
                    subwayTansferArrayList.get( 0 ).direction_no = "4호선";
                }
            }
        }

        final SubwayTime subwayTime = new SubwayTime(subwayTime1.time,subwayTime1.time1);
        SubwayProcessAdapter myAdapter = new SubwayProcessAdapter( this, subwayTansferArrayList);
        mRecyclerView.setAdapter( myAdapter );

        start_1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubwayProcessActivity.this,SwbwayReceiver.class );
                intent.putExtra( "subway_route", subwayRouteArrayList);
                intent.putExtra( "time",subwayTime );
                startActivity(intent);
            }
        } );






    }

        public Date transforDate(String str) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date date = sdf.parse(str);
            return date;

        }

}
