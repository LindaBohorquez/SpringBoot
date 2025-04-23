package com.api.demo.model;


import org.hibernate.envers.Audited;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Audited
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String lasteName;

    @Column
    private String email;

}
