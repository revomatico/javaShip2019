package com.revomatico.internship2019.demo1.readers;

import java.io.IOException;
//import com.revomatico.internship2019.demo1.readers.JsonParser;

import io.vavr.collection.List;

public class DanutzJsonReader implements EventsConnector {
private String path;
public DanutzJsonReader(String path) {
	this.path=path;
}
	@Override
	public List<List<String>> readEvents() {
		  List<List<String>> rows = List.empty();
		    try {
		      rows = new JsonParser(path).readJsonEvents();
		    } catch (Exception e) {
		      throw new RuntimeException(e);
		    }
		    return rows;
	}


}
