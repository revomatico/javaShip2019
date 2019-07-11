package com.revomatico.internship2019.demo1.readers;

import io.vavr.collection.List;

public interface EventsReader {
  List<List<String>> readEvents();
}
