package com.shop.CartIn.product;

import com.shop.CartIn.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController
{
    private final ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addProduct(@Valid @RequestPart ProductCreateRequest productRequest,
                                           @RequestPart(required = false) MultipartFile image,@AuthenticationPrincipal CustomUserDetails userDetails) throws IOException
    {
        Long userId = userDetails.getUser().getId();
        productService.create(productRequest, image,userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/{productId}", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateProduct(@RequestPart(required = false) ProductUpdateRequest productRequest,
                                              @RequestPart(required = false) MultipartFile image, @PathVariable Long productId) throws IOException
    {
        productService.update(productId, productRequest, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId)
    {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProductAll() {
        List<ProductResponse> products = productService.findAll().stream()
                .map(ProductResponse::new)
                .toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId)
    {
        Product product = productService.findById(productId);
        return ResponseEntity.ok(new ProductResponse(product));
    }

}
