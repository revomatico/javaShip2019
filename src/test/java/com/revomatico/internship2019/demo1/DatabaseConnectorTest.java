package com.revomatico.internship2019.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.revomatico.internship2019.demo1.connectors.DatabaseConnector;
import com.revomatico.internship2019.demo1.connectors.EventRepository;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//for full spring-test: @SpringBootTest @AutoConfigureTestDatabase 
@Tags({ @Tag("slow"), @Tag("spring"), @Tag("integration") })
public class DatabaseConnectorTest {
  @Autowired
  public EventRepository repo;

  @Test
  void addEventsToRepository() {
    assertNotNull(repo);
    resetTestBeforeWrite();
    EventsConnector repository = new DatabaseConnector(repo);
    int events = repository.readEvents().size();
    repository.addEvent(new Event("concert", "2020", "singer"));
    assertEquals(events + 1, repository.readEvents().size());
  }

  private void resetTestBeforeWrite() {
  }

}
