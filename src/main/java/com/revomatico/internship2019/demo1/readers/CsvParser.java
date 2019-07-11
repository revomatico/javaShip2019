package com.revomatico.internship2019.demo1.readers;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.opencsv.CSVReader;
import io.vavr.collection.List;

public class CsvParser {
    private static final String Path = "src\\main\\resources\\events.csv";
    
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
    

    public static List<List<String>> ReadCsv() throws IOException {
    	List<List<String>> rows = List.empty();
    	Map<String,String> restrictedKeywords = initRestrictedKeywords();
       
        try (
            Reader reader = Files.newBufferedReader(Paths.get(Path));
            CSVReader csvReader = new CSVReader(reader);
        ) {csvReader.readNext();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String firstRecord = filterKeyWord(nextRecord[0],restrictedKeywords);
                rows = rows.append(List.of(firstRecord, nextRecord[1]));
            }
        	
            return rows;
        }
        
    }
}