package com.revomatico.internship2019.demo1.readers;

import com.google.common.base.Preconditions;
import io.vavr.collection.List;

public class EventsRepository {
  private List<Event> events = List.empty();
  private EventsConnector connector;


  public EventsRepository(EventsConnector connector) {
    Preconditions.checkNotNull(connector);
    this.connector = connector;
  }

  public List<Event> readEvents() {
    events = connector.readEventsAndValidate().map(x/* :List<String> */ -> new Event(x.get(0), x.get(1), x.get(2), x));
    return events;
  }

  public void addEvent(Event event) {
    events = events.append(event);
    connector.addEvent(event);
  }
}
