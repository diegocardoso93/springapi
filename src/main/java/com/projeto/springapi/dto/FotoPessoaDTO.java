package com.projeto.springapi.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FotoPessoaDTO {
    private Long fpId;
    private Long pesId;
    private LocalDate fpData;
    private String fpBucket;
    private String fpHash;
}
