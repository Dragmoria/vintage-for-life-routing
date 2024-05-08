package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.mapper.RouteMapper;
import com.vintageforlife.service.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultRouteService implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    @Autowired
    public DefaultRouteService(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    @Override
    public List<RouteDTO> getRoutesByUserId(Integer userId) {
        return routeRepository.findByUserId(userId).stream()
                .map(routeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RouteDTO> getAllRoutes() {
        return StreamSupport.stream(routeRepository.findAll().spliterator(), false)
                .map(routeMapper::toDTO)
                .collect(Collectors.toList());
    }
}
