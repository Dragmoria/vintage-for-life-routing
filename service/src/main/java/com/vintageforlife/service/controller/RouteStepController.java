package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.SimpleRouteStepDTO;
import com.vintageforlife.service.exception.NotAuthorizedException;
import com.vintageforlife.service.services.database.RouteStepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/routesteps")
public class RouteStepController {
    private final RouteStepService routeStepService;

    @Autowired
    public RouteStepController(RouteStepService routeStepService) {
        this.routeStepService = routeStepService;
    }

    @PostMapping(value = "/setcompleted", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_PLANNER', 'ROLE_CARRIER', 'ROLE_USER')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Boolean> setRouteStepCompleted(@RequestBody @Valid SimpleRouteStepDTO simpleRouteStepDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        try {
            Boolean result = routeStepService.changeCompleted(simpleRouteStepDTO, username);
            return ResponseEntity.ok(result);
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(403).body(false);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}
