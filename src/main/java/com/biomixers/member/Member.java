package com.biomixers.member;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "members")
//@TypeDefs(@TypeDef(name = "string-array", typeClass = StringArrayType.class))

// TODO:  Should this be serializable???
public class Member implements Serializable {



    @Id
    @Column(name="user_id")
    //@GeneratedValue
    private Integer user_id;

    //@Type( type = "string-array" )
    //@Column(columnDefinition = "text[]")
    @ElementCollection
    @CollectionTable(name ="food_preferences")
    private List<String> food_preferences = new ArrayList<>();


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
    private Map<String, String[]> availability = new HashMap<>();

    @Column(name = "num_active_configs")
    private int numActiveConfigs;

    protected Member(){

    }

    public Member(int user_id, String email, String fullName) {
        super();

        /*
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});
        */


        //Member member = new Member(new String[]{"American", "Mexican"}, "Dan Bernstein", "dan@swssupport.com", 11, new int[]{33, 45, 66}, availability, 0);

        List<Integer> met = Arrays.asList(33, 45, 66);

        List<String> food_preferences = Arrays.asList("American", "Mexican");

        int num_active_configs = 0;

        this.food_preferences = food_preferences;
        this.fullName = fullName;
        this.email = email;
        this.user_id = user_id;
        this.membersMet = met;
        this.availability = null;
        this.numActiveConfigs = num_active_configs;
    }

    public Member(int user_id, String email, String fullName, List<Integer> membersMet, List<String> food_preferences, HashMap availability, int numActiveConfigs) {
        super();

        this.food_preferences = food_preferences;
        this.fullName = fullName;
        this.email = email;
        this.user_id = user_id;
        this.membersMet = membersMet;
        this.availability = availability;
        this.numActiveConfigs = numActiveConfigs;
    }

    public void numActiveConfigsMinusOne() {
        this.numActiveConfigs--;
    }
    public void numActiveConfigsPlusOne() {
        this.numActiveConfigs++;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<String> getFood_preferences() {
        return food_preferences;
    }

    public void setFood_preferences(List<String> food_preferences) {
        this.food_preferences = food_preferences;
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


