package com.example.a1.whereami.SubwaySystem;

import java.io.Serializable;

public class SubwayTime implements Serializable {
    int time, time1, transfer;
    SubwayTime(){
    time1 =0;
    time =0;
    }
    SubwayTime(int time, int time1) {
        this.time = time;
        this.time1 = time1;
    }
}
