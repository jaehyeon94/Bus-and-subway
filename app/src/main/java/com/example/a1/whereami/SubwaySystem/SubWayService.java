package com.example.a1.whereami.SubwaySystem;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.a1.whereami.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SubWayService extends Service {
    @Nullable
    ArrayList<SubwayRoute> subwayRouteArrayList;
    SubwayTime subwayTime;
    Thread thread;
    int i =0;
    int s =0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        subwayRouteArrayList = (ArrayList<SubwayRoute>) intent.getExtras().getSerializable( "subway_route" );
        subwayTime = (SubwayTime) intent.getExtras().getSerializable( "subwayTime" );
        final int end_count = subwayRouteArrayList.size();
        thread = new Thread() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                Date date = new Date( now );
                SimpleDateFormat sdf = new SimpleDateFormat( "HHmm" );
                SimpleDateFormat sdf1 = new SimpleDateFormat( "ss" );

                int now_time = Integer.parseInt( sdf.format( date ) );
                int now_ss = Integer.parseInt( sdf1.format( date ) );
                int count = end_count;
                int num =0;
                String start = subwayRouteArrayList.get( 0 ).start;
                int start_time = Integer.parseInt( start.replaceAll( ":", "" ) );

                if (start_time - now_time < 0) {
                    int s = 10000;
                    Intent sendintent = new Intent( "com.example.a1.whereami.SubwaySystem" );
                    sendintent.putExtra( "subway_route", subwayRouteArrayList.get( 0 ) );
                    sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( 0 ) );
                    sendintent.putExtra( "count", s );
                    sendBroadcast( sendintent );
                } else {
                    Intent sendintent;
                    if (start_time - now_time != 0) {
                        sendintent = new Intent( "com.example.a1.whereami.SubwaySystem" );
                        sendintent.putExtra( "subway_route", subwayRouteArrayList.get( 0 ) );
                        sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( 0 ) );
                        sendintent.putExtra( "count", end_count );
                        sendBroadcast( sendintent );
                        try {
                            Thread.sleep( ((start_time - now_time) * 1000 * 60) - (now_ss * 1000) );
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } while (true) {
                        now = System.currentTimeMillis();
                        date = new Date( now );
                        sdf = new SimpleDateFormat( "HHmm" );
                        now_time = Integer.parseInt( sdf.format( date ) );
                        if (start_time - now_time == 0) {
                            for (i = 0; i < subwayRouteArrayList.size() - 1; i++) {
                                count--;
                                sendintent = new Intent( "com.example.a1.whereami.SubwaySystem" );
                                sendintent.putExtra( "subway_route", subwayRouteArrayList.get( i ) );
                                sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( i + 1 ) );
                                sendintent.putExtra( "count", count );
                                sendBroadcast( sendintent );
                                if (subwayRouteArrayList.get( i + 1 ).transfer != 1) {
                                    if(count == 1) {
                                        try {
                                            Thread.sleep( (subwayRouteArrayList.get( i + 1 ).time * 1000) - 50000 );
                                            s = 500;
                                            sendintent.putExtra( "count", s );
                                            sendBroadcast( sendintent );
                                            Thread.sleep( 50000 );
                                            sendintent.putExtra( "count", 0 );
                                            sendBroadcast( sendintent );
                                            Thread.sleep( 10000 );

                                            sendintent = new Intent( getApplicationContext(),SubwayStartActivity.class );
                                            sendintent.putExtra("Type",1);
                                            startActivity(sendintent);

                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            Thread.sleep( subwayRouteArrayList.get( i + 1 ).time * 1000 );
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else if (subwayRouteArrayList.get( i + 1 ).transfer == 1) {
                                    if(num == 0) {
                                    try {
                                        Thread.sleep( (subwayRouteArrayList.get( i + 1 ).time * 1000) - 40000 );
                                        s = 500;
                                        sendintent.putExtra( "count", s );
                                        sendBroadcast( sendintent );
                                        Thread.sleep( 40000 );
                                        s = 1000;
                                        sendintent.putExtra( "subway_route", subwayRouteArrayList.get( i + 1 ) );
                                        sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( i + 2 ) );
                                        sendintent.putExtra( "count", s );
                                        sendBroadcast( sendintent );
                                        Thread.sleep( 120000 + (subwayTime.time * 1000) );

                                        num++;
                                    }catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    } else if(num == 1){
                                    try {
                                        Thread.sleep( (subwayRouteArrayList.get( i + 1 ).time * 1000) - 40000 );
                                        s = 500;
                                        sendintent.putExtra( "count", s );
                                        sendBroadcast( sendintent );
                                        Thread.sleep( 40000 );
                                        s = 1000;
                                        sendintent.putExtra( "subway_route", subwayRouteArrayList.get( i + 1 ) );
                                        sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( i + 2 ) );
                                        sendintent.putExtra( "count", s );
                                        sendBroadcast( sendintent );
                                        Thread.sleep( 120000 + (subwayTime.time1 * 1000) );

                                        num++;
                                    }catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                    while (true) {
                                        now = System.currentTimeMillis();
                                        date = new Date( now );
                                        sdf = new SimpleDateFormat( "HHmm" );
                                        now_time = Integer.parseInt( sdf.format( date ) );
                                        start = subwayRouteArrayList.get( i + 1 ).start;
                                        start_time = Integer.parseInt( start.replaceAll( ":", "" ) );
                                        if (start_time - now_time == 0) {
                                            count--;
                                            sendintent = new Intent( "com.example.a1.whereami.SubwaySystem" );
                                            sendintent.putExtra( "subway_route", subwayRouteArrayList.get( i ) );
                                            sendintent.putExtra( "subway_route_next", subwayRouteArrayList.get( i + 1 ) );
                                            sendintent.putExtra( "count", count );
                                            sendBroadcast( sendintent );
                                            i++;
                                            try {
                                                Thread.sleep( subwayRouteArrayList.get( i + 1 ).time * 1000 );
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        } else if (start_time - now_time != 0) {
                                            break;
                                        } else if(count == 1) {
                                            try {
                                                Thread.sleep( (subwayRouteArrayList.get( i + 1 ).time * 1000) - 50000 );
                                                s = 500;
                                                sendintent.putExtra( "count", s );
                                                sendBroadcast( sendintent );
                                                Thread.sleep( 50000 );
                                                sendintent.putExtra( "count", 0 );
                                                sendBroadcast( sendintent );
                                                Thread.sleep( 10000 );

                                                sendintent = new Intent( getApplicationContext(),SubwayStartActivity.class );
                                                sendintent.putExtra("Type",1);
                                                startActivity(sendintent);

                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            break;
                                        }
                                    }
                                } else if (start_time - now_time != 0) {
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
        thread.start();
        return START_STICKY;
        //super.onStartCommand( intent, flags, startId );

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        i =subwayRouteArrayList.size()+1;
    }
}

