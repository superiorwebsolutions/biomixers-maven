package com.biomixers.event;

import com.biomixers.member.Member;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class FinalEventCollection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_config_id")
    private Integer eventConfigId;

    private int totalPmc;

    private int numUnplacedInt;

    private int numPlacedInt;

    @Transient
    private List<Member> membersPlacedList;

    @Transient
    private String htmlPlaced;

    private int maxActiveConfigs;

    private int maxMembersAllowedPerRestaurant;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "final_event_config_id", referencedColumnName = "event_config_id")
//    @Transient
    Map<Integer, Event> configTree = new HashMap<>();



    public FinalEventCollection(){

    }

    public FinalEventCollection(int totalPmc, int numUnplacedInt, int numPlacedInt, List<Member> membersPlacedList, String htmlPlaced, int maxActiveConfigs, int maxMembersAllowedPerRestaurant) {
        super();
        this.totalPmc = totalPmc;
        this.numUnplacedInt = numUnplacedInt;
        this.numPlacedInt = numPlacedInt;
        this.membersPlacedList = membersPlacedList;
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

    public int getNumPlacedInt() {
        return numPlacedInt;
    }

    public void setNumPlacedInt(int numPlacedInt) {
        this.numPlacedInt = numPlacedInt;
    }

    public List<Member> getMembersPlacedList() {
        return membersPlacedList;
    }

    public void setMembersPlacedList(List<Member> membersPlacedList) {
        this.membersPlacedList = membersPlacedList;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalEventCollection that = (FinalEventCollection) o;
        return totalPmc == that.totalPmc &&
                numUnplacedInt == that.numUnplacedInt &&
                numPlacedInt == that.numUnplacedInt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalPmc, numUnplacedInt, numPlacedInt, htmlPlaced);
    }

    @Override
    public String toString() {
        return "FinalEventCollection{" +
                "totalPmc=" + totalPmc +
                ", numUnplacedInt=" + numUnplacedInt +
                ", numPlacedInt=" + numPlacedInt +
                ", membersPlacedList=" + membersPlacedList +
                ", htmlPlaced='" + htmlPlaced + '\'' +
                ", maxActiveConfigs=" + maxActiveConfigs +
                ", maxMembersAllowedPerRestaurant=" + maxMembersAllowedPerRestaurant +
                ", configTree=" + configTree +
                '}';
    }
}
