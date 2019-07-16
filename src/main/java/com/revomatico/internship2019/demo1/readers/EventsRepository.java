package com.revomatico.internship2019.demo1.readers;

import com.google.common.base.Preconditions;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;

public class EventsRepository {
  private List<Event> events = List.empty();
  private EventsConnector eventsReader;

  public EventsRepository(EventsConnector eventsReader) {
    Preconditions.checkNotNull(eventsReader);
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
