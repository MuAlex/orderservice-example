package org.example.service;



import org.example.domain.ActionEnum;
import org.example.domain.DirectionEnum;
import org.example.domain.OrderRequest;
import org.example.domain.PositionDetail;
import org.example.exception.BusinessException;

import java.math.BigDecimal;

public class OrderStrategy {

    public static PositionDetail processOrder(PositionDetail existingPosition, OrderRequest orderRequest) throws BusinessException {
        if(orderRequest.getAction().equals(ActionEnum.INSERT)){

            return processInsertStrategy(existingPosition,orderRequest);
        }
        else if(orderRequest.getAction().equals(ActionEnum.UPDATE)){
            return processUpdateStrategy(existingPosition,orderRequest);
        }
        else if(orderRequest.getAction().equals(ActionEnum.CANCEL)){
            return processUpdateStrategy(existingPosition,orderRequest);
        }
        throw new BusinessException("invalid action");
    }

    public static PositionDetail processInsertStrategy(PositionDetail existingPosition, OrderRequest orderRequest) throws BusinessException {
        if(DirectionEnum.SELL.equals(orderRequest.getDirection())) {
            if (existingPosition == null) {
                throw new BusinessException("action is INSERT, direction is SELL, while no position found");
            }
            BigDecimal before = existingPosition.getQuantity();
            BigDecimal after = before.subtract(orderRequest.getQuantity());
            if(after.compareTo(BigDecimal.ZERO) < 0 ){
                throw new BusinessException("no enough quantity to sell");
            }
            else{
                System.out.println(String.format("SELL order found, before %s, sell quantity %s, after %s",
                        before,orderRequest.getQuantity(),after));
                existingPosition.setQuantity(after);
                return existingPosition;
            }
        }

        else if(DirectionEnum.BUY.equals(orderRequest.getDirection())) {

            if (existingPosition != null) {
                throw new BusinessException("existing position is not null, action cannot be INSERT");
            }
            return new PositionDetail(orderRequest);
        }
        throw new BusinessException("invalid order request");
    }

    public static PositionDetail processUpdateStrategy(PositionDetail existingPosition, OrderRequest orderRequest) throws BusinessException {
        if(existingPosition == null){
            throw new BusinessException("existing position is null, action cannot be UPDATE");
        }
        existingPosition.setProduct(orderRequest.getProduct());
        existingPosition.setQuantity(orderRequest.getQuantity());
        return existingPosition;
    }

}
