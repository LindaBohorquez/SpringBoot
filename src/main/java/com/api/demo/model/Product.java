package com.api.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

@Audited
@Getter
@Setter
@Entity
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    // Si quieres relacionarlo con un usuario, podrías agregar:
    /*
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    */

    // Otros atributos que quieras auditar o usar
}
