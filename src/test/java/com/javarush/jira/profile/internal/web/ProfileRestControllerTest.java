package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(ProfileRestController.class)
public class ProfileRestControllerTest extends AbstractControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void getSuccessful() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/profile")
//                .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.mailNotifications").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.contacts").isNotEmpty())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.lastLogin").exists());
//    }

}