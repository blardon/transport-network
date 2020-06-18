package de.fhkiel.advancedjava.model.schedule;

import de.fhkiel.advancedjava.model.StopType;
import de.fhkiel.advancedjava.model.relationship.TransferTo;
import lombok.*;
import org.neo4j.ogm.annotation.*;

@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Stop {

    @Id @GeneratedValue
    private Long id;

    private StopType type;

    @Relationship(type = "TRANSFER_TO", direction = Relationship.OUTGOING)
    private TransferTo transferTo;

    public Long getStopId() {
        return id;
    }

    public StopType getType() {
        return type;
    }

    public void setType(StopType type) {
        this.type = type;
    }

    public TransferTo getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(TransferTo transferTo) {
        this.transferTo = transferTo;
    }
}
