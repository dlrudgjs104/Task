package com.nhnacademy;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;

public class DataHandler {
    private File dataFile;
    private JSONObject database;
    private String filePath;

    public DataHandler(String filePath) {
        this.filePath = filePath;
        dataFile = new File(filePath);
        database = new JSONObject();
    }

    public void operate() {
        // 프로그램 시작 시 파일이 존재하면 데이터를 읽어서 데이터베이스를 생성
        if (dataFile.exists()) {
            try {
                String data = readFromFile(dataFile);
                if (!data.isEmpty()) {
                    database = new JSONObject(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        baseSetting(filePath);
    }

    // 데이터베이스 기본값 세팅
    public void baseSetting(String filePath) {
        if (filePath.equals("./src/main/java/com/nhnacademy/setting.json")) {
            if (!database.has("deny_list")) {
                JSONArray denyList = new JSONArray();
                database.put("deny_list", denyList);
                saveToFile();
            }
        } else {
            if (!database.has("log")) {
                JSONArray logList = new JSONArray();
                database.put("log", logList);
                saveToFile();
            }
        }
    }

    public File getDataFile() {
        return dataFile;
    }

    // 데이터베이스에 값을 추가 또는 갱신
    public void put(String key, Object value) {
        database.put(key, value);
    }

    // 특정 키에 해당하는 값을 가져옴
    public Object get(String key) {
        try {
            return database.get(key);
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }

    // 데이터베이스 내용을 파일에 저장
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            writer.print(database.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("데이터베이스에 내용을 저장하지 못하였습니다.");
        }
    }

    // 파일에서 데이터를 읽어옴
    private String readFromFile(File file) throws IOException {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line);
            }
        }
        return data.toString();
    }
}
