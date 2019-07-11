package com.revomatico.internship2019.demo1.readers;

import io.vavr.collection.List;
import io.vavr.collection.Traversable;

public class EventsRepository {
  private List<Event> events = List.empty();
  private EventsReader eventsReader;

  public EventsRepository(EventsReader eventsReader) {
    this.eventsReader = eventsReader;
    events = eventsReader.readEvents().map(x/* :List<String> */ -> new Event(x.get(0), x.get(1), x));
  }

  public List<Event> readEvents() {
    return events;
  }

  public void addEvent(Event event) {
    events = events.append(event);
    // eventsReader.addEvent(event);
  }
}
