package com.example.a1.whereami.SubwaySystem;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1.whereami.R;

import java.util.ArrayList;

public class SwbwayReceiver extends AppCompatActivity {

    Thread thread;
    BroadcastReceiver mReceiver;
    TextView now_station, next_station, count_no;
    ArrayList<SubwayRoute> subwayRouteArrayList;
    SubwayRoute subwayRoute,subwayRoute_next;
    SubwayTime subwayTime;
    private static final String INTENT_ACTION = "arabiannight.tistory.com.alarmmanager";
    int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subway_ongoing );
        Intent intent = getIntent();

        subwayRouteArrayList = (ArrayList<SubwayRoute>) intent.getExtras().getSerializable( "subway_route" );
        subwayTime = (SubwayTime) intent.getExtras().getSerializable( "time" );


       // Toast.makeText(SwbwayReceiver.this, String.valueOf(subwayTime.time), Toast.LENGTH_LONG ).show();

        now_station = findViewById( R.id.now_station );
        next_station = findViewById( R.id.next_station );
        count_no = findViewById( R.id.count_no );
        final Vibrator vibrator = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );

       // Toast.makeText( this, subwayTime.time,Toast.LENGTH_LONG ).show();

        Intent serviceIntent = new Intent( SwbwayReceiver.this, SubWayService.class );
        serviceIntent.putExtra( "subway_route", subwayRouteArrayList );
        serviceIntent.putExtra( "subwayTime",subwayTime );
        startService( serviceIntent );

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( "com.example.a1.whereami.SubwaySystem" );



        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                subwayRoute = (SubwayRoute) intent.getExtras().getSerializable( "subway_route" );
                subwayRoute_next = (SubwayRoute) intent.getExtras().getSerializable( "subway_route_next" );
                count = intent.getExtras().getInt( "count" );
                if (subwayRoute.station.equals( subwayRoute_next.station ) && count != 10000) {
                    now_station.setText( "아직 열차가 도착하지 않았습니다." );
                    next_station.setText( subwayRoute_next.station );
                    count_no.setText( "목적지 까지 " + count + "정거장전" );
                } else if (subwayRoute.station.equals( subwayRoute_next.station ) && count == 10000) {
                    now_station.setText( "이미 열차가 출발 하였습니다." );
                    next_station.setText( "" );
                    count_no.setText( "" );
                } else if(count == 500) {
                    vibrator.vibrate( 5000 );
                    Toast.makeText(context,"다음역은 "+ subwayRoute_next.station +"역입니다.", Toast.LENGTH_LONG).show();
                } else if(count == 1000) {
                    now_station.setText( subwayRoute.station+"(환승중)" );
                    next_station.setText( subwayRoute_next.station);
                } else if(count == 0) {
                    now_station.setText( subwayRoute_next.station +"(도착)");
                    next_station.setText( "" );
                    count_no.setText( "목적지에 도착하였습니다." );
                    Toast.makeText(context,"목적지에 도착하였습니다.", Toast.LENGTH_LONG).show();
                }
                else {
                    now_station.setText( subwayRoute.station );
                    next_station.setText( subwayRoute_next.station );
                    count_no.setText( "목적지 까지 " + count + "정거장전" );
                    }
                }
            };
        registerReceiver( mReceiver, intentFilter );

    }
    private void unregisterReceiver() {
        if (mReceiver != null) {
            this.unregisterReceiver( mReceiver );
            mReceiver = null;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent serviceIntent = new Intent(SwbwayReceiver.this,SubWayService.class);
        stopService(serviceIntent);
        unregisterReceiver();
    }
}


