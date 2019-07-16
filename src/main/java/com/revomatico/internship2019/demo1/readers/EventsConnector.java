package com.revomatico.internship2019.demo1.readers;

import io.vavr.collection.List;

public interface EventsConnector {
  List<List<String>> readEvents();

  default void addEvent(Event event) {
  }
}
