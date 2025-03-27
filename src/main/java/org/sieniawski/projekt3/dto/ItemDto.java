package org.sieniawski.projekt3.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @Positive(message = "Value must be greater than zero")
    private Integer value;
}
