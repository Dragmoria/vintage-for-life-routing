package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.NodeDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.RoutingService;
import com.vintageforlife.service.services.database.OrderService;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;
    private final Matrix matrix;
    private final RoutingService routingService;

    @Autowired
    public OrderController(OrderService orderService, Matrix matrix, RoutingService routingService) {
        this.orderService = orderService;
        this.matrix = matrix;
        this.routingService = routingService;
    }

    @GetMapping("/public/matrix/")
    public ResponseEntity<List<NodeDTO>> getMatrix() {
        Graph graph = routingService.createGraph();

        return ResponseEntity.ok(graph.getNodes().stream()
                .map(NodeDTO::new)
                .collect(Collectors.toList()));
    }

    @PostMapping("/public/orders/")
    public ResponseEntity<List<OrderDTO>> createOrders(@RequestBody List<OrderDTO> order) {
        return ResponseEntity.ok(null);
    }
}
