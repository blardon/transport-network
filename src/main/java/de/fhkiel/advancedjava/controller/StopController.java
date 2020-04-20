package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.Stop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/stops")
public class StopController {

    @GetMapping
    public Collection<Stop> getAllStops() {
        return null;
    }

}
