package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String name;
    @NotBlank(message = "El nombre de usuario no puede estar vacio")
    @NotNull(message = "El nombre de usuario es obligatorio")
    private String lastName;
    @NotBlank(message = "La contraseña no puede estar vacia")
    @NotNull(message = "La contraseña es obligatoria")
    private String code;
    private String address;
    private boolean isMember;
    private LocalDate birthdate;

    private String phoneNumber;
}
