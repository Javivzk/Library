package com.svalero.library.domain;

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
@Entity(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String code;

    @Column
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String titleNotice;

    @Column
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacio")
    private String description;

    @Column
    private LocalDate dateSendNotice;

    @Column
    @NotNull(message = "El nombre es obligatorio")
    private boolean hasRead;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_notices")
    private User userNotices;

    @ToString.Exclude
    @ManyToMany(mappedBy = "notices")
    private List<Rent> rents;

    @ToString.Exclude
    @ManyToMany(mappedBy = "noticesByBooks")
    private List<Book> books;

}
