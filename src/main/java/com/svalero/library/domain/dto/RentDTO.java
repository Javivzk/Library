package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentDTO {

    private int id;

    private String title;
    private String code;
    @NotNull
    private boolean isReturned;
    private long userId;

    private long bookId;

}
