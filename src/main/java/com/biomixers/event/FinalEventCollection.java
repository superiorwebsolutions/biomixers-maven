package com.biomixers.event;

import java.util.HashMap;
import java.util.Map;

public class FinalEventCollection {

    private int totalPmc;

    private int numUnplacedInt;

    private String htmlNumUnplaced;

    private String htmlPlaced;

    private int maxActiveConfigs;

    private int maxMembersAllowedPerRestaurant;

    Map<Integer, Event> configTree = new HashMap<>();

    public FinalEventCollection(){

    }

    public FinalEventCollection(int totalPmc, int numUnplacedInt, String htmlNumUnplaced, String htmlPlaced, int maxActiveConfigs, int maxMembersAllowedPerRestaurant) {
        super();
        this.totalPmc = totalPmc;
        this.numUnplacedInt = numUnplacedInt;
        this.htmlNumUnplaced = htmlNumUnplaced;
        this.htmlPlaced = htmlPlaced;
        this.maxActiveConfigs = maxActiveConfigs;
        this.maxMembersAllowedPerRestaurant = maxMembersAllowedPerRestaurant;
    }

    public int getTotalPmc() {
        return totalPmc;
    }

    public void setTotalPmc(int totalPmc) {
        this.totalPmc = totalPmc;
    }

    public int getNumUnplacedInt() {
        return numUnplacedInt;
    }

    public void setNumUnplacedInt(int numUnplacedInt) {
        this.numUnplacedInt = numUnplacedInt;
    }

    public String getHtmlNumUnplaced() {
        return htmlNumUnplaced;
    }

    public void setHtmlNumUnplaced(String htmlNumUnplaced) {
        this.htmlNumUnplaced = htmlNumUnplaced;
    }

    public String getHtmlPlaced() {
        return htmlPlaced;
    }

    public void setHtmlPlaced(String htmlPlaced) {
        this.htmlPlaced = htmlPlaced;
    }

    public Map<Integer, Event> getConfigTree() {
        return configTree;
    }

    public void setConfigTree(Map<Integer, Event> configTree) {
        this.configTree = configTree;
    }

    public int getMaxActiveConfigs() {
        return maxActiveConfigs;
    }

    public void setMaxActiveConfigs(int maxActiveConfigs) {
        this.maxActiveConfigs = maxActiveConfigs;
    }

    public int getMaxMembersAllowedPerRestaurant() {
        return maxMembersAllowedPerRestaurant;
    }

    public void setMaxMembersAllowedPerRestaurant(int maxMembersAllowedPerRestaurant) {
        this.maxMembersAllowedPerRestaurant = maxMembersAllowedPerRestaurant;
    }

    @Override
    public String toString() {
        return "FinalEventCollection{" +
                "totalPmc=" + totalPmc +
                ", numUnplacedInt=" + numUnplacedInt +
                ", htmlNumUnplaced='" + htmlNumUnplaced + '\'' +
                ", htmlPlaced='" + htmlPlaced + '\'' +
                ", maxActiveConfigs=" + maxActiveConfigs +
                ", maxMembersAllowedPerRestaurant=" + maxMembersAllowedPerRestaurant +
                ", configTree=" + configTree +
                '}';
    }
}
