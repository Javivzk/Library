package com.svalero.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {
    private String code;
    @NotNull
    private long userId;
    @NotNull
    private long bookId;

    private String titleNotice;

    private String description;

    private Boolean hasRead;

}
