package com.vintageforlife.service.routing;

import com.vintageforlife.service.routing.genetic.Chromosome;

public record Solution(long algorithmDuration, Chromosome chromosome, int uniqueChromosomesSeen) {
}
