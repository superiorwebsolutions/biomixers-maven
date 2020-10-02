package com.biomixers.member;

import com.biomixers.HelperFunctions;
import com.biomixers.filter.SearchFilterQuery;
import org.hibernate.id.uuid.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serializable;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;


@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    private SearchFilterQuery searchFilterQuery;


    @PostMapping("/filter")
    public ResponseEntity<SearchFilterQuery> filterResults(@RequestBody SearchFilterQuery filter) {

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


        // TODO:  Should I be storing this query somewhere else, and then referencing it in getMembers()?
        searchFilterQuery = filter;
        System.out.println(searchFilterQuery.toString());
        getMembers();


        return new ResponseEntity<SearchFilterQuery>(searchFilterQuery, HttpStatus.OK);
    }

    @GetMapping("/add")
    public void addMember() {
        HashMap availability = new HashMap();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});

        List<Integer> list = Arrays.asList(33, 45, 66);

        //Member member = new Member(11, "Dan Bernstein", "dan@swssupport.com", null, null, null, 0);
        //Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, new String[]{"American", "Mexican"}, availability, 0);
        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, null, 0);

        //memberRepository.save(member);

    }

    @GetMapping("/member/{user_id}")
    public Member getMember(@PathVariable int user_id) {
        return memberRepository.findById(user_id).get();

    }



    @GetMapping("/members")
    public List<Member> getMembers() {

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


        memberService.getAllMembers();
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


            Random rand = new Random();

            // Sets random generator seed, forces consistency in sample data
            //if(!searchFilterQuery.isRandomizeResults()) {
                rand.setSeed(member.getUser_id());
            //}

            // TODO:  Use searchFilterQuery.getNumDaysOfAvailability to figure out how many of these days to include
            availability.put("Monday", HelperFunctions.randomAvailability(avail_options, 2, rand));
            rand.setSeed(member.getUser_id() + 1);

//            availability.put("Tuesday", HelperFunctions.randomAvailability(avail_options, 2, rand));
//            rand.setSeed(member.getUser_id() + 2);

            availability.put("Wednesday", HelperFunctions.randomAvailability(avail_options, 2, rand));
            rand.setSeed(member.getUser_id() + 3);

            availability.put("Thursday", HelperFunctions.randomAvailability(avail_options, 2, rand));
            rand.setSeed(member.getUser_id() + 4);

            availability.put("Friday", HelperFunctions.randomAvailability(avail_options, 2, rand));
            rand.setSeed(member.getUser_id());


            List<Integer> list1 = HelperFunctions.convertArrayToList(allMemberIds);

            // Choose random sample of 25% of members_met
            ArrayList<Integer> list2 = (ArrayList<Integer>) HelperFunctions.randomSampleInt(list1, total_num_members / 4, rand);


            ArrayList<Integer> members_met = list2;




            // TODO:  Use searchFilterQuery.getNumFoodPreferences to figure out how many of these days to include
            //String[] food_options = new String[]{"American", "Italian", "Mexican", "Indian", "Chinese"};
            ArrayList<String> food_options = new ArrayList<>();
            food_options.add("American");
            food_options.add("Italian");
            food_options.add("Mexican");
            food_options.add("Indian");
            food_options.add("Chinese");


            List<String> food_preferences = food_options;
            //List<String> food_preferences = HelperFunctions.randomFoodPreferences(food_options, 3, rand);


            member.setAvailability(availability);
            member.setFood_preferences(food_preferences);
            member.setMembersMet(members_met);


            memberRepository.save(member);



        }

        return members;

    }






    /*
    @GetMapping("/instructors/{username}/courses/{id}")
    public Course getCourse(@PathVariable String username, @PathVariable long id) {
        return courseManagementService.findById(id);
    }

    @DeleteMapping("/instructors/{username}/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String username, @PathVariable long id) {

        Course course = courseManagementService.deleteById(id);

        if (course != null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/instructors/{username}/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable String username, @PathVariable long id,
                                               @RequestBody Course course) {

        Course courseUpdated = courseManagementService.save(course);

        return new ResponseEntity<Course>(course, HttpStatus.OK);
    }

    @PostMapping("/instructors/{username}/courses")
    public ResponseEntity<Void> createCourse(@PathVariable String username, @RequestBody Course course) {

        Course createdCourse = courseManagementService.save(course);

        // Location
        // Get current resource url
        /// {id}
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }


     */


}


