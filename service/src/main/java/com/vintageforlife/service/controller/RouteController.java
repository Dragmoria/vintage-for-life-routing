package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(value = "/route/{userId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<RouteDTO>> getRoutesByUserId(@PathVariable Integer userId) {
        List<RouteDTO> routes = routeService.getRoutesByUserId(userId);
        return ResponseEntity.ok(routes);
    }
}
