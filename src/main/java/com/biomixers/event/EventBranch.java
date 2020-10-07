package com.biomixers.event;

import java.util.HashMap;

public class EventBranch {
    int configId;
    int totalPmc;
    int count;
    HashMap<Integer, EventMemberAttending> membersAttending;
    int totalNumActiveConfigs;
    String foodType;
    String dayOfWeek;
    String timeOfDay;

    public EventBranch(){

    }

    public EventBranch(int configId, int totalPmc, int count, HashMap<Integer, EventMemberAttending> membersAttending, int totalNumActiveConfigs, String foodType, String dayOfWeek, String timeOfDay) {
        super();
        this.configId = configId;
        this.totalPmc = totalPmc;
        this.count = count;
        this.membersAttending = membersAttending;
        this.totalNumActiveConfigs = totalNumActiveConfigs;
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

    public int getTotalNumActiveConfigs() {
        return totalNumActiveConfigs;
    }

    public void setTotalNumActiveConfigs(int totalNumActiveConfigs) {
        this.totalNumActiveConfigs = totalNumActiveConfigs;
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

    @Override
    public String toString() {
        return "EventBranch{" +
                "configId=" + configId +
                ", totalPmc=" + totalPmc +
                ", count=" + count +
                ", membersAttending=" + membersAttending +
                ", totalNumActiveConfigs=" + totalNumActiveConfigs +
                ", foodType=" + foodType +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", timeOfDay='" + timeOfDay + '\'' +
                '}';
    }
}
