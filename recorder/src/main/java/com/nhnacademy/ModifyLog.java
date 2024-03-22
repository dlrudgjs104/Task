package com.nhnacademy;

import org.json.JSONObject;
import java.time.LocalDateTime;

public class ModifyLog {
    private JSONObject modifyObject;

    public ModifyLog(){ 
        modifyObject = new JSONObject();
    }

    public JSONObject getModify(JSONObject modifyContent){
        modifyObject.put("MODIFYCONTENT", modifyContent);

        LocalDateTime modifyTime = LocalDateTime.now();
        modifyObject.put("MODIFYTIME", modifyTime);
        
        System.out.println(modifyObject);

        return modifyObject;
    }

}
