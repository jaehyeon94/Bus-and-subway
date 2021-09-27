package com.example.a1.whereami;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StationParsing{
    StartDestinationVO startDestinationVO = StartDestinationVO.getInstance();
    String API_KEY = "nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
    String lineid;
    String select_carno;

    String station_name;
    RecyclerView.Adapter adapter;
    ArrayList<StopBusVO> stopBusVOs;
    ArrayList<LineInfo> lineInfos;

    StationParsing(String station_name, RecyclerView.Adapter adapter){
        this.station_name = station_name;
        this.adapter = adapter;
    }

    public StationParsing(String lineid, String carno, RecyclerView.Adapter adapter) {
        this.lineid = lineid;
        this.select_carno = carno;
        this.adapter = adapter;
    }

    void assendingArray(){
        Comparator comparator = new Comparator<StopBusVO>() {

            @Override
            public int compare(StopBusVO o1, StopBusVO o2) {
                if(o1.getRemain_min()>o2.getRemain_min()){
                    return 1;
                }
                else{
                    return -1;
                }
            }
        };
        Collections.sort(stopBusVOs,comparator);
    }

    public void searchStopBus(ArrayList<StopBusVO> stopBuss){

        this.stopBusVOs = stopBuss;

        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void ...voids) {
                final int STEP_NONE = 0;
                final int STEP_ARSNO = 1;
                final int STEP_CARNO = 2;
                final int STEP_LINEID = 3;
                final int STEP_LINENO = 4;
                final int STEP_REMAIN_MIN = 5;
                final int STEP_REMAIN_STATION= 6;

                int step = STEP_NONE;

                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/stopArr?bstopid=" + station_name + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String arsNo = null;
                    String carNo = null;
                    String lineid = null;
                    String lineno = null;
                    int remain_min = 0;
                    String remain_station = null;

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        XmlPullParser xmlPullParser = Xml.newPullParser();
                        xmlPullParser.setInput(conn.getInputStream(), "UTF-8");
                        int eventType = xmlPullParser.getEventType();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_DOCUMENT) {
                                Log.i("xml시작", "버스정류소 파싱의 시작");
                            } else if (eventType == XmlPullParser.START_TAG) {
                                String startTag = xmlPullParser.getName();

                                if (startTag.equals("arsNo")) {
                                    step = STEP_ARSNO;
                                } else if (startTag.equals("carNo1")) {
                                    step = STEP_CARNO;
                                } else if (startTag.equals("lineNo")) {
                                    step = STEP_LINENO;
                                } else if (startTag.equals("lineid")) {
                                    step = STEP_LINEID;
                                } else if (startTag.equals("min1")) {
                                    step = STEP_REMAIN_MIN;
                                } else if (startTag.equals("station1")) {
                                    step = STEP_REMAIN_STATION;
                                }
                            } else if (eventType == XmlPullParser.TEXT) {
                                String text = xmlPullParser.getText();
                                if (step == STEP_ARSNO) {
                                    arsNo = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_CARNO) {
                                    carNo = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_LINEID) {
                                    lineid = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_LINENO) {
                                    lineno = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_REMAIN_MIN) {
                                    remain_min = Integer.valueOf(text);
                                    step = STEP_NONE;
                                } else if (step == STEP_REMAIN_STATION) {
                                    remain_station = text;
                                    step = STEP_NONE;
                                }

                            } else if (eventType == XmlPullParser.END_TAG) {
                                String endTag = xmlPullParser.getName();
                                if (endTag.equals("item")) {
                                    stopBusVOs.add(new StopBusVO(arsNo,carNo,lineid,lineno,remain_min,remain_station));
                                }
                            }
                            eventType = xmlPullParser.next();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                assendingArray();
                adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }
        };

        asyncTask.execute();
    }

    public void monitorStation(){

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final int STEP_NONE = 0;
                final int STEP_BSTOPNAME = 1;
                final int STEP_CARNO = 2;
                int step = STEP_NONE;

                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busInfoRoute?lineid=" + lineid + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
                Log.e("라인아이디값 : ",lineid);
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String bsstopname = null;
                    String station_carno = null;
                    String nextStation = null;
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
                                    Log.i("bstopname : ", text);
                                    bsstopname = text;
                                    if(isFindNowStation){
                                        nextStation = text;
                                        startDestinationVO.setNextStation(nextStation);
                                    }
                                    step = STEP_NONE;
                                } else if (step == STEP_CARNO) {
                                    Log.i("bstopcarno : ", text);
                                    station_carno = text;
                                    if(select_carno.equals(station_carno.substring(4))){
                                        startDestinationVO.setStartStation(bsstopname);
                                        isFindNowStation = true;
                                    }
                                    step = STEP_NONE;
                                }
                            } else if (eventType == XmlPullParser.END_TAG) {
                                String endTag = xmlPullParser.getName();
                                if (endTag.equals("item")) {
                                    Log.e("lineinfo : ",station_carno + " , "+ bsstopname);
                                    lineInfos.add(new LineInfo(station_carno, bsstopname));
                                    station_carno= null;
                                }
                            }
                            eventType = xmlPullParser.next();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }
        };

        asyncTask.execute();
    }

    public void searchDestinationStop(final ArrayList<LineInfo> lineInfos) {
        this.lineInfos = lineInfos;
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                final int STEP_NONE = 0;
                final int STEP_BSTOPNAME = 1;
                final int STEP_CARNO = 2;
                int step = STEP_NONE;


                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busInfoRoute?lineid=" + station_name + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D";
                Log.e("라인아이디값 : ",station_name);
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String bsstopname = null;
                    String station_carno = null;
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        XmlPullParser xmlPullParser = Xml.newPullParser();
                        xmlPullParser.setInput(conn.getInputStream(), "UTF-8");
                        int eventType = xmlPullParser.getEventType();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_DOCUMENT) {
                                Log.i("xml시작", "목적지 파싱의 시작");

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
                                    Log.i("bstopname : ", text);
                                    bsstopname = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_CARNO) {
                                    Log.i("bstopcarno : ", text);
                                    station_carno = text;
                                    step = STEP_NONE;

                                }
                            } else if (eventType == XmlPullParser.END_TAG) {
                                        String endTag = xmlPullParser.getName();
                                        if (endTag.equals("item")) {
                                            Log.e("lineinfo : ",station_carno + " , "+ bsstopname);
                                            lineInfos.add(new LineInfo(station_carno, bsstopname));
                                            station_carno= null;
                                        }
                                    }
                            eventType = xmlPullParser.next();
                        }
                        }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }
        };

        asyncTask.execute();
    }


    public void searchStation(ArrayList<Station> stations) {
        final ArrayList<Station> stationList = stations;

        @SuppressLint("StaticFieldLeak") AsyncTask<Void,Void,Void> asyncTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void ...voids) {
                final int STEP_NONE = 0;
                final int STEP_BSTOPARSNO = 1;
                final int STEP_BSTOPNM = 2;
                final int STEP_STOPTYPE = 3;
                final int STEP_BSTOPID = 4;
                int step = STEP_NONE;


                String strurl = "http://61.43.246.153/openapi-data/service/busanBIMS2/busStop?bstopnm=" + station_name + "&serviceKey=nQXBhoqZKdnFHvwi2%2Bl6JZO4garNidtHzdktRpqhjVH9GX5saW9tv5HNeSLWrSDFbAf9iXRnVIWmWToD6n1xTA%3D%3D&numOfRows=100&pageNo=1";
                try {
                    URL url = new URL(strurl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    int bstoparsno = 0;
                    int busstopid = 0;
                    String bstopNm = null;
                    String stoptype = null;

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        XmlPullParser xmlPullParser = Xml.newPullParser();
                        xmlPullParser.setInput(conn.getInputStream(), "UTF-8");
                        int eventType = xmlPullParser.getEventType();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            if (eventType == XmlPullParser.START_DOCUMENT) {
                                Log.i("xml시작", "시작지 파싱의 시작");
                            } else if (eventType == XmlPullParser.START_TAG) {
                                String startTag = xmlPullParser.getName();

                                if (startTag.equals("bstopArsno")) {
                                    step = STEP_BSTOPARSNO;
                                } else if (startTag.equals("bstopNm")) {
                                    step = STEP_BSTOPNM;
                                } else if (startTag.equals("stoptype")) {
                                    step = STEP_STOPTYPE;
                                } else if (startTag.equals("bstopId")){
                                    step = STEP_BSTOPID;
                                }
                            } else if (eventType == XmlPullParser.TEXT) {
                                String text = xmlPullParser.getText();
                                if (step == STEP_BSTOPARSNO) {
                                    Log.i("bstoparsno : ", text);
                                    bstoparsno = Integer.parseInt(text);
                                    step = STEP_NONE;
                                } else if (step == STEP_BSTOPNM) {
                                    Log.i("bstopNm : ", text);
                                    bstopNm = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_STOPTYPE) {
                                    Log.i("stoptype : ", text);
                                    stoptype = text;
                                    step = STEP_NONE;
                                } else if (step == STEP_BSTOPID){
                                    Log.i("stopid : ", text);
                                    busstopid = Integer.parseInt(text);
                                    step = STEP_BSTOPID;
                                }
                            } else if (eventType == XmlPullParser.END_TAG) {
                                String endTag = xmlPullParser.getName();
                                if (endTag.equals("item")) {
                                    Log.i("dataset : ", bstoparsno + "," + bstopNm + "," + stoptype);
                                    stationList.add(new Station(busstopid,bstoparsno, 0, bstopNm, stoptype));
                                }
                            }
                            eventType = xmlPullParser.next();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                super.onPostExecute(aVoid);
            }


        };

        asyncTask.execute();
    }
}
