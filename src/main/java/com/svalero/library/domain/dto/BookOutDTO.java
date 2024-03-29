package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOutDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String title;

    private String author;
    @NotNull(message = "La descripcion es obligatoria")
    @NotBlank(message = "La descripcion no puede estar vacia")
    private String description;
    private String genre;

    private boolean hasStock;

    private int rentId;
}
