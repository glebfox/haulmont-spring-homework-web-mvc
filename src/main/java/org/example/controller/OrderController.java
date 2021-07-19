package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.Order;
import org.example.domain.OrderStatusResponse;
import org.example.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rest")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/register.do")
    @ResponseBody
    public long register(Order order) {
        return service.register(order).getId();
    }

    @PostMapping(path = "/deposit.do", params = {"id", "amount"})
    @ResponseBody
    public void deposit(@RequestParam long id, @RequestParam long amount) {
        service.deposit(id, amount);
    }

    @GetMapping(path = "/status.do", params = "id")
    @ResponseBody
    public OrderStatusResponse status(@RequestParam long id) {
        return service.status(id);
    }

    @DeleteMapping(path = "/reverse.do", params = "id")
    @ResponseBody
    public void reverse(@RequestParam long id) {
        service.reverse(id);
    }

}
