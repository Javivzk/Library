package com.svalero.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "book")
public class Book {

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
    private String title;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String author;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate year;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String genre;

    @Column
    @Min(value = 0)
    private int pages;

    @Column
    @NotNull
    private boolean hasStock;

    @ManyToOne
    @JoinColumn(name = "rent_id")
    private Rent rent;

    @ManyToMany
    @JoinTable(name = "books_stock",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "stock_id"))
    private List<Stock> stock;


}
