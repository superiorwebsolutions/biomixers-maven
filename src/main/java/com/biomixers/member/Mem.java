package com.biomixers.member;

import javax.persistence.*;


//@Table(name = "members")
@Entity
public class Mem {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name2")
    private String first_name;

    public Mem(){

    }

    public Mem(int id, String first_name) {
        super();
        this.id = id;
        this.first_name = first_name;
    }
}
