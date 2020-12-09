package com.biomixers.member;

import com.biomixers.BiomixersApplication;
import com.biomixers.util.HelperFunctions;
import com.biomixers.filter.SearchFilterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

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

        List<Member> members = new ArrayList<>();


        memberRepository.findAll()
                .forEach(members::add);

        return members;
    }

    public Member addMember() {
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});

        Set<Integer> list = new HashSet<Integer>(Arrays.asList(33, 45, 66));

        //Member member = new Member(11, "Dan Bernstein", "dan@swssupport.com", null, null, null, 0);
        //Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, new String[]{"American", "Mexican"}, availability, 0);
        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, null, 0);

        memberRepository.save(member);

        return member;
    }


}


