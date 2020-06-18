package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.schedule.Line;
import de.fhkiel.advancedjava.model.schedule.dto.LineDto;
import de.fhkiel.advancedjava.service.DtoConversionService;
import de.fhkiel.advancedjava.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/line")
public class LineController {

    private LineService lineService;
    private DtoConversionService conversionService;

    @Autowired
    public LineController(LineService lineService, DtoConversionService conversionService){
        this.lineService = lineService;
        this.conversionService = conversionService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LineDto> addNewLine(@RequestBody LineDto lineDto){
        Line newLine = this.conversionService.convert(lineDto);

        this.lineService.addNewLine(newLine);

        return ResponseEntity.ok(lineDto);
    }

    @PostMapping(path = "/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Line> testLine(@RequestBody Line line){
        Line res = this.lineService.addNewLine(line);
        return ResponseEntity.ok(res);
    }

}
