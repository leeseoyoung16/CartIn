package com.shop.CartIn.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ProductCreateRequest
{
    @NotBlank(message = "상품명은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "상품 설명은 필수 입력 항목입니다.")
    private String detail;

    @Positive(message = "상품 가격은 0원 이상이여야 합니다.")
    private int price;

    @Positive(message = "상품 재고는 0개 이상이여야 합니다.")
    private int stock;

}
