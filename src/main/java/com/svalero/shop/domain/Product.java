package com.svalero.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String code;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String name;

    private boolean isNew;

    private int rating;

    private boolean isFragile;

    private boolean hasStock;



}
