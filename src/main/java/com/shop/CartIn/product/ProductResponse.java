package com.shop.CartIn.product;

import lombok.Getter;

@Getter
public class ProductResponse
{
    private Long id;
    private String name;
    private String detail;
    private ProductStatus status;
    private int price;
    private int stock;
    private String imgUrl;

    public ProductResponse(Product product)
    {
        this.id = product.getId();
        this.name = product.getName();
        this.detail = product.getDetail();
        this.status = product.getStatus();
        this.price = product.getPrice();
        this.stock = product.getStock();
        this.imgUrl = product.getImgUrl();
    }
}
