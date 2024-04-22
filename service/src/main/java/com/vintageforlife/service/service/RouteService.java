package com.vintageforlife.service.service;

import com.vintageforlife.service.dto.RouteDTO;

import java.util.List;

public interface RouteService {
    List<RouteDTO> getRoutesByUserId(Integer userId);
}
