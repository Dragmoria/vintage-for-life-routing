package com.vintageforlife.service.services.database;

import com.vintageforlife.service.dto.AddressDTO;
import com.vintageforlife.service.dto.OrderDTO;
import com.vintageforlife.service.dto.TransportSettingDTO;
import com.vintageforlife.service.services.googleApi.Matrix;
import com.vintageforlife.service.services.googleApi.MatrixResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

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


    @Autowired
    public DatabaseTaskScheduler(
            TaskScheduler taskScheduler,
            TransportSettingService transportSettingService,
            Matrix matrix,
            OrderService orderService) {
        this.taskScheduler = taskScheduler;
        this.transportSettingService = transportSettingService;
        this.matrix = matrix;
        this.orderService = orderService;
    }

    public void scheduleDatabaseTasks() {
        Runnable task = this::runAlgorithm;

        List<TransportSettingDTO> transportSettings = transportSettingService.getTransportSettingsForDistributionCenter(1);

        Optional<TransportSettingDTO> cronTime = transportSettingService.getTransportSettingsForDistributionCenter(1)
                .stream()
                .filter(transportSettingDTO -> transportSettingDTO.getName().equals("cron_ts_routing"))
                .findFirst();

        if (cronTime.isPresent()) {
            String temp = cronTime.get().getValue();

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

        List<AddressDTO> uniqueAddresses = orders.stream()
                .map(OrderDTO::getAddress)
                .distinct()
                .toList();

        List<String> formattedAddresses = uniqueAddresses.stream()
                .map(address -> address.getStreet() + " " + address.getHouseNumber() + ", " + address.getPostCode() + " " + address.getCity() + ", Netherlands").toList();

        MatrixResponse matrixResponse = matrix.getMatrix(formattedAddresses);
    }
}
