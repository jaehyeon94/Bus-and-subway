package com.example.a1.whereami.SubwaySystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.whereami.R;
import com.example.a1.whereami.StatinListActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class SubwaySttingActivity extends AppCompatActivity {

    private String start_k_name, end_k_name;
    private int start_no, end_no;
    TextView start, end, info;
    LinearLayout start_layout, end_layout;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<SubwayRunInfo> subwayRunInfoArrayList, cypeArrayList , subwayDirection;
    ArrayList<SubwayTimeInfo> subwayTimeInfoArrayList,subwayTimeInfoArrayList1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subway_setting );

        mRecyclerView = findViewById( R.id.recycler_s_serch );
        mRecyclerView.setHasFixedSize( true );
        mLayoutManager = new LinearLayoutManager( this );
        mRecyclerView.setLayoutManager( mLayoutManager );



        start = findViewById( R.id.start_tv );
        end = findViewById( R.id.end_tv );
        start_layout = findViewById( R.id.start_layout );
        end_layout = findViewById( R.id.end_layout );

        Button search = findViewById( R.id.s_but );
        Button change = findViewById( R.id.c_but );
        info = findViewById( R.id.info_text );

        long now = System.currentTimeMillis();
        Date date = new Date( now );
        SimpleDateFormat sdfNow = new SimpleDateFormat( "mmss" );

        final Intent intent = getIntent();
        start_k_name = intent.getExtras().getString( "start_k_name" );
        start_no = intent.getExtras().getInt( "start_no" );
        end_k_name = intent.getExtras().getString( "end_k_name" );
        end_no = intent.getExtras().getInt( "end_no" );

        start.setText( start_k_name );
        end.setText( end_k_name );

        start_layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwaySttingActivity.this, SubwayStartActivity.class );
                intent.putExtra( "end_k_name", end_k_name );
                intent.putExtra( "end_no", end_no );
                intent.putExtra( "Type", 1 );
                intent.putExtra( "Modify", 1 );
                startActivity( intent );
            }
        } );

        end_layout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SubwaySttingActivity.this, SubwayEndActivity.class );
                intent.putExtra( "start_k_name", start_k_name );
                intent.putExtra( "start_no", start_no );
                intent.putExtra( "Type", 1 );
                intent.putExtra( "Modify", 1 );
                startActivity( intent );

            }
        } );

        change.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cype_name;
                int cype_no;
                cype_name = start_k_name;
                start_k_name = end_k_name;
                end_k_name = cype_name;

                cype_no = start_no;
                start_no = end_no;
                end_no = cype_no;

                start.setText(start_k_name);
                end.setText(end_k_name);
            }
        } );

        search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long now = System.currentTimeMillis();
                Date date = new Date( now );
                SimpleDateFormat sdfNow = new SimpleDateFormat( "mmss" );
                StrictMode.enableDefaults();
                Subway subway = new Subway();
                subway.start_k_name = start_k_name;
                subway.start_no = start_no;
                subway.end_k_name = end_k_name;
                subway.end_no = end_no;
                int direction = 0;

                info.setText("운행정보");
                SubwayXmlParsing subwayXmlParsing = new SubwayXmlParsing();
                ArrayList<SubwayRoute> subwayRouteArrayList = new ArrayList<>();
                SubwaySerch subwaySerch = new SubwaySerch();

                subwayRouteArrayList = subwaySerch.serch(start_k_name,end_k_name);



                subwayDirection = new ArrayList<>();
                subwayDirection = subwayXmlParsing.getRunInfoXmlData(start_no);

                //Toast.makeText(getApplicationContext(),subwayDirection.get(0).startSc,Toast.LENGTH_LONG).show();

               for(int i = 0 ; i < subwayDirection.size(); i++) {
                    if(start_no != end_no) {
                        if(subwayDirection.get(i).startSn.equals(subwayRouteArrayList.get(0).station)) {
                            if (subwayDirection.get( i ).endSn.equals( subwayRouteArrayList.get( 1 ).station )) {
                                if (Integer.parseInt( subwayDirection.get( i ).startSc ) < Integer.parseInt( subwayDirection.get( i ).endSc )) {
                                    subwayRouteArrayList.get(0).direction=0;
                                    direction = 0;
                                } else if (Integer.parseInt( subwayDirection.get( i ).startSc ) > Integer.parseInt( subwayDirection.get( i ).endSc )) {
                                    subwayRouteArrayList.get(0).direction=1;
                                    direction = 1;
                                }
                            }
                        }
                    } else if (start_no == end_no) {
                        break;
                    }
                }
                int count = 0;


       /*         subwayDirection = new ArrayList<>();
                subwayDirection = subwayXmlParsing.getRunInfoXmlData(start_no);


               while (count < subwayRouteArrayList.size()) {
                    while (count_1 < subwayDirection.size()) {
                            if (subwayRouteArrayList.get( count + 1 ).station.equals( subwayDirection.get( count_1 ).endSn )) {
                                subwayRouteArrayList.get( count + 1 ).no = Integer.parseInt( subwayDirection.get( count_1 ).endSc );
                            } else if (!subwayRouteArrayList.get( count + 1 ).station.equals( subwayDirection.get( count_1 ).endSn )) {
                                if (subwayDirection.get( count_1 ).startSn.equals( subwayDirection.get( count_1 ).endSn )) {
                                    subwayRouteArrayList.get( count ).transfer = 1;
                                    subwayRouteArrayList.get( count ).no = Integer.parseInt( subwayDirection.get( count_1 ).endSc );
                                    count--;
                                }
                        } count_1++;
                    }count++;
                }
*/

                count = 1;
                while (count < subwayRouteArrayList.size()-1) {
                    if (subwayRouteArrayList.get( count - 1 ).station.equals( "범내골" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "전포" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 229 ;
                        subwayRouteArrayList.get( count ).direction = 1 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "범내골" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "부암" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 229 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "부전" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "전포" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 229 ;
                        subwayRouteArrayList.get( count ).direction = 1 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "부전" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "부암" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 229 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "부암" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "범내골" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 119 ;
                        subwayRouteArrayList.get( count ).direction = 1 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "부암" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "부전" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 119 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "전포" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "범내골" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 119 ;
                        subwayRouteArrayList.get( count ).direction = 1 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "전포" ) && subwayRouteArrayList.get( count ).station.equals( "서면" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "부전" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 119 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;

                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "거제" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "시청" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 123 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "거제" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "교대" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 123 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "시청" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "거제" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 305 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "시청" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "물만골" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 305 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "물만골" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "시청" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 123 ;
                        subwayRouteArrayList.get( count ).direction = 1 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "물만골" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "교대" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 123 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "교대" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "거제" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 305 ;
                        subwayRouteArrayList.get( count ).direction = 0 ;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "교대" ) && subwayRouteArrayList.get( count ).station.equals( "연산" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "물만골" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 305 ;
                        subwayRouteArrayList.get( count ).direction = 1;

                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "구포" ) && subwayRouteArrayList.get( count ).station.equals( "덕척" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "구명" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 223 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "구포" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "수정" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 223 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "구명" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "구포" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 313 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "구명" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "숙등" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 313 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "숙등" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "수정" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 223 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "숙등" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "구명" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 223 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "수정" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "구포" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 313 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "수정" ) && subwayRouteArrayList.get( count ).station.equals( "덕천" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "숙등" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 313 ;
                        subwayRouteArrayList.get( count ).direction = 1;

                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "미남" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "명륜" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 125 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "미남" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "교대" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 125 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "명륜" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "미남" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 402 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "명륜" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "수안" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 402 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "교대" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "미남" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 402 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "교대" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "수안" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 402 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "수안" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "명륜" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 125 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "수안" ) && subwayRouteArrayList.get( count ).station.equals( "동래" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "교대" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 125 ;
                        subwayRouteArrayList.get( count ).direction = 1;

                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "만덕" ) && subwayRouteArrayList.get( count ).station.equals( "미남" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "동래" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 401 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "사직" ) && subwayRouteArrayList.get( count ).station.equals( "미남" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "동래" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 401 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "동래" ) && subwayRouteArrayList.get( count ).station.equals( "미남" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "만덕" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 309 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "동래" ) && subwayRouteArrayList.get( count ).station.equals( "미남" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "사직" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 309 ;
                        subwayRouteArrayList.get( count ).direction = 1;

                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "광안" ) && subwayRouteArrayList.get( count ).station.equals( "수영" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "망미" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 301 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "민락" ) && subwayRouteArrayList.get( count ).station.equals( "수영" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "망미" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 301 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "망미" ) && subwayRouteArrayList.get( count ).station.equals( "수영" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "민락" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 208 ;
                        subwayRouteArrayList.get( count ).direction = 1;
                    } else if (subwayRouteArrayList.get( count - 1 ).station.equals( "망미" ) && subwayRouteArrayList.get( count ).station.equals( "수영" ) && subwayRouteArrayList.get( count + 1 ).station.equals( "광안" )) {
                        subwayRouteArrayList.get( count ).transfer = 1;
                        subwayRouteArrayList.get( count ).no = 208 ;
                        subwayRouteArrayList.get( count ).direction = 0;
                    }
                    count++;
                }

                subwayTimeInfoArrayList = new ArrayList<>();

                String count_day = null;

                long now_1 = System.currentTimeMillis();
                Date today = new Date(now_1);
                SimpleDateFormat s_time , s_day;
                s_time = new SimpleDateFormat("HHmm");
                s_day = new SimpleDateFormat("E");

                if(s_day.equals("토")) {
                    count_day = "2";
                } else if(s_day.equals("일")) {
                    count_day = "3";
                } else {
                    count_day ="1";
                }

                String now_time = s_time.format(today);

                int transfer = 0;
                 int number = 0;
                subwayRouteArrayList.get(0).no = start_no;

                subwayTimeInfoArrayList = new ArrayList<>();
                subwayTimeInfoArrayList = subwayXmlParsing.getTimeInfoXmlData( start_no, direction,5 ,now_time,count_day);



                ArrayList<SubwayTime> subwayTimes = new ArrayList<>(  );


                ArrayList<SubwayTimeInfo> timecype = new ArrayList<>();
                for(int i=0; i< subwayTimeInfoArrayList.size() ; i++) {
                    timecype.add( new SubwayTimeInfo( subwayTimeInfoArrayList.get(i).hour, subwayTimeInfoArrayList.get( i ).time ) );
                }ArrayList<SubwayTimeInfo> subwayTimeInfos;


                 while (number < subwayTimeInfoArrayList.size()) {
                     int num = 0;
                     count = 0;
                     int time_cype = 0;
                     transfer = 0;
                     int t_total_time = 0;
                     SubwayTime subwayTime = new SubwayTime();

                     while (count < subwayRouteArrayList.size()) {
                         if (subwayRouteArrayList.get( count ).transfer != 1) {
                             t_total_time += subwayRouteArrayList.get( count ).time;
                         } else if (subwayRouteArrayList.get( count ).transfer == 1) {
                             t_total_time += subwayRouteArrayList.get( count ).time-20;
                             time_cype = t_total_time / 60;
                             if (t_total_time % 60 > 30) {
                                 time_cype++;
                             }
                             try {
                                 String str = "2018-10-17 " + timecype.get( number ).hour + ":" + timecype.get( number ).time + ":00.123";
                                 Date date1 = transforDate( str );
                                 SimpleDateFormat sdformat = new SimpleDateFormat( "HHmm" );
                                 Calendar cal = Calendar.getInstance();
                                 cal.setTime( date1 );
                                 cal.add( Calendar.MINUTE, time_cype);
                                 String end_time = sdformat.format( cal.getTime() );
                                 cal.add( Calendar.MINUTE, 2);
                                 String end_time1 = sdformat.format( cal.getTime() );

                                 subwayTimeInfos = subwayXmlParsing.getTimeInfoXmlData( subwayRouteArrayList.get( count ).no, subwayRouteArrayList.get( count ).direction, 1, end_time1, count_day );
                                 String t_start = subwayTimeInfos.get( 0 ).hour + subwayTimeInfos.get( 0 ).time;
                                 if (num == 0) {
                                     subwayTime.time = ((Integer.parseInt( t_start ) - Integer.parseInt( end_time )) * 60);
                                } else if(num == 1) {
                                     subwayTime.time1 = ((Integer.parseInt( t_start ) - Integer.parseInt( end_time )) * 60);
                                 }
                             transfer++;
                             subwayRouteArrayList.get( count ).count = time_cype;

                             timecype.get(number).hour = subwayTimeInfos.get( 0 ).hour;
                             timecype.get(number).time = subwayTimeInfos.get( 0 ).time;
                             t_total_time = 0;
                             num++;
                             } catch (ParseException e) {
                                 e.printStackTrace();
                             }
                         }
                         subwayTimes.add( subwayTime );
                         count++;
                     }
                     number++;
                 }
                if(start_no != end_no) {
                    SubwaySerchAdapter myAdapter = new SubwaySerchAdapter( getApplicationContext(), subwayTimeInfoArrayList,subwayRouteArrayList, start_k_name, end_k_name, count_day , start_no, transfer , subwayTimes);
                    mRecyclerView.setAdapter( myAdapter );
                } else {
                    Toast.makeText( getApplicationContext(),"출발역과 도착역이 같습니다.",Toast.LENGTH_LONG).show();
                    info.setText("");
                }
            }
        } );

    }  public Date transforDate(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = sdf.parse(str);
        return date;

    }
}

