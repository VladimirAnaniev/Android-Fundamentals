package com.example.vladimir.worldtour;

/**
 * Created by Vladimir on 14-Sep-16.
 */
public class Capital {
    private String Name;
    private Integer Picture;
    private String Info;

    public Capital(String name, Integer picture, String info) {
        Name = name;
        Picture = picture;
        Info = info;
    }

    public String getName() {
        return Name;
    }

    public Integer getPicture() {
        return Picture;
    }

    public String getInfo() {
        return Info;
    }
}
