package com.svalero.library.domain;

import lombok.Data;

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
    @NotNull(message = "El nombre es obligatorio")
    private Boolean isAvailable;

    @Column
    @Min(value = 0)
    private int quantity;


    @ManyToMany(mappedBy = "stock")
    private List<Book> books;

}
