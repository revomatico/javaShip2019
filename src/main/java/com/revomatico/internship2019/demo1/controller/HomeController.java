package com.revomatico.internship2019.demo1.controller;

import java.io.FileNotFoundException;
import java.time.ZonedDateTime;

import javax.annotation.PostConstruct;

import com.google.common.base.Preconditions;
import com.revomatico.internship2019.demo1.readers.DanutzEventsReader;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import com.revomatico.internship2019.demo1.readers.EventsRepository;
import com.revomatico.internship2019.demo1.readers.SimoEventsReader;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @Autowired
  private EventsConnector databaseController;
  private EventsRepository repo;

  @PostConstruct
  public void init() {
    Preconditions.checkNotNull(databaseController);
    repo = new EventsRepository(
        // new AdConnector()
        // new LdapConnector()
        // new DanutzEventsReader("events-webapp.csv")
        databaseController
    // new LdapConnector()
    // new DanutzEventsReader();
    // new ManualEventsReader();
    // new SimoEventsReader()
    // new StefanEventsReader();
    );
  }

  @RequestMapping("/")
  public String home() {
    return displayEvents(readEvents());
  }

  @RequestMapping("/add")
  public String add(@RequestParam String name) {
    repo.addEvent(new Event(name, ZonedDateTime.now().toString()));
    return home();
  }

  private String displayEvents(List<List<String>> rows) {
    String table = tag("table", row(rows.head(), "th") + rows.tail().map(row -> row(row, "td")).mkString());
    String style = "<link rel=\"stylesheet\" href=\"//cdn.rawgit.com/milligram/milligram/master/dist/milligram.min.css\">";
    return style + "Events:<br/>" + table;
  }

  private List<List<String>> readEvents() {
    return repo.readEvents().map(x/* :Event */ -> x.details/* List.of(x.name, x.date).appendAll(x.details) */);
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

  private String row(List<String> row, String cell) {
    return tag("tr", row.map(col -> tag(cell, col)).mkString());
  }
}
