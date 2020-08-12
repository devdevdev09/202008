package com.heo.slacktohtml.enums;

public enum UserField {
    ID("name"),NAME("name"),REAL_NAME("real_name"),IS_BOT("is_bot");

    private String key;

    UserField(String key){
        this.key = key;
    }

    public String toString(){
        return key;
    }
}