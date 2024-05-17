package com.vintageforlife.service.routing;

import com.vintageforlife.service.routing.genetic.Chromosome;
import lombok.AllArgsConstructor;
import lombok.Getter;

public record Solution(long algorithmDuration, Chromosome chromosome, int uniqueChromosomesSeen) {
}
