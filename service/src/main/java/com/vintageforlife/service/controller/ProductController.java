package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.ProductDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class ProductController {
    @PostMapping("/products")
    public String createProduct(@RequestBody ProductDTO productDTO) {
        return "Product created";
    }
}
