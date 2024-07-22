package org.example.service;

import org.example.domain.ActionEnum;
import org.example.domain.DirectionEnum;
import org.example.domain.OrderRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderRequestCollector {

    public static List<OrderRequest>  collectRequestFromFile(String filePath) throws IOException {

        List<OrderRequest> requestList;
        try (Stream<String> fileStream = Files.lines(Paths.get(filePath))) {
            requestList = fileStream.map(line -> {
                String[] array = line.split(",");
                return new OrderRequest(
                        Integer.parseInt(array[0]), //id
                        array[1],//tradeId
                        Integer.parseInt(array[2]), //version
                        array[3], //product
                        new BigDecimal(array[4]),//quantity
                ActionEnum.valueOf(array[5]),
                DirectionEnum.valueOf(array[6]));
            }).collect(Collectors.toList());
        }

        return requestList;
    }
}
