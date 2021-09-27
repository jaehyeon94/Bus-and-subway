package com.example.a1.whereami.SubwaySystem;


public class SubwayInfo {
    public String k_name;
    public String e_name;
    public int metro_no;
    public int type;

    public SubwayInfo() {
    }

    public SubwayInfo(String k_name, String e_name, int metro_no, int type) {
        this.e_name = e_name;
        this.k_name = k_name;
        this.metro_no = metro_no;
        this.type = type;

    }
    public String getK_name() {
        return k_name;
    }

    public void setK_name(String k_name) {
        this.k_name = k_name;
    }

    public String getE_name() {
        return e_name;
    }

    public void setE_name(String e_name) {
        this.e_name = e_name;
    }

    public int getMetro_no() {
        return metro_no;
    }

    public void setMetro_no(int metro_no) {
        this.metro_no = metro_no;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
