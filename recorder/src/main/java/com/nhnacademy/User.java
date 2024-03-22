package com.nhnacademy;

import org.json.JSONObject;

public class User {
    JSONObject userObject;

    public User(String id, String nikename){
        userObject = new JSONObject();
        userObject.put("ID", id);
        userObject.put("NIKENAME", nikename);
        
        System.out.println(userObject);
    }

    public JSONObject getUserObject(){
        return userObject;
    }
}
