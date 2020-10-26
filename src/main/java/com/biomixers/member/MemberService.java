package com.biomixers.member;

import com.biomixers.BiomixersApplication;
import com.biomixers.util.HelperFunctions;
import com.biomixers.event.EventCollection;
import com.biomixers.filter.SearchFilterQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    private SearchFilterQuery searchFilterQuery;


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

    public List<Member> generateSampleData(){
        SearchFilterQuery searchFilterQuery = BiomixersApplication.getSearchFilterQuery();
        //searchFilterQuery = new SearchFilterQuery();

        // USE http://localhost:8080/filter post to run /members
             /*
    // USE THIS FOR TESTING THE POST
    {
"minAllowedPerRestaurant": 6,
"maxAllowedPerRestaurant": 12,
"numDaysOfAvailability": 3,
"numFoodPreferences": 3,
  "randomizeResults": false
}
     */


        List<Member> members = new ArrayList<>();

        int total_num_members = memberRepository.findAll().size();

        memberRepository.findAll()
                .forEach(members::add);

        Integer allMemberIds[] = new Integer[total_num_members];

        /*
        int count = 0;
        for (Member member: members){
            all_member_ids[count] = member.getUser_id();
            count++;
        }

         */

        for (int i = 0; i < total_num_members; i++){
            allMemberIds[i] = members.get(i).getUser_id();
        }

        for (Member member: members){
            ArrayList<String> avail_options = new ArrayList<>();
            avail_options.add("Breakfast");
            avail_options.add("Lunch");
            avail_options.add("Dinner");

            HashMap<String, String[]> availability = new HashMap<>();

            //availability.put("Monday", new String[]{"Breakfast", "Lunch"});


            long seed;

            // Sets random generator seed, forces consistency in sample data
            if(!searchFilterQuery.isRandomizeResults()) {
                seed = member.getUser_id();
                //rand.setSeed(member.getUser_id());
            }
            else{
                seed = System.currentTimeMillis();
                //rand.setSeed(System.currentTimeMillis());
            }

            // TODO:  Use searchFilterQuery.getNumDaysOfAvailability to figure out how many of these days to include
            availability.put("Monday", HelperFunctions.randomAvailability(avail_options, 2, seed));

//            availability.put("Tuesday", HelperFunctions.randomAvailability(avail_options, 2, seed + 2));

            availability.put("Wednesday", HelperFunctions.randomAvailability(avail_options, 2, seed + 3));

            availability.put("Thursday", HelperFunctions.randomAvailability(avail_options, 2, seed + 4));

            availability.put("Friday", HelperFunctions.randomAvailability(avail_options, 2, seed + 5));

            List<Integer> list1 = HelperFunctions.convertArrayToList(allMemberIds);

            // Choose random sample of 25% of members_met
            ArrayList<Integer> list2 = (ArrayList<Integer>) HelperFunctions.randomSampleInt(list1, (int)(total_num_members * searchFilterQuery.getPercentageOfMembersMet()), seed);

            ArrayList<Integer> members_met = list2;


            // TODO:  Use searchFilterQuery.getNumFoodPreferences to figure out how many of these days to include
            ArrayList<String> food_options = new ArrayList<>();
            food_options.add("American");
            food_options.add("Italian");
            food_options.add("Mexican");
            food_options.add("Indian");
            food_options.add("Chinese");

            int numFoodPreferences = searchFilterQuery.getNumFoodPreferences();
            List<String> food_preferences = HelperFunctions.randomFoodPreferences(food_options, numFoodPreferences, seed);


            member.setAvailability(availability);
            member.setFood_preferences(food_preferences);
            member.setMembersMet(members_met);

            memberRepository.save(member);



        }

        return members;
    }


    public List<Member> getAllMembers(){

        searchFilterQuery = new SearchFilterQuery();

        // USE http://localhost:8080/filter post to run /members
             /*
    // USE THIS FOR TESTING THE POST
    {
"minAllowedPerRestaurant": 6,
"maxAllowedPerRestaurant": 12,
"numDaysOfAvailability": 3,
"numFoodPreferences": 3,
  "randomizeResults": false
}
     */


        List<Member> members = new ArrayList<>();

        int total_num_members = memberRepository.findAll().size();

        memberRepository.findAll()
                .forEach(members::add);

        return members;
    }

    public Member addMember() {
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});

        List<Integer> list = Arrays.asList(33, 45, 66);

        //Member member = new Member(11, "Dan Bernstein", "dan@swssupport.com", null, null, null, 0);
        //Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, new String[]{"American", "Mexican"}, availability, 0);
        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, null, 0);

        memberRepository.save(member);

        return member;
    }


}


