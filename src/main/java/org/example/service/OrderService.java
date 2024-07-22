package org.example.service;


import org.example.domain.OrderRequest;
import org.example.domain.PositionDetail;
import org.example.domain.PositionResponse;
import org.example.exception.BusinessException;

import java.net.URI;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private static String TRADE_ID = "tradeId";
    private static String PRODUCT = "product";


    public List<PositionResponse> generatePositionDetailFromFile() {
        try {

            URI uri = ClassLoader.getSystemClassLoader().getResource("orderRequest.txt").toURI();
            String path = Paths.get(uri).toString();

            List<OrderRequest> requestList = OrderRequestCollector.collectRequestFromFile(path);

            return generatePositionResponse(requestList);
        } catch (Exception e) {
            System.out.println("exception when load existing file");
            return null;
        }
    }

    public List<PositionResponse> generatePositionResponse(List<OrderRequest> requestList){

        List<PositionDetail> positionDetailList = generatePositionDetail(requestList);

        return positionDetailList.stream().map(PositionResponse::new).distinct().toList();
    }
    public List<PositionDetail> generatePositionDetail(List<OrderRequest> requestList){
        if(requestList == null || requestList.size() == 0){
            return null;
        }

        ThreadLocal<Map<String,Map<String,PositionDetail>>> threadLocal = new ThreadLocal<>();
        Map<String,PositionDetail> tradeIdMap = new HashMap<>();
        Map<String,PositionDetail> productIdMap = new HashMap<>();
        Map<String,Map<String,PositionDetail>> positionDetailMap = new HashMap<>();
        positionDetailMap.put(TRADE_ID,tradeIdMap);
        positionDetailMap.put(PRODUCT,productIdMap);
        threadLocal.set(positionDetailMap);

        Map<String, List<OrderRequest>> ordersMap = requestList.stream().collect(Collectors.groupingBy(OrderRequest::getTradeId));

        return ordersMap.entrySet().stream().map(
                e -> generatePositionDetail(e.getKey(), e.getValue(),threadLocal)).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private PositionDetail generatePositionDetail(String tradeId, List<OrderRequest> orderRequests,
                                                  ThreadLocal<Map<String,Map<String,PositionDetail>>> threadLocal)  {
        Collections.sort(orderRequests);
        String product = orderRequests.get(0).getProduct();
        PositionDetail existingPosition = getCurrentPosition(tradeId,product,threadLocal.get());

        System.out.println(String.format("processing order %s with %s orders",tradeId, orderRequests.size()));
        for(OrderRequest orderRequest : orderRequests){
            try {
                existingPosition = OrderStrategy.processOrder(existingPosition,orderRequest);
                Map<String, Map<String, PositionDetail>> positionDetailMap = threadLocal.get();
                Map<String, PositionDetail> tradeIdMap = positionDetailMap.get(TRADE_ID);
                Map<String, PositionDetail> productIdMap = positionDetailMap.get(PRODUCT);

                tradeIdMap.put(tradeId,existingPosition);
                productIdMap.put(product,existingPosition);
                threadLocal.set(positionDetailMap);

            } catch (BusinessException e) {
                existingPosition = null;
                System.out.println(String.format("exception found for order %s, msg is: %s", tradeId, e.getMessage()));
                break;
            }
        }

        return existingPosition;

    }

    private PositionDetail getCurrentPosition(String tradeId,String productId, Map<String,Map<String,PositionDetail>> existingMapDetail) {
        if(existingMapDetail == null) {
            return null;
        }

        Map<String, PositionDetail> tradeIdMap = existingMapDetail.get(TRADE_ID);
        Map<String,PositionDetail> prductMap = existingMapDetail.get(PRODUCT);

        PositionDetail positionDetail = tradeIdMap.get(tradeId);

        if(positionDetail == null ){
            //get by product
           return prductMap.get(productId);
        }
        return positionDetail;
    }


}
