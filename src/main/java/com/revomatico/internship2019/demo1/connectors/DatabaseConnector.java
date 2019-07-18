package com.revomatico.internship2019.demo1.connectors;

import javax.annotation.PostConstruct;

import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class DatabaseConnector implements EventsConnector {
  private EventRepository repo;

  public DatabaseConnector(@Autowired EventRepository repo) {
    this.repo = repo;
  }

  @PostConstruct
  public void init() {
    repo.saveAll(List.of(new Event("concert1", "data1"), new Event("concert2", "data2")));
  }

  @Override
  public List<List<String>> readEvents() {
    Iterable<Event> events = repo.findAll();
    return List.of(List.of("name", "date")).appendAll(Iterator.ofAll(events).map(x -> List.of(x.name, x.date))).toList();
  }

}
