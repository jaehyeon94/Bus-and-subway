package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class NowNextStationActivity extends AppCompatActivity {
    TextView nowStation, nextStation, destinationStation,countText ;
    String lineid;
    String carno;
    boolean change=true;
    private BroadcastReceiver broadcastReceiver= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_and_next_station_layout);
        Drawable alpha = findViewById(R.id.nowandnextstationlayout).getBackground();
        alpha.setAlpha(50);
        nowStation = findViewById(R.id.nowstation);
        nextStation = findViewById(R.id.nextstation);
        destinationStation = findViewById(R.id.destinationstation);
        countText = findViewById(R.id.count);


        Intent intent = getIntent();
        carno = intent.getStringExtra("carno");
        lineid = intent.getStringExtra("lineid");


        StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
        String nextstation = startDestinationVO.getNextStation();
        String startsation = startDestinationVO.getStartStation();
        String destinationstation = startDestinationVO.getDestinationStation();
        final TextView timer = findViewById(R.id.timer);


        nowStation.setText(startsation);
        nextStation.setText(nextstation);
        destinationStation.setText(destinationstation);

        Log.i("원하는 정보 : ", "carno : " + carno + "" + "nextStation" + nextStation + "startstation" + startsation + "destination" + destinationstation);

        intent = new Intent(this,MoniterStationService.class);
        intent.putExtra("lineid",lineid);
        intent.putExtra("carno",carno);

        //Toast.makeText( NowNextStationActivity.this,carno ,Toast.LENGTH_LONG).show();
        this.startService(intent);

        /*@SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> countthread = new AsyncTask<Void,Void,Void>() {
            int counthelper = 30;

            @Override
            protected void onPostExecute(Void aVoid) {
                change = false;
            }

            @Override
            protected Void doInBackground(Void... voids) {
                Log.e("카운트 호출 : ", ""+counthelper);
                while(counthelper>0) {
                    counthelper--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    onProgressUpdate();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                timer.setText("갱신 " + counthelper + "초전");
            }


        };

            countthread.execute();*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        Log.e("프로바이더 호출","프로바이더 생성");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver();
        Log.e("프로바이더 호출","프로바이더 제거");
    }

    private void registerReceiver(){
        if(broadcastReceiver != null) return;

        IntentFilter theFilter = new IntentFilter();
        theFilter.addAction("changeStation");

        this.broadcastReceiver = new BroadcastReceiver() {
            StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();

            @Override
            public void onReceive(Context context, Intent intent) {
                int count = intent.getIntExtra("count",0);
                change = true;

                if(intent.getAction().equals("changeStation")){
                    nowStation.setText(startDestinationVO.getStartStation());
                    nextStation.setText(startDestinationVO.getNextStation());
                    countText.setText("목적지 까지 " +count + "정류장 전");
                }
                if(startDestinationVO.getNextStation().equals(startDestinationVO.getDestinationStation())){
                    Vibrator vibrator;
                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(2000);
                    Toast.makeText(context, "목적지 도착 한 정거장 전입니다.", Toast.LENGTH_SHORT).show();
                }
                if(startDestinationVO.getDestinationStation().equals(startDestinationVO.getStartStation())){
                    Vibrator vibrator;
                    vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    vibrator.vibrate(2000);
                    Toast.makeText(context, "목적지에 도착했습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        };
        this.registerReceiver(this.broadcastReceiver,theFilter);
    }

    private void unregisterReceiver(){
        if(broadcastReceiver!=null){
            broadcastReceiver = null;
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
