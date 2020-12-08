package com.biomixers.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.biomixers.BiomixersApplicationTests;
import org.junit.Before;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class MemberControllerTest extends BiomixersApplicationTests {


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/generate-sample-data")).andExpect(status().isOk());
        mockMvc.perform(get("/member/10")).andExpect(status().isOk());
    }

//    @Test
//    public void generateSampleData() throws Exception {
////        System.out.println(mockMvc.perform(get("/generate-sample-data")));
////        System.out.println(mockMvc.perform(get("/member/10")));
////        mockMvc.perform(get("/member/10")).andExpect(status().isOk());
//
////        mockMvc.perform(get("/member/10")).andExpect(status().isOk())
////                .andExpect(content().contentType("application/json;charset=UTF-8"))
////                .andExpect(jsonPath("$.name").value("emp1")).andExpect(jsonPath("$.designation").value("manager"))
////                .andExpect(jsonPath("$.empId").value("1")).andExpect(jsonPath("$.salary").value(3000));
//
//    }
//
//    @Test
//    void addMember() {
//    }
//
//    @Test
//    void getMember() {
//    }
//
//    @Test
//    void getAllMembers() {
//    }
}