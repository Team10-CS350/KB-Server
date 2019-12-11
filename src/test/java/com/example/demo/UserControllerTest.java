package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import com.example.demo.domain.User;
import com.example.demo.dto.User.UserRegistrationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;


import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "fooClientId");
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic("fooClientId","secret"))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }




    @Test
    public void whenValidInput_thenReturns200() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(get("/users/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andExpect(status().isOk());

    }

    @Test
    public void whenUnsupported_thenReturns415() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(post("/users/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/something")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isUnsupportedMediaType());
    }

    @Test
    public void whenContentIsNull_thenReturns400() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(post("/users/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void whenInvalidURL_thenReturns404() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(post("/usersaaa/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void DifferentURL_thenReturns405() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(post("/users/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\": \"Hello There\"}")
        ).andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void CheckingCorrectName() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        MvcResult mvcResult=mockMvc.perform(post("/users/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\": \"Ben\"}")
        ).andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody.contains("{\"name\": \"Ben\"}"));
    }


    @Test
    public void GetAllUsersList_ValidInput_thenReturns200() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(get("/users/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void GetUserByID_ValidInput_thenReturns200() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(get("/users/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andExpect(status().isOk());
    }

    @Test
    public void GetUserByID_CheckingRightOutput() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        MvcResult mvcResult=mockMvc.perform(get("/users/1")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andReturn();
        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponseBody.contains("{\"name\": \"aaa\"}"));
    }

    // Through this test, we found out that we need to throw a notFoundError when the ID does not exist in the database
    @Test
    public void GetUserByID_InvalidInput_thenReturns400() throws Exception {
        String accessToken = obtainAccessToken("a@kaist.ac.kr", "aaaaaaaaaa");
        mockMvc.perform(get("/users/1998")
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json;charset=UTF-8")
        ).andExpect(status().isNotFound());
    }


    @Test
    public void Registration_ValidInput_thenReturns200AndNewUser() throws Exception {

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setName("Name");
        userRegistrationDTO.setEmail("aaaa@kaist.ac.kr");
        userRegistrationDTO.setPassword("1234567890");
        userRegistrationDTO.setConfirmPassword("1234567890");

        MvcResult mvcResult = mockMvc.perform(post("/registration")
                .contentType("application/json;charset=UTF-8")
                .content(objectMapper.writeValueAsString(userRegistrationDTO))
        ).andExpect(status().isOk()).andReturn();

        User user = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), User.class);

        assertThat(
                user.getEmail().equals(userRegistrationDTO.getEmail()) &&
                        user.getName().equals(userRegistrationDTO.getName())
        );
    }


    @Test
    public void Registration_PasswordsDontMatch_thenReturn400() throws Exception {

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setName("Name");
        userRegistrationDTO.setEmail("aaaa@kaist.ac.kr");
        userRegistrationDTO.setPassword("123456789");
        userRegistrationDTO.setConfirmPassword("12345678910");

        mockMvc.perform(post("/registration")
                .contentType("application/json;charset=UTF-8")
                .content(objectMapper.writeValueAsString(userRegistrationDTO))
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void Registration_PasswordTooShort_thenReturn400() throws Exception {

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setName("Name");
        userRegistrationDTO.setEmail("aaaa@kaist.ac.kr");
        userRegistrationDTO.setPassword("123");
        userRegistrationDTO.setConfirmPassword("123");

        mockMvc.perform(post("/registration")
                .contentType("application/json;charset=UTF-8")
                .content(objectMapper.writeValueAsString(userRegistrationDTO))
        ).andExpect(status().isBadRequest());

    }


}


