package com.svalero.library.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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

    @Column
    @NotNull
    private float totalPrice;

    @ToString.Exclude
    @OneToMany(mappedBy = "rent")
    private List<Book> book;

    @ToString.Exclude
    @OneToMany(mappedBy = "userRents")
    private List<User> user;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "notice_rent",
            joinColumns = @JoinColumn(name = "rent_id"),
            inverseJoinColumns = @JoinColumn(name = "notice_id"))
    private List <Notice> notices;

}
