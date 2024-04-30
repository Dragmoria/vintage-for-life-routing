package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.NodeDTO;
import com.vintageforlife.service.routing.Graph;
import com.vintageforlife.service.routing.RoutingService;
import com.vintageforlife.service.services.database.OrderService;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/public/testAlgorithm")
    public ResponseEntity<String> testAlgorithm() {
        Graph graph = routingService.createGraph();

        routingService.calculateRoute(graph);

//        routingService.calculateRoute(graph).thenAccept(result -> {
//            System.out.println("Succes???");
//        });

        return new ResponseEntity<>("Algorithm started", HttpStatus.ACCEPTED);
    }
}
