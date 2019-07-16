package com.revomatico.internship2019.demo1.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.revomatico.internship2019.demo1.controller.WrappedException;
import io.vavr.collection.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class StefanEventsReader implements EventsConnector {

  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();

    try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\events.csv"));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
      for (CSVRecord csvRecord : csvParser) {
        rows = rows.append(List.of(csvRecord.get("name"), csvRecord.get("date")));

      }
    } catch (IOException e) {
      throw new WrappedException(e);
    }
    return rows;

  }
}
