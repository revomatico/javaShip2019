package com.revomatico.internship2019.demo1.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.revomatico.internship2019.demo1.controller.WrappedException;
import io.vavr.collection.List;

public class ManualEventsReader implements EventsReader {

  @Override
  public List<List<String>> readEvents() {
    List<List<String>> rows = List.empty();
    try (Scanner sc = new Scanner(new File("src\\main\\resources\\events.csv"))) {
      while (sc.hasNext()) {
        String line = sc.nextLine();

        String[] vals = line.split(",");
        if (vals[0].startsWith("\"")) {
          line = line.replace("\"", "");
          vals = line.split(",");
          rows = rows.append(List.of(vals[0] + " " + vals[1], vals[2]));
        } else {
          vals = line.split(",");
          rows = rows.append(List.of(vals[0], vals[1]));
        }

      }
    } catch (FileNotFoundException e) {
      throw new WrappedException(e);
    }
    return rows;
//    List<List<String>> rows = List.of(List.of("concert rock", "2019-07-09 18:00"), List.of("concert rock2", "2019-07-09 18:00"),
//        List.of("concert rock3", "2019-07-09 18:00"), List.of("concert rock4", "2019-07-09 18:00"), List.of("concert rock5", "2019-07-09 18:00"));
//    return rows;
  }

}
