package com.ajith.spring.springint.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "person")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Person {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

}
