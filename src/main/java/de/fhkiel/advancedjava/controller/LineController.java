package de.fhkiel.advancedjava.controller;

import de.fhkiel.advancedjava.model.node.Line;
import de.fhkiel.advancedjava.model.node.dto.LineDto;
import de.fhkiel.advancedjava.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
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
    private GenericConversionService conversionService;

    @Autowired
    public LineController(LineService lineService, GenericConversionService conversionService){
        this.lineService = lineService;
        this.conversionService = conversionService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LineDto> addNewLine(@RequestBody LineDto lineDto){

        Line newLine = conversionService.convert(lineDto, Line.class);

        if (newLine != null){
            this.lineService.addNewLine(newLine);
            return ResponseEntity.ok(lineDto);
        }
        return ResponseEntity.badRequest().body(lineDto);
    }

}
