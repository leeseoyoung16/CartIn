package com.shop.CartIn.order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequest {

    private List<Long> productIds;

    private List<Integer> quantities;
}
