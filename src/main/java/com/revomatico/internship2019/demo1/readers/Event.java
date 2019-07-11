package com.revomatico.internship2019.demo1.readers;

import io.vavr.collection.List;

public class Event {
  public final String name;
  public final String date;
  public final List<String> details;

  public Event(String name, String date, List<String> details) {
    this.name = name;
    this.date = date;
    this.details = details;
  }

  public Event(String name, String date) {
    this(name,date,List.empty());
  }
}
