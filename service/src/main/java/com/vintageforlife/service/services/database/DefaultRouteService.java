package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.DistributionCenterDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.dto.RouteStepDTO;
import com.vintageforlife.service.entity.*;
import com.vintageforlife.service.mapper.OrderMapper;
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
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final DistributionCenterService distributionCenterService;

    @Autowired
    public DefaultRouteService(RouteRepository routeRepository,
                               RouteMapper routeMapper,
                               UserRepository userRepository,
                               RouteStepMapper routeStepMapper,
                               OrderService orderService,
                               OrderMapper orderMapper,
                               DistributionCenterService distributionCenterService) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
        this.userRepository = userRepository;
        this.routeStepMapper = routeStepMapper;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.distributionCenterService = distributionCenterService;
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
                            .map(routeStepEntity -> {
                                RouteStepDTO routeStepDTO = routeStepMapper.toDTO(routeStepEntity);

                                // Load the OrderEntity and set it on the RouteStepDTO
                                if (routeStepEntity.getOrder() != null) {
                                    OrderEntity orderEntity = orderService.getOrderEntity(routeStepEntity.getOrder().getId());
                                    routeStepDTO.setOrder(orderMapper.toDTO(orderEntity));
                                }
                                else {
                                    DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

                                    OrderDTO orderDTO = new OrderDTO();
                                    orderDTO.setAddress(distributionCenterDTO.getAddress());
                                    orderDTO.setRetour(false);
                                    routeStepDTO.setOrder(orderDTO);
                                }

                                return routeStepDTO;
                            })
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

            if (routeStepDTO.getOrder() != null) {
                OrderEntity order = orderService.getOrderEntity(routeStepDTO.getOrder().getId());
                routeStepEntity.setOrder(order);
            }

            routeStepEntities.add(routeStepEntity);
        }

        routeEntity.setRouteSteps(routeStepEntities);

        routeRepository.save(routeEntity);
    }
}
