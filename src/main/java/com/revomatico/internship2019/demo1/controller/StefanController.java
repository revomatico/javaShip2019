package com.revomatico.internship2019.demo1.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.vavr.collection.List;

@RestController
public class StefanController {
  @RequestMapping("/custom")
  public String home() throws IOException {
    return displayEvents(new StefanEventReader().readEvents());
  }

  private String displayEvents(List<List<String>> rows) {
    String displayedRows = rows.map(row -> row(row.get(0), row.get(1))).mkString();
    String table = tag("table", tag("tr", tag("th", "name") + tag("th", "date")) + displayedRows);
    return "Events custom:<br/>" + table;
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
