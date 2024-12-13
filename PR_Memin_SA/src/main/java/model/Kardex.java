package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Kardex {
    private int id;
    private int products_id;
    private String productName;
    private Date registration_date;
    private String month;
    private String motion;
    private int amount;
    private BigDecimal unit_cost;
    private BigDecimal total_cost;
    private String state;
}