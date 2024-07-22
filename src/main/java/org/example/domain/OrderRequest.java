package org.example.domain;


import java.math.BigDecimal;

public class OrderRequest implements Comparable<OrderRequest>{

    private int id;

    private String tradeId;

    private int version;

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

    @Override
    public int compareTo(OrderRequest o) {
        return this.id - o.getId();
    }

    public OrderRequest(int id, String tradeId, int version, String product, BigDecimal quantity, ActionEnum action, DirectionEnum direction) {
        this.id = id;
        this.tradeId = tradeId;
        this.version = version;
        this.product = product;
        this.quantity = quantity;
        this.action = action;
        this.direction = direction;
    }
}
