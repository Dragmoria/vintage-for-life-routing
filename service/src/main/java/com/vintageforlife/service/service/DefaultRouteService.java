package com.vintageforlife.service.service;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.mapper.RouteMapper;
import com.vintageforlife.service.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultRouteService implements RouteService {
    private final RouteRepository routeRepository;

    @Autowired
    public DefaultRouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public List<RouteDTO> getRoutesByUserId(Integer userId) {
        return routeRepository.findByUserId(userId).stream()
                .map(RouteMapper::makeRouteDTO)
                .collect(Collectors.toList());
    }
}
