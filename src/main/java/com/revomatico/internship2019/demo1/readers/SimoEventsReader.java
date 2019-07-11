package com.revomatico.internship2019.demo1.readers;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

import io.vavr.collection.List;

public class SimoEventsReader implements EventsReader{

  @Override
	public List<List<String>> readEvents(){

		CSVReader reader = null;
		List<List<String>> rows  = List.empty();
		try
		{
			//Get the CSVReader instance with specifying the delimiter to be used
			reader = new CSVReader(new FileReader("src\\main\\resources\\events.csv"),',');
			String[] nextLine;
			//Read one line at a time
			while ((nextLine = reader.readNext()) != null)
			{
				rows = rows.append(List.of(nextLine[0], nextLine[1]));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return rows;

	}
}
