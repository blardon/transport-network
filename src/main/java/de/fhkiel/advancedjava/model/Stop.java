package de.fhkiel.advancedjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NodeEntity
public class Stop {

    @Id
    private Long id;

    private String name;


}
