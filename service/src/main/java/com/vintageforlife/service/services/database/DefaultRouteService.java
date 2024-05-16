package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.entity.RouteEntity;
import com.vintageforlife.service.entity.RouteStepEntity;
import com.vintageforlife.service.entity.UserEntity;
import com.vintageforlife.service.mapper.RouteMapper;
import com.vintageforlife.service.mapper.RouteStepMapper;
import com.vintageforlife.service.repository.RouteRepository;
import com.vintageforlife.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DefaultRouteService implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final UserRepository userRepository;
    private final RouteStepMapper routeStepMapper;

    @Autowired
    public DefaultRouteService(RouteRepository routeRepository, RouteMapper routeMapper, UserRepository userRepository, RouteStepMapper routeStepMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.userRepository = userRepository;
        this.routeStepMapper = routeStepMapper;
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

    @Override
    public List<RouteDTO> getRoutesForUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " does not exist"));

        return userEntity.getRoutes().stream()
                .map(routeEntity -> {
                    RouteDTO routeDTO = routeMapper.toDTO(routeEntity);

                    routeDTO.setRouteSteps(routeEntity.getRouteSteps().stream()
                            .map(routeStepMapper::toDTO)
                            .collect(Collectors.toList()));

                    return routeDTO;
                })
                .toList();
    }

    @Override
    public void saveNewRoute(RouteDTO routeDTO) {
        RouteEntity routeEntity = routeMapper.toEntity(routeDTO);

        routeRepository.save(routeEntity);

        List<RouteStepEntity> routeStepEntities = new ArrayList<>();

        for (RouteStepDTO routeStepDTO: routeDTO.getRouteSteps()) {
            RouteStepEntity routeStepEntity = routeStepMapper.toEntity(routeStepDTO);
            routeStepEntity.setRoute(routeEntity);
            routeStepEntities.add(routeStepEntity);
        }

        routeEntity.setRouteSteps(routeStepEntities);

        routeRepository.save(routeEntity);
    }
}
