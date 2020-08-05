package com.heo.slacktohtml.service;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    
    public String fileToString(MultipartFile file) {
        String string = "";
        
        try {
            string = new String(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }

    public String byteToString(byte[] byts){
        String string = new String(byts);

        return string;
    }
    
    public List<Object> stringToList(String string){
        List<Object> list;

        JsonParser jp = JsonParserFactory.getJsonParser();
        list = jp.parseList(string);        

        return list;
    }
}