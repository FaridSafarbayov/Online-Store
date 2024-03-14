package com.example.demo.controller;


import com.example.demo.exception.StockLimitException;
import com.example.demo.model.dto.OrdersDto;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/createOrder")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<OrdersDto> createOrder(@Valid @RequestBody OrdersDto orderDto,
                                                 @RequestParam Long userId,
                                                 @RequestParam Long productId) throws StockLimitException {
        return new ResponseEntity<>(orderService.createOrder(orderDto, userId, productId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> deleteByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrderById(id));

    }
    @GetMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<OrdersDto>> getAllOrders() {
        List<OrdersDto> orderDetails = orderService.getAllOrders();
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
    @GetMapping(value = { "/history","/{id}"})
//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<String> getHistory(@PathVariable Long id){
       return orderService.getOrderHistory(id);
    }

}
