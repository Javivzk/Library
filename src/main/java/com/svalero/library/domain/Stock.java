package com.svalero.library.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Entity(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Min(value = 0)
    private int quantity;


    @ManyToMany(mappedBy = "stock")
    private List<Book> books;

}
