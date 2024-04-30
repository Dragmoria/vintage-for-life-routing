package com.vintageforlife.service.dto;

import com.vintageforlife.service.routing.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NodeDTO {
    private String id;
    private List<EdgeDTO> edges;

    public NodeDTO(Node node) {
        this.id = node.getId();
        this.edges = node.getEdges().stream()
                .map(EdgeDTO::new)
                .collect(Collectors.toList());
    }
}
