package com.vintageforlife.service.services.googleApi;

import java.util.List;

public interface Matrix {
    MatrixResponse getMatrix(List<String> locations);
}
