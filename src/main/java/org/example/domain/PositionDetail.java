package org.example.domain;


import java.math.BigDecimal;

public class PositionDetail {
    private Integer id;

    private String tradeId;

    private Integer version;

    private String product;

    private BigDecimal quantity;

    private ActionEnum action;

    private DirectionEnum direction;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public ActionEnum getAction() {
        return action;
    }

    public void setAction(ActionEnum action) {
        this.action = action;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public PositionDetail(OrderRequest orderRequest){
        this.id = orderRequest.getId();
        this.tradeId = orderRequest.getTradeId();
        this.version = orderRequest.getVersion();
        this.product = orderRequest.getProduct();
        this.quantity = orderRequest.getQuantity();
    }
}
