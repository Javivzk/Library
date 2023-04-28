package com.svalero.library.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String code;

    @Column
    @NotNull(message = "El campo es obligatorio")
    private Boolean isAvailable;

    @Column
    private int quantity;


    @ToString.Exclude
    @ManyToMany(mappedBy = "stocks")
    private List<Book> books;

}
