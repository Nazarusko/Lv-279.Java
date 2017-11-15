/*
* Country
*
* Version 1.0-SNAPSHOT
*
* 14.11.17
*
* All rights reserved by DoubleO Team (Team#1)
* */

package ua.softserve.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @Column(nullable = false)
    private String name;

    private boolean ita;

}
