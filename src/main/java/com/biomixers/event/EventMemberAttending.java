package com.biomixers.event;

import com.biomixers.member.Member;

public class EventMemberAttending {
    int userId;
    Member memberData;
    String memberName;
    int pmc;

    public EventMemberAttending(){

    }

    public EventMemberAttending(int userId, Member memberData, int pmc) {
        super();
        this.userId = userId;
        this.memberData = memberData;
        //this.memberName = memberData.getFullName();
        this.pmc = pmc;
    }




    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
/*
    @Override
    public String toString() {
        return "EventMemberAttending{" +
                "userId=" + userId +
                ", memberData=" + memberData +
 //               ", memberData=" + memberData.getFullName() +
                ", pmc=" + pmc +
                '}';
    }

 */
}
