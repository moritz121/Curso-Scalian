package com.scalian.ArquitecturaSpringBoot.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDTO {
    //No queremos que muestre el campo ID.
    private String titulo;
    private String autor;
    private int paginas;

}
