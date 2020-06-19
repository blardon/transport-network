package de.fhkiel.advancedjava.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import de.fhkiel.advancedjava.model.queryresult.ConnectionResultDto;
import de.fhkiel.advancedjava.service.StationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConnectionControllerWebTest {

    private final WebApplicationContext wac;
    private final ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private StationService stationService;
    private ConnectionController connectionController;

    @Autowired
    public ConnectionControllerWebTest(WebApplicationContext wac, ObjectMapper om, StationService stationService, ConnectionController connectionController) {
        this.wac = wac;
        this.objectMapper = om;
        this.stationService = stationService;
        this.connectionController = connectionController;
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        // this launches the entire application (fully integrated test) -> maybe mock ticketservice and use
        //  MockMvcBuilders.standaloneSetup(new TicketController(...))
        // and pass mocked arguments (partially integrated test)
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

    @Test
    void testFastestConnection() throws Exception {
        ConnectionResult result = stationService.findFastestPathWithTransferTime("Hummelwiese", "Fachhochschule");

        assertNotNull(result);
        assertTrue(result.getStations().size() > 0);
        assertTrue(result.getLegs().size() > 0);
        assertTrue(result.getLegs().size() > 0);
        assertEquals(result.getTotalTime(), 8L);
    }

    @Test
    void testFastestConnection_mvc() throws Exception {
        ResponseEntity<ConnectionResultDto> result = connectionController.getFastestConnectionWithTransferTime("Hummelwiese", "Fachhochschule");

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().getStationDtos().size() > 0);
        assertTrue(result.getBody().getLineDtos().get(0).getLegDTOs().size() > 0);
        assertTrue(result.getBody().getLineDtos().size() > 0);
        assertEquals(result.getBody().getTotalTime(), 8L);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testFindNStopsInMMinutes() throws Exception {
        ConnectionResult result = stationService.findNStopsInMMinutes(4L, 10L);

        assertNotNull(result);
        assertTrue(result.getStations().size() >= 4);
        assertTrue(result.getTotalTime() <= 10);
    }

    @Test
    void testFindNStopsInMMinutes_mvc(){
        ResponseEntity<ConnectionResultDto> result = connectionController.getNStopsInMMinutes(4L, 10L);

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().getStationDtos().size() >= 4);
        assertTrue(result.getBody().getTotalTime() <= 10);
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void testFind3Cheapest() throws Exception {
        Collection<ConnectionResult> result = stationService.find3Cheapest("Hummelwiese", "Fachhochschule");

        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    void testFind3Cheapest_mvc(){
        ResponseEntity<Collection<ConnectionResultDto>> result = connectionController.find3CheapestConnections("Hummelwiese", "Fachhochschule");

        assertNotNull(result);
        assertNotNull(result.getBody());
        assertTrue(result.getBody().size() > 0);
        assertFalse(result.getBody().contains(null));
        assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}
