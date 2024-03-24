package models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "abonents")
@Getter
@Setter
@SequenceGenerator(name = "my_sequence", sequenceName = "my_sequence", allocationSize = 1)
public class Abonent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_sequence")
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;
}
