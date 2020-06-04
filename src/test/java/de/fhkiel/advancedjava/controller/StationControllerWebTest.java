package de.fhkiel.advancedjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StationControllerWebTest {

    private final WebApplicationContext wac;
    private final ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public StationControllerWebTest(WebApplicationContext wac, ObjectMapper om) {
        this.wac = wac;
        this.objectMapper = om;
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // this launches the entire application (fully integrated test) -> maybe mock ticketservice and use
        //  MockMvcBuilders.standaloneSetup(new TicketController(...))
        // and pass mocked arguments (partially integrated test)
    }

    @Test
    void testFindStationNotExisting() throws Exception {
        mockMvc.perform(get("/api/station/1000").accept(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(""));
    }

}
