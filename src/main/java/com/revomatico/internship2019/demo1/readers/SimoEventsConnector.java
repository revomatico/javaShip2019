package com.revomatico.internship2019.demo1.readers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Preconditions;
import com.opencsv.CSVReader;
import com.revomatico.internship2019.demo1.controller.WrappedException;
import io.vavr.collection.List;

public class SimoEventsConnector implements EventsConnector {
  private String path;

  public SimoEventsConnector(String path) {
    this.path = path;
  }
  @Override
  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();
    try (CSVReader reader = new CSVReader(new FileReader(path), ',');) {
      // Get the CSVReader instance with specifying the delimiter to be used

      String[] nextLine;
      // Read one line at a time
      nextLine = reader.readNext();
      Preconditions.checkArgument(nextLine.length>=3,"File %s has too few columns.",path);
      if(!nextLine[0].matches("name")&& !nextLine[1].matches("date")) {
        List<String> line = List.of(nextLine);
        Preconditions.checkArgument(nextLine.length==line.length());
    	  rows = rows.append(line);
      }
      while ((nextLine = reader.readNext()) != null) {
        Preconditions.checkArgument(nextLine.length>=3);
        List<String> line = List.of(nextLine);
        Preconditions.checkArgument(nextLine.length==line.length());
        rows = rows.append(line);
      }
    } catch (FileNotFoundException e) {
      throw new WrappedException(e);
    } catch (IOException e) {
      throw new WrappedException(e);
    }
    return rows;
  }
  
  @Override
  public void addEvent(Event event) {
    List<List<String>> events = readEvents();
    events = events.append(List.of(event.name,event.date, event.singer).appendAll(event.details));
    new CsvParser(path).writeCsv(events);
  }
}
