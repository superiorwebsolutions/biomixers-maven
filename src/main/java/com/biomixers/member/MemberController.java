package com.biomixers.member;

import com.biomixers.filter.SearchFilterQuery;
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
        generateSampleData();
        getAllMembers();


        return new ResponseEntity<SearchFilterQuery>(searchFilterQuery, HttpStatus.OK);
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
    public List<Member> generateSampleData(){
        return memberService.generateSampleData();
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


