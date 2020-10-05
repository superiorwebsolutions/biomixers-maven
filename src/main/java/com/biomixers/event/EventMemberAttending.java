package com.biomixers.event;

import com.biomixers.member.Member;

public class EventMemberAttending {
    int userId;
    Member memberData;
    int pmc;

    public EventMemberAttending(){

    }

    public EventMemberAttending(int userId, Member memberData, int pmc) {
        super();
        this.userId = userId;
        this.memberData = memberData;
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

    public int getPmc() {
        return pmc;
    }

    public void setPmc(int pmc) {
        this.pmc = pmc;
    }

    @Override
    public String toString() {
        return "EventMemberAttending{" +
                "userId=" + userId +
                ", memberData=" + memberData +
                ", pmc=" + pmc +
                '}';
    }
}
