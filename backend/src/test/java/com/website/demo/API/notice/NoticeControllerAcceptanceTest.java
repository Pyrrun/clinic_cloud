package com.website.demo.API.notice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NoticeControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void shouldGetCustomWelcomeMessage() throws Exception {
        mockMvc.perform(get("/yoooo"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(equalTo("Heyy Roman")));

    }

    @Test
    void testIfGettingAllNoticesWorks() throws Exception {
        mockMvc.perform(get("/notices"))
                .andExpect(status().isOk())
                .andDo(print());
    }


}