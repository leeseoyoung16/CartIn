package com.shop.CartIn.product;

import com.shop.CartIn.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    private String detail;

    @Column(nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User user;

    @Builder
    public Product(String name, int price, String detail, int stock, String imgUrl, User user) {
        this.name = name;
        this.price = price;
        this.detail = detail;
        this.stock = stock;
        this.imgUrl = imgUrl;
        this.user = user;
    }

    public void setStock(int stock) {
        this.stock = stock;
        this.status = stock == 0 ? ProductStatus.SOLD_OUT : ProductStatus.ON_SALE;
    }

}
