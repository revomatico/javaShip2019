package com.revomatico.internship2019.demo1.readers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import io.vavr.collection.List;

import org.json.JSONArray;
import org.json.JSONObject;
public class JsonParser {
	   private String path;
public JsonParser(String path) {
	this.path=path;
}
	public List<List<String>> readJsonEvents() {
		List<List<String>> rows=List.empty();
	     try {
		     URL obj = new URL(path);
		     HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		     con.setRequestMethod("GET");
		     con.setRequestProperty("User-Agent", "Mozilla/5.0");
		     int responseCode = con.getResponseCode();
		     BufferedReader in = new BufferedReader(
		             new InputStreamReader(con.getInputStream()));
		     String inputLine;
		     StringBuffer response = new StringBuffer();
		     while ((inputLine = in.readLine()) != null) {
		     	response.append(inputLine);
		     }
		     in.close();
		     JSONObject myResponse = new JSONObject(response.toString());
		     JSONObject events_object = new JSONObject(myResponse.getJSONObject("events").toString());

		     JSONArray Obj_JSONArray1=events_object.getJSONArray("name");
		     JSONArray Obj_JSONArray2=events_object.getJSONArray("date");
		     for(int i=0;i<Obj_JSONArray1.length();i++) {
		     rows=rows.append(List.of(Obj_JSONArray1.getString(i),Obj_JSONArray2.getString(i)));
		     }
	     }catch(Exception e) {
	    	 e.printStackTrace();
	     }

	     return rows;
	}
}