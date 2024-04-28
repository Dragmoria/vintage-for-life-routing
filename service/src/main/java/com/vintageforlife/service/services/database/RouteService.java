package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.RouteDTO;

import java.util.List;

public interface RouteService {
    List<RouteDTO> getRoutesByUserId(Integer userId);

    List<RouteDTO> getAllRoutes();
}
