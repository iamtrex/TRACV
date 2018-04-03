package com.tracv.util;

import java.io.*;

public class TextFileReader {

    public static String readFile(String fileName) {
        String response = "";
        try {
            InputStream in = TextFileReader.class.getResourceAsStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = br.readLine()) != null){
                response += line;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Shouldn't happen");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("Response " + response);
        return response;

    }
}
