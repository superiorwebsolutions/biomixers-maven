package com.biomixers;

import com.biomixers.member.Member;
import com.biomixers.member.MemberDAORepository;
import com.biomixers.member.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class MemberDaoServiceCommandLineRunner implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(MemberDaoServiceCommandLineRunner.class);

    @Autowired
    private MemberDAORepository memberDAORepository;

    public void run(String... arg0) throws Exception {
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});
        List<Integer> list = Arrays.asList(33, 45, 66);

        //Member member = new Member(11, "Dan Bernstein", "dan@swssupport.com", null, null, null, 0);
        //Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, new String[]{"American", "Mexican"}, availability, 0);
        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, null, 0);
        //long insert = memberDAORepository.insert(member);
        //log.info("New member is created: " + member);
    }
}
