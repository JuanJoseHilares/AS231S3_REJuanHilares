package model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Garments {
    private int id;
    private String code;
    private String name;
    private String brand;
    private String category;
    private BigDecimal price;
    private String state;
}