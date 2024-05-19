package com.example.demo.price;

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
public class Price {
    @JsonProperty("순번")
    Long id;
    @JsonProperty("지자체명")
    String city;
    @JsonProperty("업종")
    String sector;
    @JsonProperty("구간금액(원)")
    int unitPrice;

    public Price getPrice(){
        return (this);
    }

    public static List<Price> parse (String filePath){
        List<Price> prices = new LinkedList<>();

        try (InputStream inputStream = readFromCsv(filePath);
             InputStreamReader csvData = new InputStreamReader(Objects.requireNonNull(inputStream));
             CSVParser csvParser = CSVParser.parse(csvData, CSVFormat.EXCEL)) {

            List<CSVRecord> csvRecordList = csvParser.getRecords();

            for (int record_i = 1; record_i < csvRecordList.size(); record_i++) {
                CSVRecord csvRecord = csvRecordList.get(record_i);

                Long id = Long.parseLong(csvRecord.get(0).trim());
                String city = csvRecord.get(1).trim();
                String sector = csvRecord.get(2).trim();
                int unitPrice = Integer.parseInt(csvRecord.get(6).trim());

                Price price = new Price(id, city, sector, unitPrice);
                prices.add(price);
            }

        } catch (Exception e) {
            log.error("error:{}", e.getMessage());
        }

        return prices;
    }

    public static InputStream readFromCsv(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            log.error("error:{}", e.getMessage());
            return null;
        }
    }

    public static List<Price> readFromJson(String filePath) {
        File JsonFile = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(JsonFile, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    public int getBillTotal(int usage){
        return unitPrice * usage;
    }

    @Override
    public String toString(){
        return String.format("Price(id=%d, city=%s, sector=%s, unitPrice=%d)", id, city, sector, unitPrice);
    }
}
