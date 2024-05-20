package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.SimpleRouteStepDTO;
import com.vintageforlife.service.entity.RouteStepEntity;
import com.vintageforlife.service.exception.NotAuthorizedException;
import com.vintageforlife.service.repository.RouteStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultRouteStepService implements RouteStepService {
    private final RouteStepRepository routeStepRepository;

    @Autowired
    public DefaultRouteStepService(RouteStepRepository routeStepRepository) {
        this.routeStepRepository = routeStepRepository;
    }


    @Override
    public Boolean changeCompleted(SimpleRouteStepDTO simpleRouteStepDTO, String username) {
        RouteStepEntity routeStepEntity = routeStepRepository.findById(simpleRouteStepDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Route step with id " + simpleRouteStepDTO.getId() + " does not exist"));

        if (!routeStepEntity.getRoute().getUser().getUsername().equals(username)) {
            throw new NotAuthorizedException("User " + username + " is not authorized to change the status of route step with id " + simpleRouteStepDTO.getId());
        }

        routeStepEntity.setCompleted(simpleRouteStepDTO.getCompleted());
        routeStepRepository.save(routeStepEntity);

        return true;
    }
}
