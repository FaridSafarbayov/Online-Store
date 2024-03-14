package com.example.demo.model.dto;

import com.example.demo.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private int quantity;
    @JsonIgnore
    private Long usersId;
    @JsonIgnore
    private Long productsId;
    private OrderStatus orderStatus;
    private LocalDateTime localDateTime;


}
