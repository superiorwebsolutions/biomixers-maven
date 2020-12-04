package com.biomixers.event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Event implements Serializable, Cloneable {

    @Id
    @Column(name="config_id")
    private Integer configId;



    private int totalPmc;
    private int count;


    // totalNumActiveConfigs is actually the median
    private int medianNumActiveConfigs;
    private String foodType;
    private String dayOfWeek;
    private String timeOfDay;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "event_member_attending_id", referencedColumnName = "config_id")
//    @Transient
    private Map<Integer, EventMemberAttending> membersAttending = new HashMap<>();

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

    public Event clone()
    {
        // Assign the shallow copy to new reference variable t
        try {
            return (Event) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            return null;
        }

    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
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

    public void countMinusOne(){ this.count -= 1; }

    public Map<Integer, EventMemberAttending> getMembersAttending() {
        return membersAttending;
    }

    public void setMembersAttending(HashMap<Integer, EventMemberAttending> membersAttending) {
        this.membersAttending = membersAttending;
    }

    public void removeMemberFromAttending(int userId){
        this.membersAttending.remove(userId);
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

    @Override
    public String toString() {
        return "Event{" +
                "configId=" + configId +
                ", totalPmc=" + totalPmc +
                ", count=" + count +
                ", medianNumActiveConfigs=" + medianNumActiveConfigs +
                ", foodType='" + foodType + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", timeOfDay='" + timeOfDay + '\'' +
                ", membersAttending=" + membersAttending +
                '}';
    }
}
