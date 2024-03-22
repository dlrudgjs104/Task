package com.nhnacademy;

import org.json.JSONObject;

public class Unit {
    private JSONObject unitObject;

    public Unit(String unitId, String model, int hp, int attackPower, int defence, int movingSpeed, int attackSpeed){    
        unitObject = new JSONObject();
        unitObject.put("UNITID", unitId);
        unitObject.put("MODEL", model);
        unitObject.put("HP", hp);
        unitObject.put("ATTACKPOWER", attackPower);
        unitObject.put("DEFENSE", defence);
        unitObject.put("MOVINGSPEED", movingSpeed);
        unitObject.put("ATTACKSPEED", attackSpeed);
        System.out.println(unitObject);   
    }

    public JSONObject getUnitObject(){
        return unitObject;
    }

}
