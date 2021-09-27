package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MoniterStationService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("서비스시작","서비스시작!!");

        final String lineid = intent.getStringExtra("lineid");
        final String carno = intent.getStringExtra("carno");

        final StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final int STEP_NONE = 0;
                final int STEP_BSTOPNAME = 1;
                final int STEP_CARNO = 2;
                int step = STEP_NONE;
                boolean countCheck = false;
                int count = 0;

                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busInfoRoute?lineid=" + lineid + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
                Log.e("라인아이디값 : ",lineid);
                while(true) {
                    try {
                        count = 0;
                        URL url = new URL(strurl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                        String bsstopname = null;
                        String station_carno = null;
                        String nextStation = null;
                        String nowstationTemp = startDestinationVO.getStartStation();
                        String selectedStation = null;

                        boolean isFindNowStation = false;

                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            XmlPullParser xmlPullParser = Xml.newPullParser();
                            xmlPullParser.setInput(conn.getInputStream(), "UTF-8");
                            int eventType = xmlPullParser.getEventType();

                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if (eventType == XmlPullParser.START_DOCUMENT) {
                                    Log.i("xml시작", "모니터링의 시작");

                                } else if (eventType == XmlPullParser.START_TAG) {
                                    String startTag = xmlPullParser.getName();
                                    if (startTag.equals("bstopnm")) {
                                        step = STEP_BSTOPNAME;
                                    } else if (startTag.equals("carNo")) {
                                        step = STEP_CARNO;
                                    }
                                } else if (eventType == XmlPullParser.TEXT) {
                                    String text = xmlPullParser.getText();
                                    if (step == STEP_BSTOPNAME) {
                                        Log.i("버스스탑: ", text);
                                        bsstopname = text;
                                        if (isFindNowStation) {
                                            nextStation = text;
                                            isFindNowStation = false;
                                            Log.e("nextStation",text);
                                            startDestinationVO.setNextStation(nextStation);
                                        }
                                        if(countCheck){
                                            count++;
                                            if(startDestinationVO.getDestinationStation().equals(bsstopname)){
                                                countCheck =false;
                                            }
                                        }
                                        step = STEP_NONE;
                                    } else if (step == STEP_CARNO) {
                                        Log.i("버스번호 : ", text);
                                        station_carno = text;
                                        if (carno.equals(station_carno.substring(3))) {
                                            startDestinationVO.setStartStation(bsstopname);
                                            selectedStation = bsstopname;
                                            isFindNowStation = true;
                                            countCheck = true;
                                        } else if(carno.equals(station_carno.substring(4))) {
                                            startDestinationVO.setStartStation( bsstopname );
                                            selectedStation = bsstopname;
                                            isFindNowStation = true;
                                            countCheck = true;
                                        }
                                        step = STEP_NONE;
                                    }
                                }
                                eventType = xmlPullParser.next();
                            }
                        }

                        if (!nowstationTemp.equals(selectedStation)) {
                            Log.e("정류장의 갱신","정류장갱신 브로드캐스트~!");
                            Intent intent = new Intent();
                            intent.setAction("changeStation");
                            intent.putExtra("count",count);
                            sendBroadcast(intent);
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }

           }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };

        asyncTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

}
