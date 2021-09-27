package com.example.a1.whereami.SubwaySystem;

import java.io.Serializable;

public class SubwayRoute implements Serializable {
    String station, start ,end, direction_name, direction_no;
    int count, no, transfer, direction, time;


    SubwayRoute(){

    }
    SubwayRoute(String station,String end) {
        this.start = station;
        this.end = end;
    }

    SubwayRoute(String station, int time) {
        this.station = station;
        this.time = time;
        transfer = 0;
        no = 0;
    }
    SubwayRoute(String station, String start, String end, int count) {
        this.station = station;
        this.start = start;
        this.end = end;
        this.count = count;
    }
}
