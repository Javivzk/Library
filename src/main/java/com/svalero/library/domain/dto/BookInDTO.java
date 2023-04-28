package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInDTO {

    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String title;

    @NotNull
    @NotBlank
    private String author;

    private String genre;

    private boolean hasStock;

    private int rentId;
}
