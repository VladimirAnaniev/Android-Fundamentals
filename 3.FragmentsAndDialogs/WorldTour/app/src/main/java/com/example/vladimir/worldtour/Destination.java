package com.example.vladimir.worldtour;

/**
 * Created by Vladimir on 14-Sep-16.
 */
public class Destination {
    private String Name;
    private String Language;
    private Capital Capital;
    private String Information;
    private Integer Flag;

    public Destination(String name, String language, Capital capital, String information, Integer flag) {
        Name = name;
        Language = language;
        Capital = capital;
        Information = information;
        Flag = flag;
    }

    public String getName() {
        return Name;
    }

    public String getLanguage() {
        return Language;
    }

    public Capital getCapital() {
        return Capital;
    }

    public String getInformation() {
        return Information;
    }

    public Integer getFlag() {
        return Flag;
    }

    public void setCapital(Capital capital) {
        Capital = capital;
    }
}
