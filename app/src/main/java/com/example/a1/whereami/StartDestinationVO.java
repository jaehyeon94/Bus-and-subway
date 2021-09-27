package com.example.a1.whereami;

public class StartDestinationVO {
    private static StartDestinationVO startDestinationVO = new StartDestinationVO();
    private String startStation;
    private String DestinationStation;
    private String nextStation;


    static public StartDestinationVO getInstance(){
        return startDestinationVO;
    }

    public String getNextStation() {
        return nextStation;
    }

    public void setNextStation(String nextStation) {
        this.nextStation = nextStation;
    }

    private StartDestinationVO() {
    }

    private StartDestinationVO(String startStation, String destinationStation) {
        this.startStation = startStation;
        DestinationStation = destinationStation;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getDestinationStation() {
        return DestinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        DestinationStation = destinationStation;
    }
}
