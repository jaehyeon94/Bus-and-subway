package com.example.a1.whereami;

public class StopBusVO {
    private String arsNo;
    private String carNo;
    private String lineid;
    private String lineno;
    private int remain_min;
    private String remain_station;

    public StopBusVO(String arsNo, String carNo, String lineid, String lineno, int remain_min, String remain_station) {
        this.arsNo = arsNo;
        this.carNo = carNo;
        this.lineid = lineid;
        this.lineno = lineno;
        this.remain_min = remain_min;
        this.remain_station = remain_station;
    }

    public String getArsNo() {
        return arsNo;
    }

    public void setArsNo(String arsNo) {
        this.arsNo = arsNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getLineno() {
        return lineno;
    }

    public void setLineno(String lineno) {
        this.lineno = lineno;
    }

    public int getRemain_min() {
        return remain_min;
    }

    public void setRemain_min(int remain_min) {
        this.remain_min = remain_min;
    }

    public String getRemain_station() {
        return remain_station;
    }

    public void setRemain_station(String remain_station) {
        this.remain_station = remain_station;
    }
}
