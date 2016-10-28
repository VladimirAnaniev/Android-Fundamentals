package com.example.vladimir.homework;

/**
 * Created by Vladimir on 29-Sep-16.
 */
public class Item {
    private String mainText;
    private String subText;

    public Item(String mainText, String subText) {
        this.mainText = mainText;
        this.subText = subText;
    }

    public Item() {
    }

    public String getMainText() {
        return mainText;
    }

    public String getSubText() {
        return subText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }
}
