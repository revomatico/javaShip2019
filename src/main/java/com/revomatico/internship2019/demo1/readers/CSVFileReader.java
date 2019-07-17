package com.revomatico.internship2019.demo1.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CSVFileReader {
	public static void main(String[] args) throws IOException {

        URL url = new URL("http://gist.githubusercontent.com/yonbergman/7a0b05d6420dada16b92885780567e60/raw/114aa2ffb1c680174f9757431e672b5df53237eb/data.csv");
        URLConnection connection = url.openConnection();

        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        //BufferedReader buffer = null;
        String line = "";
        String csvSplitBy = ",";

        try(BufferedReader buffer = new BufferedReader(input);) {
            while ((line = buffer.readLine()) != null) {
                String[] event = line.split(csvSplitBy);
                System.out.println(line);
                //System.out.println("event [name =" + event[0] + " , date =" + event[1]);
            }
        } catch (FileNotFoundException fnfe) {
           fnfe.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
           
        }

    }

