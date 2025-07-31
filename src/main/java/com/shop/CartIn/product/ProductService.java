package com.shop.CartIn.product;

import com.shop.CartIn.user.User;
import com.shop.CartIn.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;
    //상품 생성
    @Transactional
    public void create(ProductCreateRequest request, MultipartFile image, Long userId) throws IOException
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Files.createDirectories(Paths.get(uploadDir));

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        image.transferTo(filePath.toFile());

        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .detail(request.getDetail())
                .stock(request.getStock())
                .imgUrl("/upload/" + fileName)
                .user(user)
                .build();

        product.setStatus(ProductStatus.ON_SALE);
        productRepository.save(product);
    }

    //상품 수정
    @Transactional
    public void update(Long id, ProductUpdateRequest request, MultipartFile newImage) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (request != null) {
            if (request.getName() != null) {
                product.setName(request.getName());
            }
            if (request.getPrice() != null) {
                product.setPrice(request.getPrice());
            }
            if (request.getDetail() != null) {
                product.setDetail(request.getDetail());
            }
            if(request.getStock() != null) {
                product.setStock(request.getStock());
            }
            if(request.getStatus() != null) {
                product.setStatus(request.getStatus());
            }
        }
        if(newImage != null && !newImage.isEmpty()) {
            //기존 파일 삭제
            if(product.getImgUrl() != null) {
                File oldFile = new File(uploadDir, product.getImgUrl());
                if(oldFile.exists()) oldFile.delete();
            }

            //새 파일 저장
            String fileName = System.currentTimeMillis() + "_" + newImage.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            newImage.transferTo(filePath.toFile());
            product.setImgUrl("/upload/" + fileName);
        }
    }


    //상품 삭제
    @Transactional
    public void delete(Long id)
    {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        //이미지 파일도 삭제
        if(product.getImgUrl() != null) {
            File file = new File(uploadDir, product.getImgUrl().replace("/upload/",""));
            if(file.exists()) file.delete();
        }
        productRepository.delete(product);
    }


    //상품 단건 조회
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    //상품 전체 조회
    @Transactional(readOnly = true)
    public List<Product> findAll()
    {
        return productRepository.findAll();
    }

}
