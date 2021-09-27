package com.example.a1.whereami.SubwaySystem;

public class SubwayTimeInfo {
    String scode; // 역코드
    String line; // 호선
    String sname; // 한글역명
    String engname; // 영문역명
    String trainno; // 열차번호
    String hour; // 시간
    String time; // 분
    String day; // 요일
    String updown; // 상하행선
    String endcode; // 종착역


    SubwayTimeInfo(String hour, String time) {
        this.hour = hour;
        this.time = time;
    }
    SubwayTimeInfo(String scode, String line, String sname, String engname, String trainno, String hour, String time, String day, String updown, String endcode) {
        this.scode = scode;
        this.line = line;
        this.sname = sname;
        this.engname = engname;
        this.trainno = trainno;
        this.hour = hour;
        this.time = time;
        this.day = day;
        this.updown = updown;
        this.endcode = endcode;
    }
    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getEngname() {
        return engname;
    }

    public void setEngname(String engname) {
        this.engname = engname;
    }

    public String getTrainno() {
        return trainno;
    }

    public void setTrainno(String trainno) {
        this.trainno = trainno;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }

    public String getEndcode() {
        return endcode;
    }

    public void setEndcode(String endcode) {
        this.endcode = endcode;
    }
}
