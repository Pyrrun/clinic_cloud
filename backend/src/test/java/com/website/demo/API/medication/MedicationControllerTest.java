package com.website.demo.API.medication;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private MedicationRepository medicationRepository;

    @Test
    public void testFindingMedicationByLetters() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/medications?name=He"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult).isNotNull();


    }



}