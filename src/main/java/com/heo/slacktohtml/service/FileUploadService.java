package com.heo.slacktohtml.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public String fileToString(MultipartFile file) {
        String string = "";

        try {
            string = new String(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }

    public String byteToString(byte[] byts) {
        String string = new String(byts);

        return string;
    }

    public List<Object> stringToList(String string) {
        List<Object> list;

        JsonParser jp = JsonParserFactory.getJsonParser();
        list = jp.parseList(string);

        return list;
    }

    @Value("${FILE_UPLOAD_PATH}")
    String FILE_UPLOAD_PATH;

    public File multipartToFile(MultipartFile mfile) {
        File file = new File(FILE_UPLOAD_PATH + mfile.getOriginalFilename());
        
        try {
            mfile.transferTo(file);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return file;
    }

    public List<Map<String,Object>> readContent(ZipFile zipFile){
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();

        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        
        while(entries.hasMoreElements()){
            Map<String, Object> item = new HashMap<String, Object>();
            ZipEntry zipEntry = entries.nextElement();

            String fileName = zipEntry.getName();
            String[] arr = fileName.split("/");


            int idx = fileName.lastIndexOf("/");

            if(!zipEntry.isDirectory()){
                item.put("isDirectory", false);

                
                // String channel = fileName.substring(0, idx);
                // String date = fileName.substring(idx + 1);
                // logger.debug("channel : date :: " + channel + " : " + date);

                String content;
                try {
                    InputStream inStream = zipFile.getInputStream(zipEntry);
                    content = byteToString(inStream.readAllBytes());

                    item.put("content", content);
                    logger.debug("fileName :: " + content);

                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                item.put("isDirectory", true);    
            }
            item.put("name", fileName);

            list.add(item);
        }

        return list;
    }
}