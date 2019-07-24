package com.revomatico.internship2019.demo1.readers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
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
            Preconditions.checkArgument(nextRecord.length>2,"This code works for maximum 2 columns. The others are just ignored. We just found a row with more columns: %s. Offending line %s",nextRecord.length, List.of(nextRecord));
            if(!nextRecord[0].matches("name") && !nextRecord[1].matches("date")) {
            	//String firstRecord = filterKeyWord(nextRecord[0],restrictedKeywords);
            	List<String> line = List.of(nextRecord);
                rows = rows.append(line);
            }
            while ((nextRecord = csvReader.readNext()) != null) {
                String firstRecord = filterKeyWord(nextRecord[0],restrictedKeywords);
                //rows = rows.append(List.of(firstRecord, nextRecord[1]));
                nextRecord[0]=nextRecord[0].replace(nextRecord[0],firstRecord);
                List<String> line =List.of(nextRecord);
                rows=rows.append(line);
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
    	        events.map(row->row.toJavaArray(String[]::new)).forEach(row-> writer.writeNext(row));

    	        // closing writer connection 
    	        writer.close(); 
    	    } 
    	    catch (IOException e) { 
    	        // TODO Auto-generated catch block 
    	        e.printStackTrace(); 
    	    } 
    	} 
}