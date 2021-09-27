package com.example.a1.whereami.SubwaySystem;

public class SubwayRunInfo {

    String startSn; // 출발역명
    String startSc; // 출발역코드
    String endSn; // 도착역명
    String endSc; // 도착역코드
    String dist; // 이동거리
    String time; // 이동시간
    String stoppingTime; //정차시간
    String exchane; // 환승구분

    SubwayRunInfo() {

    }
    SubwayRunInfo(String startSn, String startSc, String endSn, String endSc, String dist, String time, String stoppingTime, String exchane) {
        this.startSn = startSn;
        this.startSc = startSc;
        this.endSn = endSn;
        this.endSc = endSc;
        this.dist = dist;
        this.time = time;
        this.stoppingTime = stoppingTime;
        this.exchane = exchane;
    }
    public String getStartSn() {
        return startSn;
    }

    public void setStartSn(String startSn) {
        this.startSn = startSn;
    }

    public String getStartSc() {
        return startSc;
    }

    public void setStartSc(String startSc) {
        this.startSc = startSc;
    }

    public String getEndSn() {
        return endSn;
    }

    public void setEndSn(String endSn) {
        this.endSn = endSn;
    }

    public String getEndSc() {
        return endSc;
    }

    public void setEndSc(String endSc) {
        this.endSc = endSc;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStoppingTime() {
        return stoppingTime;
    }

    public void setStoppingTime(String stoppingTime) {
        this.stoppingTime = stoppingTime;
    }

    public String getExchane() {
        return exchane;
    }

    public void setExchane(String exchane) {
        this.exchane = exchane;
    }
}
