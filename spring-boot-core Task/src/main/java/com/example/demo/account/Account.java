package com.example.demo.account;

import com.example.demo.price.Price;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

@Slf4j
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    @JsonProperty("아이디")
    Long id;
    @JsonProperty("비밀번호")
    String password;
    @JsonProperty("이름")
    String name;

    public static List<Account> parse (String filePath){
        List<Account> accounts = new LinkedList<>();

        try (InputStream inputStream = readFromCsv(filePath);
             InputStreamReader csvData = new InputStreamReader(inputStream);
             CSVParser csvParser = CSVParser.parse(csvData, CSVFormat.EXCEL)) {

            List<CSVRecord> csvRecordList = csvParser.getRecords();

            for (int record_i = 1; record_i < csvRecordList.size(); record_i++) {
                CSVRecord csvRecord = csvRecordList.get(record_i);

                Long id = Long.parseLong(csvRecord.get(0).trim());
                String password = csvRecord.get(1).trim();
                String name = csvRecord.get(2).trim();

                Account account = new Account(id, password, name);
                accounts.add(account);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return accounts;
    }

    public static InputStream readFromCsv(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            log.info("error: {}", e.getMessage());
            return null;
        }
    }

    public static List<Account> readFromJson(String filePath) {
        File jsonFile = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    @Override
    public String toString() {
        return String.format("Account(id: %d, password: %s, name: %s)", id, password, name);
    }
}
