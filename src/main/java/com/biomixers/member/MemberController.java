package com.biomixers.member;

import com.biomixers.BiomixersApplication;
import com.biomixers.filter.SearchFilterQuery;
import com.biomixers.util.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public Member firstPage() {

        List<Integer> list = Arrays.asList(33, 45, 66);
        List<String> food_preferences = Arrays.asList("American", "Mexican");

        HashMap<String, String[]> availability = new HashMap<>();

        availability.put("Monday", new String[]{"Breakfast", "Lunch"});
        //availability.put("Monday", "Breakfast");

        //member = new Member(new String[]{"American", "Mexican"}, "Dan Bernstein", "dan@swssupport.com", 11, new int[]{33, 45, 66}, availability, 0);
        //member = new Member(2, "TEST", "dan2@swssupport.com");

        Member member = new Member(11,"Dan Bernstein", "dan@swssupport.com", list, null, availability, 0);
//        Member member = null;


        if(member != null){
//            member = memberRepository.save(member);
            ////////log.info("New member is created: " + member);
        }

        return member;
    }


    @GetMapping("/add")
    public ResponseEntity<Member> addMember() {
        Member member = memberService.addMember();

        return new ResponseEntity<Member>(member, HttpStatus.OK);
    }

    @GetMapping("/member/{user_id}")
    public Member getMember(@PathVariable int user_id) {
        return memberRepository.findById(user_id).get();

    }



    @GetMapping("/members")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/generate-sample-data")
    public ResponseEntity<String> generateSampleData() {
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

        for (int i = 0; i < total_num_members; i++) {
            allMemberIds[i] = members.get(i).getUserId();
        }

        for (Member member : members) {
            ArrayList<String> avail_options = new ArrayList<>();
            avail_options.add("Breakfast");
            avail_options.add("Lunch");
            avail_options.add("Dinner");

            HashMap<String, String[]> availability = new HashMap<>();

            //availability.put("Monday", new String[]{"Breakfast", "Lunch"});


            long seed;

            // Sets random generator seed, forces consistency in sample data
            if (!searchFilterQuery.isRandomizeResults()) {
                seed = member.getUserId();
                //rand.setSeed(member.getUser_id());
            } else {
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
            ArrayList<Integer> members_met = (ArrayList<Integer>) HelperFunctions.randomSampleInt(list1, (int) (total_num_members * searchFilterQuery.getPercentageOfMembersMet()), seed);


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
            member.setFoodPreferences(food_preferences);
            member.setMembersMet(members_met);

            memberRepository.save(member);
        }

        return new ResponseEntity<String>("yes", HttpStatus.OK);
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


