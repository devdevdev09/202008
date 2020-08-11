package com.heo.slacktohtml.vo;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("slack.parse.filename")
public class ParseList {
    List<String> filenames;
}