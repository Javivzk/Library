package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentOutDTO {

    private int id;

    private String title;


    private String code;

    private boolean isReturned;
}
