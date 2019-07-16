package com.revomatico.internship2019.demo1.readers;

import java.io.IOException;

import io.vavr.collection.List;

public class DanutzEventsReader implements EventsConnector {
  private String path;

  public DanutzEventsReader(String path) {
    this.path = path;
  }

  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();
    try {
      rows = new CsvParser(path).readCsv();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return rows;
  }

  @Override
  public void addEvent(Event event) {
    List<List<String>> events = readEvents();
    events = events.append(List.of(event.name,event.date));
    new CsvParser(path).writeCsv(events);
  }
}
