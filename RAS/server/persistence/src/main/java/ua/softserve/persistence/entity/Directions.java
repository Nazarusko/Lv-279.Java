package ua.softserve.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Entity
@Table(name = "directions")
public class Directions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direction_id")
    private Integer directionId;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "ita")
    private Byte ita;

    public Directions() {}
}
