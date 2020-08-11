package com.heo.slacktohtml.vo;

import java.util.List;

import lombok.Data;

@Data
public class Channel {
    String name;
    String topic;
    List<String> members;
}