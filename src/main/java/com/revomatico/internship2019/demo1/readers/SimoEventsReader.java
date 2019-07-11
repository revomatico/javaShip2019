package com.revomatico.internship2019.demo1.readers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.revomatico.internship2019.demo1.controller.WrappedException;
import io.vavr.collection.List;

public class SimoEventsReader implements EventsReader {

  @Override
  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();
    try (CSVReader reader = new CSVReader(new FileReader("src\\main\\resources\\events.csv"), ',');) {
      // Get the CSVReader instance with specifying the delimiter to be used

      String[] nextLine;
      // Read one line at a time
      while ((nextLine = reader.readNext()) != null) {
        rows = rows.append(List.of(nextLine[0], nextLine[1]));
      }
    } catch (FileNotFoundException e) {
      throw new WrappedException(e);
    } catch (IOException e) {
      throw new WrappedException(e);
    }
    return rows;
  }
}
