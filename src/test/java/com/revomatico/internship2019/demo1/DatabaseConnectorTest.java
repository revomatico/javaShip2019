package com.revomatico.internship2019.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.revomatico.internship2019.demo1.connectors.DatabaseConnector;
import com.revomatico.internship2019.demo1.connectors.EventRepository;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class DatabaseConnectorTest {
  @Autowired
  EventRepository repo;
  @Test
  void addEventsToRepository() {
    assertNotNull(repo);
    resetTestBeforeWrite();
    EventsConnector repository = new DatabaseConnector();
    int events = repository.readEvents().size();
    repository.addEvent(new Event("concert", "2020"));
    assertEquals(events + 1, repository.readEvents().size());
  }

  private void resetTestBeforeWrite() {
  }

}
