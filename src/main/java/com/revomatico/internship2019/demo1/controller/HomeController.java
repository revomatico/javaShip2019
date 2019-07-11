package com.revomatico.internship2019.demo1.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.vavr.collection.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revomatico.internship2019.demo1.readers.CsvParser;
import com.revomatico.internship2019.demo1.readers.DanutzEventsReader;
import com.revomatico.internship2019.demo1.readers.EventsReader;
import com.revomatico.internship2019.demo1.readers.SimoEventsReader;
import com.revomatico.internship2019.demo1.readers.StefanEventsReader;

@RestController
public class HomeController {
  @RequestMapping("/")
  public String home() throws Exception {

    return displayEvents(new SimoEventsReader().readEvents());
  }

  private String displayEvents(List<List<String>> rows) {
    String displayedRows = rows.map(row -> row(row.get(0), row.get(1))).mkString();
    String table = tag("table", tag("tr", tag("th", "name") + tag("th", "date")) + displayedRows);
    return "Events:<br/>" + table;
  }

  private List<List<String>> readEvents() throws FileNotFoundException {
    EventsReader eventReader =
        // new DanutzEventsReader();
        // new ManualEventsReader();
        // new SimoEventsReader();
        new StefanEventsReader();
    return eventReader.readEvents();
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
