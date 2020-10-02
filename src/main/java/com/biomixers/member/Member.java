package com.biomixers.member;


import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;


import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "members")
//@TypeDefs(@TypeDef(name = "string-array", typeClass = StringArrayType.class))
public class Member {



    @Id
    @Column(name="user_id")
    //@GeneratedValue
    private Integer user_id;

    //@Type( type = "string-array" )
    //@Column(columnDefinition = "text[]")
    @ElementCollection
    @CollectionTable(name ="food_preferences")
    private List<String> food_preferences;


    @Column(name = "full_name")
    private String full_name;

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
    private List<Integer> membersMet;
    //private List<Integer> membersMet;

    //@Column(length=10000)
    //@Type(type = "hashmap")
    //@Convert(converter = HashMapConverter.class)
    @ElementCollection
    @CollectionTable(name ="availability")
    private Map<String, String[]> availability;

    @Column(name = "num_active_configs")
    private int num_active_configs;

    protected Member(){

    }

    public Member(int user_id, String email, String full_name) {
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
        this.full_name = full_name;
        this.email = email;
        this.user_id = user_id;
        this.membersMet = met;
        this.availability = null;
        this.num_active_configs = num_active_configs;
    }

    public Member(int user_id, String email, String full_name, List<Integer> membersMet, List<String> food_preferences, HashMap availability, int num_active_configs) {
        super();

        this.food_preferences = food_preferences;
        this.full_name = full_name;
        this.email = email;
        this.user_id = user_id;
        this.membersMet = membersMet;
        this.availability = availability;
        this.num_active_configs = num_active_configs;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public int getNum_active_configs() {
        return num_active_configs;
    }

    public void setNum_active_configs(int num_active_configs) {
        this.num_active_configs = num_active_configs;
    }

    @Override
    public String toString() {
        return "Member{" +
                "user_id=" + user_id +
                ", food_preferences=" + food_preferences +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", membersMet=" + membersMet +
                ", availability=" + availability +
                ", num_active_configs=" + num_active_configs +
                '}';
    }
}


