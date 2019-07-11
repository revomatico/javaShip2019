package com.revomatico.internship2019.demo1.controller;

import java.io.FileNotFoundException;
import java.time.ZonedDateTime;

import com.revomatico.internship2019.demo1.readers.DanutzEventsReader;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsReader;
import com.revomatico.internship2019.demo1.readers.EventsRepository;
import com.revomatico.internship2019.demo1.readers.StefanEventsReader;
import io.vavr.collection.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  EventsRepository repo = new EventsRepository(new DanutzEventsReader());
  // new DanutzEventsReader();
  // new ManualEventsReader();
  // new SimoEventsReader();
  //new StefanEventsReader();
  
  @RequestMapping("/")
  public String home() throws Exception {
    return displayEvents(readEvents());
  }
  @RequestMapping("/add")
  public String add(@RequestParam String name) throws Exception {
    repo.addEvent(new Event(name,ZonedDateTime.now().toString()));
    return home();
  }

  private String displayEvents(List<List<String>> rows) {
    String displayedRows = rows.map(row -> row(row.get(0), row.get(1))).mkString();
    String table = tag("table", tag("tr", tag("th", "name") + tag("th", "date")) + displayedRows);
    return "Events:<br/>" + table;
  }

  private List<List<String>> readEvents() throws FileNotFoundException {

    return repo.readEvents().map(x/*:Event*/->List.of(x.name,x.date));
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
