package com.nhnacademy;

import org.json.JSONObject;
import java.io.*;

public class DataHandler {
    private File dataFile;
    private JSONObject database;


    public DataHandler(String filePath) {
        dataFile = new File(filePath);
        database = new JSONObject();

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
    }

    public File getDataFile(){
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
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    // 데이터베이스 내용을 파일에 저장
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFile))) {
            writer.print(database.toString());
        } catch (IOException e) {
            e.printStackTrace();
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

    // 프로그램 종료 시 데이터를 파일에 저장
    public void close() {
        saveToFile();
    }
}
