package org.example.domain;


import java.math.BigDecimal;
import java.util.Objects;

public class PositionResponse {

    private String product;


    private BigDecimal quantity;

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

    @Override
    public String toString() {
        return product + ":" + quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionResponse that = (PositionResponse) o;
        return Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    public PositionResponse(PositionDetail positionDetail){
        this.product = positionDetail.getProduct();
        this.quantity = positionDetail.getQuantity();
    }

}
