package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.services.database.DatabaseTaskScheduler;
import com.vintageforlife.service.services.database.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {
    private final RouteService routeService;
    private final DatabaseTaskScheduler databaseTaskScheduler;

    @Autowired
    public RouteController(RouteService routeService, DatabaseTaskScheduler databaseTaskScheduler) {
        this.routeService = routeService;
        this.databaseTaskScheduler = databaseTaskScheduler;
    }

    @GetMapping(value = "/all", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PLANNER', 'ROLE_CARRIER', 'ROLE_USER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<RouteDTO>> getRoutesForUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        String username = userDetails.getUsername();

        List<RouteDTO> routes = routeService.getRoutesForUser(username);
        return ResponseEntity.ok(routes);
    }

    @GetMapping(value = "/{routeId}", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PLANNER', 'ROLE_CARRIER', 'ROLE_USER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<?> getRouteById(@PathVariable Integer routeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String username = userDetails.getUsername();

        List<RouteDTO> routes = routeService.getRoutesForUser(username);

        for (RouteDTO route : routes) {
            if (route.getId().equals(routeId)) {
                return ResponseEntity.ok(route);
            }
        }

        return new ResponseEntity<>("Route with id " + routeId + " not found for user " + username, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PLANNER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public void testRouting() {
        databaseTaskScheduler.scheduleDatabaseTasks();

        databaseTaskScheduler.runAlgorithmManually();
    }
}
