package org.example;

import org.example.domain.PositionResponse;
import org.example.service.OrderService;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        OrderService orderService = new OrderService();
        List<PositionResponse> positionResponses = orderService.generatePositionDetailFromFile();
        System.out.println(positionResponses);
    }
}