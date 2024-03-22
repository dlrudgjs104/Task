package com.nhnacademy;

import org.json.JSONObject;

public class Score {
    private int fightCount = 0;
    private int vitoryCount = 0;
    JSONObject scoreObject;

    public Score(){
        scoreObject = new JSONObject();
    }
}
