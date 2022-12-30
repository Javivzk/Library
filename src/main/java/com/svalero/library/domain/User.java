package com.svalero.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String code;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String name;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String lastName;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String address;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String phoneNumber;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private String email;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private LocalDate birthdate;

    @Column
    @NotBlank(message = "El nombre no puede estar vacio")
    @NotNull(message = "El nombre es obligatorio")
    private boolean isMember;

    @OneToMany(mappedBy = "users")
    private List<Rent> rents;

    @ManyToOne
    @JoinColumn(name = "user_notices")
    private Notice notices;




}
