package com.heo.slacktohtml.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.heo.slacktohtml.service.FileUploadService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/fileupload")
@Controller
public class FileUploadController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") List<MultipartFile> files, Model model) throws IOException {
        List<Object> list;

        for (MultipartFile file : files) {
            String content = fileUploadService.fileToString(file);

            list = fileUploadService.stringToList(content);

            model.addAttribute("list" + files.indexOf(file), list);
        }

        return "result/result1";
    }

    @Value("${FILE_UPLOAD_PATH}")
    String FILE_UPLOAD_PATH;

    @PostMapping("/uploadzip")
    public String zipFileUpload(@RequestParam("file") MultipartFile mfile) {
        try {
            // save file to temp
            // File zip = File.createTempFile(UUID.randomUUID().toString(), "temp");
            // FileOutputStream o = new FileOutputStream(zip);
            // IOUtils.copy(file.getInputStream(), o);
            // o.close();

            // unzip file from temp by zip4j
            String destination = "D:\\destination";
            //ZipFile zipFile = new ZipFile(zip);

            File file = new File(FILE_UPLOAD_PATH + mfile.getOriginalFilename());
            mfile.transferTo(file);


            ZipFile zipFile = new ZipFile(file);
            Enumeration<? extends ZipEntry> list = zipFile.entries();
            

            while(list.hasMoreElements()){
                ZipEntry zip2 = list.nextElement();

                if(zip2.isDirectory()){
                    System.out.println(zip2.getName());
                }else{
                    System.out.println("\t[파일]" + zip2.getName());
                }
                

                if(!zip2.isDirectory()){
                    InputStream stream = zipFile.getInputStream(zip2);
                
                    String str = fileUploadService.byteToString(stream.readAllBytes());

                    // System.out.println("\t\t content :: " + str);

                    stream.close();
                }
            }

            if(file.delete()){
                System.out.println("파일 삭제 성공");
            }else{
                System.out.println("파일 삭제 실패");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }
    
}