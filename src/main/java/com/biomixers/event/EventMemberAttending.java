package com.biomixers.event;

import com.biomixers.member.Member;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class EventMemberAttending implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

    //@OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
//    @OneToOne
//    @JoinColumn(name = "user_id2", referencedColumnName = "id")
    @Transient
    private Member memberData;


    private int pmc;

    public EventMemberAttending(){

    }

    public EventMemberAttending(int userId, Member memberData, int pmc) {
        super();
        this.userId = userId;
        this.memberData = memberData;
        this.pmc = pmc;
    }




    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Member getMemberData() {
        return memberData;
    }

    public void setMemberData(Member memberData) {
        this.memberData = memberData;
    }

    public int getNumActiveConfigs(){
        return this.memberData.getNumActiveConfigs();
    }

    public int getPmc() {
        return pmc;
    }

    public void setPmc(int pmc) {
        this.pmc = pmc;
    }

    public void pmcMinusOne(){ this.pmc -= 1; }



    @Override
    public String toString() {
        return "EventMemberAttending{" +
                "userId=" + userId +
              //  ", memberData=" + memberData +
                ", memberData=" + memberData.getFullName() +
                ", pmc=" + pmc +
                '}';
    }


}
