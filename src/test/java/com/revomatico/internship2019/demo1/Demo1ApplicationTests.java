package com.revomatico.internship2019.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Preconditions;
import com.revomatico.internship2019.demo1.connectors.google.GoogleCalendarEventsConnector;
import com.revomatico.internship2019.demo1.readers.DanutzEventsReader;
import com.revomatico.internship2019.demo1.readers.DanutzJsonReader;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import com.revomatico.internship2019.demo1.readers.EventsRepository;
import com.revomatico.internship2019.demo1.readers.SimoEventsConnector;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Demo1ApplicationTests {
	// TODO parametrized tests

	private static final String TARGET_EVENTS_CSV = "target//events.csv";
	private static final String TARGET_EVENTS_JSON="https://danutz99.github.io/json/db.json";

	@Test
	void allEventsAreReadFromFileSimo() {
		allEventsAreRead(new SimoEventsConnector(TARGET_EVENTS_CSV));
	}

	@Test
	void allEventsAreReadFromFileDanutz() {
		allEventsAreRead(new DanutzEventsReader(TARGET_EVENTS_CSV));
	}

	private void allEventsAreRead(EventsConnector reader) {
		resetTestBeforeWrite();
		assertEquals(8, reader.readEvents().size());
		assertEquals("List(concert rock 1, 2019-07-09 18:00, singers2)", reader.readEvents().head().toString());
		assertEquals("List(a, b, singers2)", reader.readEvents().last().toString());
	}
	private void allEventsAreReadJson(EventsConnector reader) {
		assertEquals(4,reader.readEvents().size());
		assertEquals("List(Concert Rock, 12.08.2019, Eminem)", reader.readEvents().head().toString());
		assertEquals("List(Concert Classic, 29.12.2019, Alan Walker)", reader.readEvents().last().toString());
	}

	@Test
	void addEventsWorksByWritingInFile() {
		resetTestBeforeWrite();
		EventsConnector reader = new DanutzEventsReader(TARGET_EVENTS_CSV);
		int events = reader.readEvents().size();
		assertEquals(8, reader.readEvents().size());
		reader.addEvent(new Event("concert", "2020", "info"));
		assertEquals(events + 1, reader.readEvents().size());
	}

	@Test
	void addEventsToRepository() {
		resetTestBeforeWrite();
		EventsRepository repository = new EventsRepository(new SimoEventsConnector(TARGET_EVENTS_CSV));
		int events = repository.readEvents().size();
		repository.addEvent(new Event("concert", "2020", "info"));
		assertEquals(events + 1, repository.readEvents().size());
	}

	@Test
	void initiallyReadEventsInRepositoryFromFile() {
		test1(8, new DanutzEventsReader(TARGET_EVENTS_CSV));
	}

	@Test
	void initiallyReadEventsInRepositoryFromFile2() {
		test1(8, new SimoEventsConnector(TARGET_EVENTS_CSV));
	}

	@Test
	void testCalendarFromGoogle() {
		test1(10, new GoogleCalendarEventsConnector("no secret"));
	}
	private void test1(int expectedEvents, EventsConnector eventsReader) {
		resetTestBeforeWrite();
		EventsRepository repository = new EventsRepository(eventsReader);
		assertEquals(expectedEvents, repository.readEvents().size());
	}

	private void resetTestBeforeWrite() {
		try {
			final File srcFile = new File("src/main/resources/events.csv");
			final File destFile = new File(TARGET_EVENTS_CSV);
			FileUtils.copyFile(srcFile, destFile);
			Assert.assertEquals(srcFile.length(), destFile.length());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Test
	void allEventsAreReadFromDanutzJsonReader() {
		allEventsAreReadJson(new DanutzJsonReader(TARGET_EVENTS_JSON));
	}
	
	
}
