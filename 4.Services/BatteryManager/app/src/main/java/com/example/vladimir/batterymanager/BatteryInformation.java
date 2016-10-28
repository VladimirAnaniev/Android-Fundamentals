package com.example.vladimir.batterymanager;

/**
 * Created by Vladimir on 24-Sep-16.
 */
public class BatteryInformation {
    private String date;
    private String BatteryPct;

    public BatteryInformation(String date, String batteryPct) {
        this.date = date;
        BatteryPct = batteryPct;
    }

    public String getDate() {
        return date;
    }

    public String getBatteryPct() {
        return BatteryPct;
    }
}
