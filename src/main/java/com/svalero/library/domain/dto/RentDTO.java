package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDTO {

    private int id;

    private String title;
    private String code;

    private LocalDate startRent;

    private LocalDate endRent;
    private boolean isReturned;
    private long userId;

    private float totalPrice;

    private long bookId;

}
