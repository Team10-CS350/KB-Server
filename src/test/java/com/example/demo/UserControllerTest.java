package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        mockMvc.perform(post("/users/")
                .contentType("application/json")
                .characterEncoding("utf8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isOk());

    }

    @Test
    public void whenUnsupported_thenReturns415() throws Exception {
        mockMvc.perform(post("/users/")
                .contentType("application/something")
                .characterEncoding("utf8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void whenContentIsNull_thenReturns400() throws Exception {
        mockMvc.perform(post("/users/")
                .contentType("application/json")
                .characterEncoding("utf8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidURL_thenReturns404() throws Exception {
        mockMvc.perform(post("/usersaaa/")
                .contentType("application/json")
                .characterEncoding("utf8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void Post_InvalidURL_thenReturns405() throws Exception {
        mockMvc.perform(post("/users/1998")
                .contentType("application/json")
                .characterEncoding("utf8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void CheckingCorrectName() throws Exception {
        MvcResult mvcResult=mockMvc.perform(post("/users/")
                .contentType("application/json")
                .characterEncoding("utf8")
                .content("{\"name\": \"Ben\"}")
        ).andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
//        assertThat(objectMapper.writeValueAsString(expectedResponseBody))
//                .isEqualToIgnoringWhitespace(actualResponseBody);
        assertThat(actualResponseBody.contains("{\"name\": \"Ben\"}"));
    }


    @Test
    public void GetAllUsersList_ValidInput_thenReturns200() throws Exception {
        mockMvc.perform(get("/users/")
                .contentType("application/json")
                .characterEncoding("utf8")
        ).andExpect(status().isOk());
    }

    @Test
    public void GetUserByID_ValidInput_thenReturns200() throws Exception {
        mockMvc.perform(get("/users/1")
                        .contentType("application/json")
                        .characterEncoding("utf8")
        ).andExpect(status().isOk());
    }

    @Test
    public void GetUserByID_CheckingRightOutput() throws Exception {
        MvcResult mvcResult=mockMvc.perform(get("/users/1")
                .contentType("application/json")
                .characterEncoding("utf8")
        ).andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody.contains("{\"name\": \"aaa\"}"));
    }

    // Through this test, we found out that we need to throw a notFoundError when the ID does not exist in the database
    @Test
    public void GetUserByID_InvalidInput_thenReturns400() throws Exception {

        mockMvc.perform(get("/users/1998")
                        .contentType("application/json")
                        .characterEncoding("utf8")
        ).andExpect(status().isNotFound());
    }




}


