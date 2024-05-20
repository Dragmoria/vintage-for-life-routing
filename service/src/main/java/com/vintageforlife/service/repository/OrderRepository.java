package com.vintageforlife.service.repository;

import com.vintageforlife.service.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<OrderEntity, Integer> {
    @Query("SELECT o FROM OrderEntity o WHERE DATE(o.date) = :date")
    List<OrderEntity> findByDate(@Param("date") Date date);

    @Query("SELECT o FROM OrderEntity o WHERE o.date BETWEEN :startDate AND :endDate")
    List<OrderEntity> findOrdersBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
