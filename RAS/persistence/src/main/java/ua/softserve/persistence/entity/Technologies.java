package ua.softserve.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@Table(name = "technologies")
public class Technologies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int technology_id;

    @NotNull
    @Column(name = "alias")
    private String alias;

    @NotNull
    @Column(name = "free")
    private int free;

    @NotNull
    @Column(name = "image")
    private String image;

    @NotNull
    @Column(name = "image2")
    private String image2;

    @Column(name = "status")
    private char status = '1';

    @Column(name = "position")
    private Integer position;

    @Column(name = "price")
    private Integer price;

    @Column(name = "ita")
    private Byte ita;

    @Column(name = "vacancy")
    private Byte vacancy;

    @Column(name = "name")
    private String name;

    @Column(name = "directiond_id", columnDefinition = "INT(11) UNSIGNED")
    private int directiond_id;

    @Column(name = "crm_id")
    private Integer crm_id;

    @Column(name = "activeGroups")
    private Integer activeGroups;

    public Technologies() {}
}
