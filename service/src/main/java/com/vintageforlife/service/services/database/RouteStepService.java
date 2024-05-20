package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.SimpleRouteStepDTO;

public interface RouteStepService {
    Boolean changeCompleted(SimpleRouteStepDTO simpleRouteStepDTO, String username);
}
