package com.revomatico.internship2019.demo1.readers;

import com.google.common.base.Preconditions;
import io.vavr.Tuple2;
import io.vavr.collection.List;

public interface EventsConnector {
  List<List<String>> readEvents();

  default void addEvent(Event event) {
  }

  default List<List<String>> readEventsAndValidate() {
    return readEvents().zipWithIndex().map((Tuple2<List<String>, Integer> x) -> {
      Preconditions.checkArgument(x._1.size() >= 3, "Connector %s broke the contract. Row %s should have at least 3 elements. Had %s: %s",
          this, x._2, x._1.size(), x._1);
      return x._1;
    });
  }
}
