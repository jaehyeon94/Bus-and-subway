package com.example.a1.whereami;

public class LineInfo {
    private String carno;
    private String bstopnm;

    public LineInfo(String carno, String bstopnm) {
        this.carno = carno;
        this.bstopnm = bstopnm;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getBstopnm() {
        return bstopnm;
    }

    public void setBstopnm(String bstopnm) {
        this.bstopnm = bstopnm;
    }
}
