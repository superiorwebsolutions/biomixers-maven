package com.biomixers.member;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "members")
//@TypeDefs(@TypeDef(name = "string-array", typeClass = StringArrayType.class))

// TODO:  Should this be serializable???
public class Member implements Serializable, Cloneable {



    @Id
    @Column(name="user_id")
    //@GeneratedValue
    private Integer userId;



    //@Type( type = "string-array" )
    //@Column(columnDefinition = "text[]")
    @ElementCollection
    @CollectionTable(name ="food_preferences")
    private List<String> foodPreferences = new ArrayList<>();


    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    //@Type(type = "list-array")
    // TODO:  figure out proper way to determine max column length (array size) for h2 database
    //@Column(length=10000)
    //@Type(type = "int-array")
    //@Column(columnDefinition = "integer[]")
    //@Column(name="members_met", nullable = true)
    @ElementCollection
    @CollectionTable(name ="members_met")
    private List<Integer> membersMet = new ArrayList<>();
    //private List<Integer> membersMet;

    //@Column(length=10000)
    //@Type(type = "hashmap")
    //@Convert(converter = HashMapConverter.class)
    @ElementCollection
    @CollectionTable(name ="availability")
//    @Transient
    private Map<String, String[]> availability = new HashMap<>();

    @Column(name = "num_active_configs")
    private int numActiveConfigs;

    protected Member(){

    }

    public Member(int userId, String email, String fullName) {
        super();


        int num_active_configs = 0;

        this.foodPreferences = null;
        this.fullName = fullName;
        this.email = email;
        this.userId = userId;
        this.membersMet = null;
        this.availability = null;
        this.numActiveConfigs = num_active_configs;
    }

    public Member(int userId, String email, String fullName, List<Integer> membersMet, List<String> foodPreferences, HashMap availability, int numActiveConfigs) {
        super();

        this.foodPreferences = foodPreferences;
        this.fullName = fullName;
        this.email = email;
        this.userId = userId;
        this.membersMet = membersMet;
        this.availability = availability;
        this.numActiveConfigs = numActiveConfigs;
    }

    public Member clone()
    {
        // Assign the shallow copy to new reference variable t
        try {
            return (Member) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            return null;
        }

    }

    public void numActiveConfigsMinusOne() {
        this.numActiveConfigs--;
    }
    public void numActiveConfigsPlusOne() {
        this.numActiveConfigs++;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }

    public List<String> getFoodPreferences() {
        return foodPreferences;
    }

    public void setFoodPreferences(List<String> food_preferences) {
        this.foodPreferences = food_preferences;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        this.fullName = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getMembersMet() {
        return membersMet;
    }

    public void setMembersMet(List<Integer> membersMet) {
        this.membersMet = membersMet;
    }

    public Map<String, String[]> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, String[]> availability) {
        this.availability = availability;
    }

    public int getNumActiveConfigs() {
        return numActiveConfigs;
    }

    public void setNumActiveConfigs(int num_active_configs) {
        this.numActiveConfigs = num_active_configs;
    }

    @Override
    public String toString() {
        return "Member{full_name=" + fullName +
        '}';

        /*
        return "Member{" +
                "user_id=" + user_id +
                ", food_preferences=" + food_preferences +
                ", full_name='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", membersMet=" + membersMet +
                ", availability=" + availability +
                ", num_active_configs=" + numActiveConfigs +
                '}';

         */


    }
}


