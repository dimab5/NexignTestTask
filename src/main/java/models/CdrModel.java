package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

/**
 * Represents a Call Data Record (CDR) model.
 */
@Entity
@Table(name = "transactions")
@Getter
@Setter
@SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", allocationSize = 1)
public class CdrModel {
    /**
     * The unique identifier for the CDR.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    private Long id;

    /**
     * The type of call (incoming, outgoing).
     */
    @Column(name = "type_call")
    private String typeCall;

    /**
     * The subscriber involved in the call.
     */
    @ManyToOne
    @JoinColumn(name = "abonent_id")
    private Abonent abonent;

    /**
     * The start time of the call.
     */
    @Column(name = "start_time")
    private Instant startTime;

    /**
     * The end time of the call.
     */
    @Column(name = "end_time")
    private Instant endTime;
}
