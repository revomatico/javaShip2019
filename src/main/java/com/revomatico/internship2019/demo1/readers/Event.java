package com.revomatico.internship2019.demo1.readers;

import javax.persistence.Entity;
import javax.persistence.Id;

import io.vavr.collection.List;

@Entity
public class Event {
  @Id
  public final String name;
  public final String date;
  public final String singer;
  public final List<String> details;

  public Event(String name, String date, String singer, List<String> details) {
    this.name = name;
    this.date = date;
    this.singer = singer;
    this.details = details;
  }

  public Event(String name, String date, String singer) {
    this(name, date, singer, List.empty());
  }

  private Event() {
    this(null, null,null);
  }
}
