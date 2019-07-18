package com.revomatico.internship2019.demo1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.revomatico.internship2019.demo1.connectors.DatabaseConnector;
import com.revomatico.internship2019.demo1.connectors.EventRepository;
import com.revomatico.internship2019.demo1.controller.HomeController;
import com.revomatico.internship2019.demo1.readers.Event;
import com.revomatico.internship2019.demo1.readers.EventsConnector;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Tags({ @Tag("slow"), @Tag("spring"), @Tag("integration") })
public class HomeControllerTest {
  @Autowired
  public HomeController controller;

  @Test
  void addEventsToRepository() {
    assertNotNull(controller);
    assertEquals("<link rel=\"stylesheet\" href=\"//cdn.rawgit.com/milligram/milligram/master/dist/milligram.min.css\">Events:<br/><table><tr><th>name</th><th>date</th></tr><tr><td>concert_strauss</td><td>data1</td></tr><tr><td>concert2</td><td>data2</td></tr></table>",controller.home());
  }
}
