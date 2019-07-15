package com.revomatico.internship2019.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.revomatico.internship2019.demo1.readers.DanutzEventsReader;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsReader;
import com.revomatico.internship2019.demo1.readers.EventsRepository;
import com.revomatico.internship2019.demo1.readers.SimoEventsReader;
import com.revomatico.internship2019.demo1.readers.StefanEventsReader;
import org.junit.jupiter.api.Test;

class Demo1ApplicationTests {

  @Test
  void allEventsAreReadFromFileSimo() {
    allEventsAreRead(new SimoEventsReader());
  }

//  @Test
//  void allEventsAreReadFromFileStefan() {
//    allEventsAreRead(new StefanEventsReader());
//  }

  @Test
  void allEventsAreReadFromFileDanutz() {
    allEventsAreRead(new DanutzEventsReader());
  }

  private void allEventsAreRead(EventsReader reader) {
    assertEquals(6, reader.readEvents().size());
    assertEquals(
        "List(List(concert rock 1, 2019-07-09 18:00), List(concert rock 2, 2019-07-09 18:00), List(concert,,, pop,,, 3, 2019-07-09 18:00), List(concert\",,roc\"\"k 4, 2019-07-09 19:00), List(concert rock 5, 2019-07-09 18:00), List(concert,roc\"k 4, 2019-07-09 18:00))",
        reader.readEvents().toString());
  }

  @Test
  void addEventsWorksByWritingInFile() {
    EventsReader reader = new SimoEventsReader();
    int events = reader.readEvents().size();
    System.out.println(events);
    reader.addEvent(new Event("concert", "2020"));
    assertEquals(events + 1, reader.readEvents().size());
  }

  @Test
  void addEventsToRepository() {
    EventsRepository repository = new EventsRepository(new SimoEventsReader());
    int events = repository.readEvents().size();
    repository.addEvent(new Event("concert", "2020"));
    assertEquals(events + 1, repository.readEvents().size());
  }

  @Test
  void initiallyReadEventsInRepositoryFromFile() {
    test1(new DanutzEventsReader());
  }

  @Test
  void initiallyReadEventsInRepositoryFromFile2() {
    test1(new SimoEventsReader());
  }

  private void test1(EventsReader eventsReader) {
    EventsRepository repository = new EventsRepository(eventsReader);
    assertEquals(6, repository.readEvents().size());
  }
}
