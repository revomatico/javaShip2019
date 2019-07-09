package com.revomatico.internship2019.demo1.controller;

import io.vavr.collection.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @RequestMapping("/")
  public String home() {
    return displayEvents(readEvents());
  }

  private String displayEvents(List<List<String>> rows) {
    String displayedRows = rows.map(row -> row(row.get(0), row.get(1)+"bla")).mkString();
    String table = tag("table", tag("tr", tag("th", "name") + tag("th", "date")) + displayedRows);
    return "Events:<br/>" + table;
  }

  private List<List<String>> readEvents() {
    List<List<String>> rows = List.of(List.of("concert rock", "2019-07-09 18:00"), List.of("concert rock2", "2019-07-09 18:00"),
        List.of("concert rock3", "2019-07-09 18:00"), List.of("concert rock4", "2019-07-09 18:00"),
        List.of("concert rock5", "2019-07-09 18:00"));
    return rows;
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
