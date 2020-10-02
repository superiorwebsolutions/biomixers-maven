package com.biomixers.member;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MemberService {

    private static List<Member> members_list = new ArrayList<Member>();

    static {
        /*
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});

        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", Arrays.asList(33, 45, 66), new String[]{"American", "Mexican"}, availability, 0);

        //Member member = new Member(new String[]{"American", "Mexican"}, "Dan Bernstein", "dan@swssupport.com", 11, new int[]{33, 45, 66}, availability, 0);
        //Member member = new Member(null, "Dan Bernstein", "dan@swssupport.com", 11, null, null, 0);

        members_list.add(member);
        */


    }


    public List<Member> getAllMembers(){

        /*
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});

        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", Arrays.asList(33, 45, 66), new String[]{"American", "Mexican"}, availability, 0);

        //Member member = new Member(new String[]{"American", "Mexican"}, "Dan Bernstein", "dan@swssupport.com", 11, new int[]{33, 45, 66}, availability, 0);
        //Member member = new Member(null, "Dan Bernstein", "dan@swssupport.com", 11, null, null, 0);

        members_list.add(member);
        return members_list;

         */
        return members_list;
    }


}


