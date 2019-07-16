package com.revomatico.internship2019.demo1.readers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import io.vavr.collection.List;

public class CsvParser {
    private final String path;
    public CsvParser(String path){
      this.path = path;
    }
    
    public static Map<String,String> initRestrictedKeywords(){
    	Map<String,String> restrictedKeywords = new HashMap<>();
    	restrictedKeywords.put("\"","");
    	restrictedKeywords.put(","," ");
    	
    	return restrictedKeywords;
    }
    
    public static String filterKeyWord(String token,Map<String,String> restrictedKeys) {
	
    	for(Map.Entry<String, String> element : restrictedKeys.entrySet()) {
    		if(token.contains(element.getKey())) {
    			token = token.replace(element.getKey(), element.getValue());
    		}
    	}
    	
    	return token;
    }
    

    public List<List<String>> readCsv() throws IOException {
    	List<List<String>> rows = List.empty();
    	Map<String,String> restrictedKeywords = initRestrictedKeywords();
       
        try (
            Reader reader = Files.newBufferedReader(Paths.get(path));
            CSVReader csvReader = new CSVReader(reader);
        ) {
            String[] nextRecord = csvReader.readNext();
            if(!nextRecord[0].matches("name") && !nextRecord[1].matches("date")) {
            	String firstRecord = filterKeyWord(nextRecord[0],restrictedKeywords);
                rows = rows.append(List.of(firstRecord, nextRecord[1]));
            }
            while ((nextRecord = csvReader.readNext()) != null) {
                String firstRecord = filterKeyWord(nextRecord[0],restrictedKeywords);
                rows = rows.append(List.of(firstRecord, nextRecord[1]));
            }
        	
            return rows;
        }
        
    }

    public void writeCsv(List<List<String>> events) {
    //      CSVWriter csv = new CSVWriter(reader);
    	    // first create file object for file placed at location 
    	    // specified by filepath 
    	    File file = new File(path); 
    	    try { 
    	        // create FileWriter object with file as parameter 
    	        FileWriter outputfile = new FileWriter(path); 
    	  
    	        // create CSVWriter object filewriter object as parameter 
    	        CSVWriter writer = new CSVWriter(outputfile); 
    	  
    	        // add data to csv
    	        
    	        int aux = events.size();
    	        int i = 0;
    	        String[] data1 = new String[2];
    	        while(i < aux ) {
    	        	data1[0] = events.get(i).get(0);
        	        data1[1] = events.get(i).get(1);
        	        writer.writeNext(data1);
    	        	i++;
    	        }

    	        // closing writer connection 
    	        writer.close(); 
    	    } 
    	    catch (IOException e) { 
    	        // TODO Auto-generated catch block 
    	        e.printStackTrace(); 
    	    } 
    	} 
}