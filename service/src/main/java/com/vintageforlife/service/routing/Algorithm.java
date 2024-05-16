package com.vintageforlife.service.routing;

import com.vintageforlife.service.routing.genetic.AlgorithmSettings;

public interface Algorithm {
    Solution solve(Problem problem, AlgorithmSettings settings);
}
