package de.fhkiel.advancedjava.repository;

import de.fhkiel.advancedjava.model.queryresult.ConnectionResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StationRepositoryTest {

    private StationRepository stationRepository;

    @Autowired
    public StationRepositoryTest(StationRepository stationRepository){
        this.stationRepository = stationRepository;
    }

    @Test
    void testGDS(){
        ConnectionResult result = this.stationRepository.findFastestPathWithTransferTime("bla", "bla");
        assertNull(result);
    }

}
