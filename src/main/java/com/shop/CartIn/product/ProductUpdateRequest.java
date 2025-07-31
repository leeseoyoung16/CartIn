package com.shop.CartIn.product;
import lombok.Getter;

@Getter
public class ProductUpdateRequest
{
    private String name;
    private String detail;
    private Integer price;
    private Integer stock;
    private ProductStatus status;
}
