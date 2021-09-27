package com.example.a1.whereami.SubwaySystem;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SubwayXmlParsing {


    SubwayXmlParsing () {
    }


    ArrayList<SubwayRunInfo> getRunInfoXmlData(int scode) {
        final ArrayList<SubwayRunInfo> subwayRunInfoArrayList = new ArrayList();

        try {

            final int STEP_NONE = 0;
            final int STEP_STSN = 1;
            final int STEP_STSC = 2;
            final int STEP_EDDSN = 3;
            final int STEP_ENDSC = 4;
            final int STEP_DIST = 5;
            final int STEP_TIME = 6;
            final int STEP_STOP = 7;
            final int STEP_EXCHANGE = 8;
            int step = STEP_NONE;


            String startSn = null; // 출발역명
            String startSc = null;
            ; // 출발역코드
            String endSn = null; // 도착역명
            String endSc = null;
            ; // 도착역코드
            String dist = null;
            ; // 이동거리
            String time = null;
            ; // 이동시간
            String stoppingTime = null;
            ; //정차시간
            String exchane = null;
            ; // 환승구분
            String url_num ="";

            String url_1="";


               url_1 = "http://data.humetro.busan.kr/voc/api/open_api_distance.tnn?act=xml&scode="+ scode +"&serviceKey=LGLrfRiFrvXAZzu7YjFb1oK9COAZK1yFeZWHW5Qt3f7bxREaoMwPkLvBcc6XFr2Kxq1CdOYnVgdrcB6cEJdrGg%3D%3D";

                URL url = new URL( url_1 );


            InputStream is = url.openStream(); //url위치로 입력스트림 연결


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput( new InputStreamReader( is, "euc-kr" ) ); //inputstream 으로부터 xml 입력받기

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.i( "xml시작", "파싱 시작" );
                } else if (eventType == XmlPullParser.START_TAG) {
                    String startTag = xmlPullParser.getName();
                    if (startTag.equals( "startSn" )) {
                        step = STEP_STSN;
                    } else if (startTag.equals( "startSc" )) {
                        step = STEP_STSC;
                    } else if (startTag.equals( "endSn" )) {
                        step = STEP_EDDSN;
                    } else if (startTag.equals( "endSc" )) {
                        step = STEP_ENDSC;
                    } else if (startTag.equals( "dist" )) {
                        step = STEP_DIST;
                    } else if (startTag.equals( "time" )) {
                        step = STEP_TIME;
                    } else if (startTag.equals( "stoppingTime" )) {
                        step = STEP_STOP;
                    } else if (startTag.equals( "exchange" )) {
                        step = STEP_EXCHANGE;
                    } else {
                        step = STEP_NONE;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xmlPullParser.getText();
                    if (step == STEP_STSN) {
                        startSn = text;
                        step = STEP_NONE;
                    } else if (step == STEP_STSC) {
                        startSc = text;
                        step = STEP_NONE;
                    } else if (step == STEP_EDDSN) {
                        endSn = text;
                        step = STEP_NONE;
                    } else if (step == STEP_ENDSC) {
                        endSc = text;
                        step = STEP_NONE;
                    } else if (step == STEP_DIST) {
                        dist = text;
                        step = STEP_NONE;
                    } else if (step == STEP_TIME) {
                        time = text;
                        step = STEP_NONE;
                    } else if (step == STEP_STOP) {
                        stoppingTime = text;
                        step = STEP_NONE;
                    } else if (step == STEP_EXCHANGE) {
                        exchane = text;
                        step = STEP_NONE;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = xmlPullParser.getName();
                    if (endTag.equals( "item" )) {
                        subwayRunInfoArrayList.add( new SubwayRunInfo( startSn, startSc, endSn, endSc, dist, time, stoppingTime, exchane ) );
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return subwayRunInfoArrayList;
    }


    ArrayList<SubwayTimeInfo> getTimeInfoXmlData(int start_no, int s_updown, int rows, String now_time, String count_day ) {
        final ArrayList<SubwayTimeInfo> subwayTimeInfoArrayList = new ArrayList();

        try {

            final int STEP_NONE = 0;
            final int STEP_SCODE = 1;
            final int STEP_LINE = 2;
            final int STEP_SANME = 3;
            final int STEP_ENGNAME = 4;
            final int STEP_TRAINNO = 5;
            final int STEP_HOUR = 6;
            final int STEP_TIME = 7;
            final int STEP_DAY = 8;
            final int STEP_UPDOWN = 9;
            final int STEP_ENDCODE = 10;

            int step = STEP_NONE;

            String scode = null ; // 역코드
            String line = null ; // 호선
            String sname = null ; // 한글역명
            String engname = null ; // 영문역명
            String trainno = null ; // 열차번호
            String hour = null ; // 시간
            String time = null ; // 분
            String day = null ; // 요일
            String updown = null ; // 상하행선
            String endcode = null ; // 종착역



            String queryUrl="http://data.humetro.busan.kr/voc/api/open_api_process.tnn?"//요청 URL
                    +"act=xml"
                    +"&scode="+ start_no
                    +"&day="+ count_day
                    +"&stime="+ now_time
                    +"&updown=" + s_updown
                    +"&enum=" + rows
                    +"&numOfRows=" + rows
                    +"&serviceKey=xejr5dnDWsS0Sac9qOi9sBgpsgKWzD3KWjhjF6JW9jxNk3Gx6ipJgwWpjuVRgVopnAJVyMxfr0phyEFDIA%2BVkA%3D%3D";

             //+"&serviceKey=LGLrfRiFrvXAZzu7YjFb1oK9COAZK1yFeZWHW5Qt3f7bxREaoMwPkLvBcc6XFr2Kxq1CdOYnVgdrcB6cEJdrGg%3D%3D";
            URL url = new URL( queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결


            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput( new InputStreamReader( is, "euc-kr" ) ); //inputstream 으로부터 xml 입력받기

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.i( "xml시작", "파싱 시작" );
                } else if (eventType == XmlPullParser.START_TAG) {
                    String startTag = xmlPullParser.getName();
                    if (startTag.equals( "scode" )) {
                        step = STEP_SCODE;
                    } else if (startTag.equals( "line" )) {
                        step = STEP_LINE;
                    } else if (startTag.equals( "sname" )) {
                        step = STEP_SANME;
                    } else if (startTag.equals( "engname" )) {
                        step = STEP_ENGNAME;
                    } else if (startTag.equals( "trainno" )) {
                        step = STEP_TRAINNO;
                    } else if (startTag.equals( "hour" )) {
                        step = STEP_HOUR;
                    } else if (startTag.equals( "time" )) {
                        step = STEP_TIME;
                    } else if (startTag.equals( "day" )) {
                        step = STEP_DAY;
                    }else if (startTag.equals( "updown" )) {
                        step = STEP_UPDOWN;
                    }else if (startTag.equals( "endcode" )) {
                            step = STEP_ENDCODE;
                    } else {
                        step = STEP_NONE;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xmlPullParser.getText();
                    if (step == STEP_SCODE) {
                        scode = text;
                        step = STEP_NONE;
                    } else if (step == STEP_LINE) {
                        line = text;
                        step = STEP_NONE;
                    } else if (step == STEP_SANME) {
                        sname = text;
                        step = STEP_NONE;
                    } else if (step == STEP_ENGNAME) {
                        engname = text;
                        step = STEP_NONE;
                    } else if (step == STEP_TRAINNO) {
                        trainno = text;
                        step = STEP_NONE;
                    } else if (step == STEP_HOUR) {
                        hour = text;
                        step = STEP_NONE;
                    } else if (step == STEP_TIME) {
                        time = text;
                        step = STEP_NONE;
                    } else if (step == STEP_DAY) {
                        day = text;
                        step = STEP_NONE;
                    } else if (step == STEP_UPDOWN) {
                        updown = text;
                        step = STEP_NONE;
                    } else if (step == STEP_ENDCODE) {
                        endcode = text;
                        step = STEP_NONE;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = xmlPullParser.getName();
                    if (endTag.equals( "item" )) {
                        subwayTimeInfoArrayList.add( new SubwayTimeInfo( scode, line, sname, engname, trainno,hour,time, day , updown, endcode ) );
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return subwayTimeInfoArrayList;
    }
}