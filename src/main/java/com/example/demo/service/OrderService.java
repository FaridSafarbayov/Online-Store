package com.example.demo.service;

import com.example.demo.exception.OrdersNotFoundException;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.StockLimitException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.OrdersDto;
import com.example.demo.model.entity.Orders;
import com.example.demo.model.entity.Product;
import com.example.demo.model.entity.Users;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    public OrdersDto createOrder(OrdersDto orderDto, Long userId, Long productsId) throws StockLimitException {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("this id not found :" + userId));
        Product product = productRepository.findById(productsId)
                .orElseThrow(() -> new ProductNotFoundException("this id not found :" + productsId));

        int requestedQuantity = orderDto.getQuantity();

        if (requestedQuantity > product.getStockQuantity()) {
            throw new StockLimitException("requested quantity more than grater stock quantity.");
        }
        if (requestedQuantity == 0) {
            throw new OrdersNotFoundException("quantity can not be zero");
        }
        double totalPrice = product.getPrice() * requestedQuantity;


        int updatedStockQuantity = product.getStockQuantity() - requestedQuantity;
        product.setStockQuantity(updatedStockQuantity);

        Orders ordersEntity = modelMapper.map(orderDto,Orders.class);
        System.out.println(ordersEntity.getQuantity()+"alma");
        ordersEntity.setUser(user);

        ordersEntity.setProduct(product);
        ordersEntity.setQuantity(orderDto.getQuantity());
        ordersEntity.setOrderStatus(orderDto.getOrderStatus());
        ordersEntity.setTotalPrice(totalPrice);


        orderRepository.save(ordersEntity);

        return modelMapper.map(ordersEntity,OrdersDto.class);
    }

    public String deleteOrderById(Long id) {
        Orders orders=orderRepository.findById(id).orElseThrow(()-> new OrdersNotFoundException("this id not found : " + id));
        orderRepository.deleteById(orders.getId());
        return "Your orders has deleted";
    }

    public List<OrdersDto> getAllOrders() {
        List<Orders> ordersList = orderRepository.findAll();
        if (ordersList.isEmpty()) {
            throw new OrdersNotFoundException("there are not any orders");
        }
        return ordersList.stream()
                .map(order -> modelMapper.map(order, OrdersDto.class))
                .collect(Collectors.toList());
    }


    public ResponseEntity<String> getOrderHistory(Long id) {
       Orders orders = orderRepository.findById(id).get();
       LocalDateTime date = orders.getLocalDateTime();
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = date.format(formatter);
            return ResponseEntity.ok("Order history: " + formattedDate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
