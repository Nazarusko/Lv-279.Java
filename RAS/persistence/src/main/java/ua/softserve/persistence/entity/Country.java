package ua.softserve.persistence.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int country_id;

    @NotNull
    @Column(name = "crm_id")
    private int crm_id;

    @Column(name = "code")
    private String code;

    @Column(name = "hot_line")
    private String hot_line;

    @Column(name = "status")
    private Byte status;

    @Column(name = "ita")
    private Integer ita;

    @Column(name = "vacancy")
    private Integer vacancy;
}
