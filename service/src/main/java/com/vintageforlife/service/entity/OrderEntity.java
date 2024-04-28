package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @Column
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    @NonNull
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", foreignKey = @ForeignKey(name = "FK_ORDER_TO_CUSTOMER_ID"))
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_TO_ADDRESS_ID"))
    @NonNull
    private AddressEntity address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;
}
