package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.*;
import com.vintageforlife.service.routing.Algorithm;
import com.vintageforlife.service.routing.Problem;
import com.vintageforlife.service.routing.Solution;
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


    @Autowired
    public DatabaseTaskScheduler(
            TaskScheduler taskScheduler,
            TransportSettingService transportSettingService,
            Matrix matrix,
            OrderService orderService,
            DistributionCenterService distributionCenterService,
            Algorithm algorithm) {
        this.taskScheduler = taskScheduler;
        this.transportSettingService = transportSettingService;
        this.matrix = matrix;
        this.orderService = orderService;
        this.distributionCenterService = distributionCenterService;
        this.algorithm = algorithm;
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
        List<OrderDTO> orders = orderService.getAllOrdersFromLast24Hours();



        List<TransportSettingDTO> settings = transportSettingService.getTransportSettingsForDistributionCenter(1);


        float truckWidth = Float.parseFloat(settings.stream()
                .filter(setting -> setting.getName().equals("truck_width"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("3"));

        float truckLength = Float.parseFloat(settings.stream()
                .filter(setting -> setting.getName().equals("truck_depth"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("5"));

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

                if (product.getWidth() > truckWidth || product.getDepth() > truckLength) {
                    throw new RuntimeException("Product is too big for truck");
                }
            });
        }

        List<AddressDTO> uniqueAddresses = orders.stream()
                .map(OrderDTO::getAddress)
                .distinct()
                .toList();

        List<String> formattedAddresses = new ArrayList<>(uniqueAddresses.stream()
                .map(AddressDTO::toString).toList());

        // get address from distribution center
        DistributionCenterDTO distributionCenterDTO = distributionCenterService.getDistributionCenterById(1);

        formattedAddresses.add(distributionCenterDTO.getAddress().toString());

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);

        Problem problem = new Problem(matrixResponse, orders, distributionCenterDTO.getAddress());

        Solution solution = algorithm.solve(problem, truckWidth, truckLength);
    }
}
