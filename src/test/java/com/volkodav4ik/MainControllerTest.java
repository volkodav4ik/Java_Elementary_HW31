package com.volkodav4ik;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.volkodav4ik.acessingdatapostgresql.UserRepository;
import com.volkodav4ik.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mvc;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        user = new User("tester", "test@test.com");
    }

    @After
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void testAddNewUser() throws Exception {
        mvc.perform(post("")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("name=tester&email=test@test.com"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.redirectedUrl(null));
    }

    @Test
    public void testExistingUser() throws Exception {
        userRepository.save(user);

        mvc.perform(post("")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .content("name=tester&email=test@test.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("exist"));
    }

    @Test
    public void testDeleteExistingUser() throws Exception {
        mvc.perform(delete("/delete/" + userRepository.save(user).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotExistingUser() throws Exception {
        mvc.perform(delete("/delete/test"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

}
