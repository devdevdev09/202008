package com.heo.slacktohtml.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import com.heo.slacktohtml.service.FileUploadService;

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
    public String zipFileUpload(@RequestParam("file") MultipartFile mfile
                                ,Model model) {
        try {
            File file = fileUploadService.multipartToFile(mfile);
            
            ZipFile zipFile = new ZipFile(file);

            List<Map<String, Object>> list = fileUploadService.readContent(zipFile);

            zipFile.close();

            if(file.exists()){
                boolean isDelete = file.delete();
                logger.debug("파일 삭제 " + ((isDelete)? "성공" : "실패"));
            }

            model.addAttribute("result", list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "result/result1";
    }
    
}