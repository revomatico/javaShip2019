package com.revomatico.internship2019.demo1.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.vavr.collection.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revomatico.internship2019.demo1.CsvParser;

@RestController
public class HomeController {
  @RequestMapping("/")
  public String home() throws FileNotFoundException {
    return displayEvents(readEvents());
  }

  private String displayEvents(List<List<String>> rows) {
    String displayedRows = rows.map(row -> row(row.get(0), row.get(1))).mkString();
    String table = tag("table", tag("tr", tag("th", "name") + tag("th", "date")) + displayedRows);
    return "Events:<br/>" + table;
  }

 private List<List<String>> readEvents() throws FileNotFoundException {
   List<List<String>> rows = List.empty();
   try {
	rows = CsvParser.ReadCsv();

} catch (IOException e) {
	e.printStackTrace();
}
//  try (Scanner sc = new Scanner(new File("src\\main\\resources\\events.csv"))) {
//   while (sc.hasNext()) {
//      String line = sc.nextLine();
//       
//       String[] vals = line.split(",");
//       if(vals[0].startsWith("\"")) {
//       	line = line.replace("\"", "");
//       	vals = line.split(",");
//           rows = rows.append(List.of(vals[0] + " " +vals[1], vals[2]));
//       }else {
//       	vals = line.split(",");
//            rows = rows.append(List.of(vals[0] ,vals[1] ));
//        }
//
// 
//     }
//   }
   
   return rows;
    // List<List<String>> rows = List.of(List.of("concert rock", "2019-07-09 18:00"), List.of("concert rock2",
    // "2019-07-09 18:00"),
    // List.of("concert rock3", "2019-07-09 18:00"), List.of("concert rock4", "2019-07-09 18:00"),
    // List.of("concert rock5", "2019-07-09 18:00"));
    // return rows;
  }

  private String tag(String string, String result) {
    return tagAsHtml(string, result);
  }

  private String tagAsHtml(String tagName, String result) {
    return "<" + tagName + ">" + result + "</" + tagName + ">";
  }

  private String tagAsJson(String tagName, String result) {
    return "{\"" + tagName + "\":\"" + result + "\"}";
  }

  private String row(String name, String date) {
    return tag("tr", tag("td", name) + tag("td", date));
  }
}
