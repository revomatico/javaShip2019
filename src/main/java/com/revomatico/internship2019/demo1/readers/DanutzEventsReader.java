package com.revomatico.internship2019.demo1.readers;

import java.io.IOException;

import io.vavr.collection.List;

public class DanutzEventsReader implements EventsReader {

  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();
    try {
      rows = CsvParser.ReadCsv();

    } catch (IOException e) {
      e.printStackTrace();
    }
    return rows;
  }

}
