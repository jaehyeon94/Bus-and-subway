package com.example.a1.whereami;

public class Station {
    private int busstopId;
    private int bosstopArsId;
    private int distance;
    private String busstopname;
    private String stoptype;

    public Station(int busstopId,int bosstopArsId, int destination, String busstopname, String stoptype) {
        this.busstopId = busstopId;
        this.bosstopArsId = bosstopArsId;
        this.distance = destination;
        this.busstopname = busstopname;
        this.stoptype = stoptype;
    }

    public int getBosstopArsId() {
        return bosstopArsId;
    }

    public void setBosstopArsId(int bosstopArsId) {
        this.bosstopArsId = bosstopArsId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int destination) {
        this.distance = destination;
    }

    public String getBusstopname() {
        return busstopname;
    }

    public void setBusstopname(String busstopname) {
        this.busstopname = busstopname;
    }

    public String getStoptype() {
        return stoptype;
    }

    public void setStoptype(String stoptype) {
        this.stoptype = stoptype;
    }

    public int getBusstopId() {
        return busstopId;
    }

    public void setBusstopId(int busstopId) {
        this.busstopId = busstopId;
    }
}
