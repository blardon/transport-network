package de.fhkiel.advancedjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.compress.utils.IOUtils;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ScheduleControllerWebTest {

    private final WebApplicationContext wac;
    private final ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    public ScheduleControllerWebTest(WebApplicationContext wac, ObjectMapper om) {
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

    //@Test
    void testImportSchedule() throws Exception {
        mockMvc.perform(post("/api/schedule/import").content("{\n" +
                "  \"stops\": [\n" +
                "    {\n" +
                "      \"stopId\": 1,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBURBAN_TRAIN\",\n" +
                "        \"SUBWAY\"\n" +
                "      ],\n" +
                "      \"state\": \"CLOSED\",\n" +
                "      \"name\": \"Hauptbahnhof D2\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stopId\": 2,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBURBAN_TRAIN\"\n" +
                "      ],\n" +
                "      \"state\": \"OPENED\",\n" +
                "      \"name\": \"Hummelwiese\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stopId\": 3,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBWAY\",\n" +
                "        \"SUBURBAN_TRAIN\"\n" +
                "      ],\n" +
                "      \"state\": \"OPENED\",\n" +
                "      \"name\": \"KVG-Btf. Werftstraße C\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stopId\": 4,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBURBAN_TRAIN\"\n" +
                "      ],\n" +
                "      \"state\": \"OPENED\",\n" +
                "      \"name\": \"Karlstal\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stopId\": 5,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBURBAN_TRAIN\"\n" +
                "      ],\n" +
                "      \"state\": \"OPENED\",\n" +
                "      \"name\": \"H D W\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"stopId\": 6,\n" +
                "      \"types\": [\n" +
                "        \"BUS\",\n" +
                "        \"SUBWAY\"\n" +
                "      ],\n" +
                "      \"state\": \"OPENED\",\n" +
                "      \"name\": \"Fachhochschule\",\n" +
                "      \"city\": \"Kiel\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"trafficLines\": [\n" +
                "    {\n" +
                "      \"lineId\": 1,\n" +
                "      \"name\": \"Line 11\",\n" +
                "      \"type\": \"BUS\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"beginStopId\": 1,\n" +
                "          \"endStopId\": 2,\n" +
                "          \"durationInMinutes\": 5\n" +
                "        },\n" +
                "        {\n" +
                "          \"beginStopId\": 2,\n" +
                "          \"endStopId\": 3,\n" +
                "          \"durationInMinutes\": 3\n" +
                "        },\n" +
                "        {\n" +
                "          \"beginStopId\": 3,\n" +
                "          \"endStopId\": 4,\n" +
                "          \"durationInMinutes\": 2\n" +
                "        },\n" +
                "        {\n" +
                "          \"beginStopId\": 4,\n" +
                "          \"endStopId\": 5,\n" +
                "          \"durationInMinutes\": 5\n" +
                "        },\n" +
                "        {\n" +
                "          \"beginStopId\": 5,\n" +
                "          \"endStopId\": 6,\n" +
                "          \"durationInMinutes\": 5\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"lineId\": 2,\n" +
                "      \"name\": \"Underground 1\",\n" +
                "      \"type\": \"SUBWAY\",\n" +
                "      \"sections\": [\n" +
                "        {\n" +
                "          \"beginStopId\": 1,\n" +
                "          \"endStopId\": 3,\n" +
                "          \"durationInMinutes\": 5\n" +
                "        },\n" +
                "        {\n" +
                "          \"beginStopId\": 3,\n" +
                "          \"endStopId\": 6,\n" +
                "          \"durationInMinutes\": 5\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}\n").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

}
