package com.heo.slacktohtml.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.heo.slacktohtml.enums.FileEnums;
import com.heo.slacktohtml.vo.Channel;
import com.heo.slacktohtml.vo.User;

import org.springframework.stereotype.Service;

@Service
public class ParseService {
    public List<Map<String, Object>> getUserInfo(List<Map<String, Object>> jsonList){

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        for (Map<String, Object> map : jsonList) {

            for(String key : map.keySet()){
                boolean isKey = false;

                for (FileEnums filename : FileEnums.values()) {
                    if(key.equals(filename.toString())){
                        isKey = true;
                    }
                }

                if(!isKey){
                    map.remove(key);
                }
            }
            // User usr = new User();
            // usr.setId((String)lhMap.get("id"));
            // usr.setName((String)lhMap.get("name"));
            // usr.setReal_name((String)lhMap.get("real_name"));
            
        }
    
        return jsonList;
    }

    public List<Object> getChannelInfo(List<Object> jsonList){
        List<Object> list = new ArrayList<Object>();

        for(Object object : jsonList){
            LinkedHashMap lhMap = (LinkedHashMap)object;
            Channel channel = new Channel();
            channel.setName((String)lhMap.get("name"));
            channel.setTopic((String)((LinkedHashMap)lhMap.get("topic")).get("value"));
            channel.setMembers((List<String>)lhMap.get("members"));
            list.add(channel);
        }

        return list;
    }
}