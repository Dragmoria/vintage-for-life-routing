package com.vintageforlife.service.controller;

import com.vintageforlife.service.services.database.OrderService;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;
    private final Matrix matrix;

    @Autowired
    public OrderController(OrderService orderService, Matrix matrix) {
        this.orderService = orderService;
        this.matrix = matrix;
    }

    @GetMapping("/public/matrix/")
    public ResponseEntity<MatrixResponse> getMatrix() {
        return ResponseEntity.ok(matrix.getMatrix(new String[]{"1.0,1.0"}, new String[]{"2.0,2.0"}));
    }
}
