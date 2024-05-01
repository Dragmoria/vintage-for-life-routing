package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "order")

    public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date", nullable = false)
    @NotBlank(message = "Date can not be null")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CUSTOMER_ID"))
    @NotBlank(message = "Customer id can not be null")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ADDRESS_ID"))
    @NotBlank(message = "Address id can not be null")
    private AddressEntity address;

    @OneToMany(mappedBy = "order")
    private List<OrderEntity> OrderEntity;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}

