package com.svalero.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "El campo no puede estar vacio")
    @NotNull(message = "El campo es obligatorio")
    private String code;

    @Column(name = "start_rent")
    @CreatedDate
    private LocalDate startRent;

    @Column(name = "end_rent")
    private LocalDate endRent;

    @Column
    @NotNull(message = "El nombre es obligatorio")
    private boolean isReturned;

    @OneToMany(mappedBy = "rent")
    private List<Book> book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToMany
    @JoinTable(name = "notice_rent",
            joinColumns = @JoinColumn(name = "rent_id"),
            inverseJoinColumns = @JoinColumn(name = "notice_id"))
    private List <Notice> notices;

}
