package com.website.demo.API.notice;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@SpringBootTest
//@AutoConfigureMockMvc
public class NoticeControllerUnitTest {



    @Test
    public void shouldReturnDefaultMessage() {

        NoticeService noticeService = Mockito.mock(NoticeService.class);
        NoticeController noticeController = new NoticeController(noticeService);
        when(noticeService.sayHello("man")).thenReturn("Heyy man");
        assertEquals("Heyy man", noticeController.hey("man"));

    }

    @Test
    public void testGetNotice(){
        NoticeRepository noticeRepository = Mockito.mock(NoticeRepository.class);
        Notice notice  = noticeRepository.findById(14L).orElseThrow(IllegalStateException::new);
        assertThat(notice).isNotNull();

    }




//
//    @Test
//    public void contextLoads() throws Exception {
//        assertThat(noticeController).isNotNull();
//    }

//    @Test
//    void testFindingAllNotices() throws Exception {
//        MvcResult result = mockMvc.perform(get("/notices"))
//                .andDo(print())
//                .andExpect(status().is(200))
//                .andReturn();
//    }


}