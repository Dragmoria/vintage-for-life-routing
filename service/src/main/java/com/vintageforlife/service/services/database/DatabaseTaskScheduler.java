package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.*;
import com.vintageforlife.service.routing.Algorithm;
import com.vintageforlife.service.routing.Node;
import com.vintageforlife.service.routing.Problem;
import com.vintageforlife.service.routing.Solution;
import com.vintageforlife.service.routing.genetic.AlgorithmSettings;
import com.vintageforlife.service.routing.genetic.Truck;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Lazy
@Component
@EnableScheduling
public class DatabaseTaskScheduler {
    private final TaskScheduler taskScheduler;
    private final TransportSettingService transportSettingService;
    private final Matrix matrix;
    private final OrderService orderService;
    private final DistributionCenterService distributionCenterService;
    private final Algorithm algorithm;
    private final RouteService routeService;


    @Autowired
    public DatabaseTaskScheduler(
            TaskScheduler taskScheduler,
            TransportSettingService transportSettingService,
            Matrix matrix,
            OrderService orderService,
            DistributionCenterService distributionCenterService,
            Algorithm algorithm,
            RouteService routeService) {
        this.taskScheduler = taskScheduler;
        this.transportSettingService = transportSettingService;
        this.matrix = matrix;
        this.orderService = orderService;
        this.distributionCenterService = distributionCenterService;
        this.algorithm = algorithm;
        this.routeService = routeService;
    }

    public void scheduleDatabaseTasks() {
        Runnable task = this::runAlgorithm;

        List<TransportSettingDTO> transportSettings = transportSettingService.getTransportSettingsForDistributionCenter(1);

        Optional<TransportSettingDTO> cronTime = transportSettings
                .stream()
                .filter(transportSettingDTO -> transportSettingDTO.getName().equals("cron_ts_routing"))
                .findFirst();

        if (cronTime.isPresent()) {
            CronTrigger cronTrigger = new CronTrigger(cronTime.get().getValue());

            taskScheduler.schedule(task, cronTrigger);
        } else {
            CronTrigger cronTrigger = new CronTrigger("0 0 0 * * *");

            taskScheduler.schedule(task, cronTrigger);
        }
    }

    public void runAlgorithmManually() {
        runAlgorithm();
    }

    private void runAlgorithm() {
//        List<OrderDTO> orders = orderService.getAllOrdersFromLast24Hours();
        List<OrderDTO> orders = orderService.getAllOrders();
        if (orders.isEmpty()) return;

        List<TransportSettingDTO> settings = transportSettingService.getTransportSettingsForDistributionCenter(1);
        AlgorithmSettings algorithmSettings = new AlgorithmSettings(settings);

        rotateProducts(orders, algorithmSettings);

        List<AddressDTO> uniqueAddresses = getUniqueAdresses(orders);
        List<String> formattedAddresses = getFormattedAdresses(uniqueAddresses);
        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);
        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);

        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        Solution solution = algorithm.solve(problem, algorithmSettings);
        saveRoutes(solution);
    }

    private void saveRoutes(Solution solution) {
        List<RouteDTO> routeDTOS = solution.chromosome().getRoutes();

        for (RouteDTO route: routeDTOS) {
            routeService.saveNewRoute(route);
        }
    }

    private List<String> getFormattedAdresses(List<AddressDTO> uniqueAddresses) {
        List<String> formattedAddresses = new ArrayList<>(uniqueAddresses.stream()
                .map(AddressDTO::toString).toList());

        return formattedAddresses;
    }

    private List<AddressDTO> getUniqueAdresses(List<OrderDTO> orders) {
        return orders.stream()
                .map(OrderDTO::getAddress)
                .distinct()
                .toList();
    }

    private void rotateProducts(List<OrderDTO> orders, AlgorithmSettings algorithmSettings) {
        for (OrderDTO order : orders) {
            order.getOrderItems().forEach(orderItem -> {
                orderItem.setOrder(order);
                ProductDTO product = orderItem.getProduct();

                if (product.getDepth() > product.getHeight()) {
                    product.setDepth(product.getHeight());
                }

                if (product.getWidth() > product.getHeight()) {
                    product.setWidth(product.getHeight());
                }

                if (product.getWidth() > algorithmSettings.getTruckWidth() || product.getDepth() > algorithmSettings.getTruckLength()) {
                    throw new RuntimeException("Product is too big for truck");
                }
            });
        }
    }
}
