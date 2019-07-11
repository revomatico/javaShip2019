package com.revomatico.internship2019.demo1.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import io.vavr.collection.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class StefanEventReader {

  public List<List<String>> readEvents() throws IOException {
    List<List<String>> rows = List.empty();

    try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\events.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      for (CSVRecord csvRecord : csvParser) {
        rows = rows.append(List.of(csvRecord.get("name"), csvRecord.get("date")));

      }
    }
    return rows;

  }
}
