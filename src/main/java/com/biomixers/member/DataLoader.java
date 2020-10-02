package com.biomixers.member;

import com.biomixers.MemberDaoServiceCommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private MemberRepository memberRepository;

    private static final Logger log =
            LoggerFactory.getLogger(MemberDaoServiceCommandLineRunner.class);

    @Autowired
    public DataLoader(MemberRepository userRepository) {
        this.memberRepository = userRepository;
    }

    public void run(ApplicationArguments args) {

        List<Integer> list = Arrays.asList(33, 45, 66);
        List<String> food_preferences = Arrays.asList("American", "Mexican");

        HashMap<String, String[]> availability = new HashMap<>();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});
        //availability.put("Monday", "Breakfast");

        //Member member = null;
        //member = new Member(new String[]{"American", "Mexican"}, "Dan Bernstein", "dan@swssupport.com", 11, new int[]{33, 45, 66}, availability, 0);
        //member = new Member(2, "TEST", "dan2@swssupport.com");
        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, availability, 0);



        if(member != null){
            memberRepository.save(member);
            log.info("New member is created: " + member);
        }


    }
}
