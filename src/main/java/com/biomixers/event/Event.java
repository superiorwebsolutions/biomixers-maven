package com.biomixers.event;

import java.io.Serializable;
import java.util.HashMap;

public class Event implements Serializable {
    int configId;
    int totalPmc;
    int count;


    // totalNumActiveConfigs is actually the median
    int medianNumActiveConfigs;
    String foodType;
    String dayOfWeek;
    String timeOfDay;

    HashMap<Integer, EventMemberAttending> membersAttending = new HashMap<>();

    public Event(){

    }

    public Event(int configId, int totalPmc, int count, HashMap<Integer, EventMemberAttending> membersAttending, int medianNumActiveConfigs, String foodType, String dayOfWeek, String timeOfDay) {
        super();
        this.configId = configId;
        this.totalPmc = totalPmc;
        this.count = count;
        this.membersAttending = membersAttending;
        this.medianNumActiveConfigs = medianNumActiveConfigs;
        this.foodType = foodType;
        this.dayOfWeek = dayOfWeek;
        this.timeOfDay = timeOfDay;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public int getTotalPmc() {
        return totalPmc;
    }

    public void setTotalPmc(int totalPmc) {
        this.totalPmc = totalPmc;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<Integer, EventMemberAttending> getMembersAttending() {
        return membersAttending;
    }

    public void setMembersAttending(HashMap<Integer, EventMemberAttending> membersAttending) {
        this.membersAttending = membersAttending;
    }

    public int getMedianNumActiveConfigs() {
        return medianNumActiveConfigs;
    }

    public void setMedianNumActiveConfigs(int medianNumActiveConfigs) {
        this.medianNumActiveConfigs = medianNumActiveConfigs;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getTimeOfDay() {
        return timeOfDay;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

}
