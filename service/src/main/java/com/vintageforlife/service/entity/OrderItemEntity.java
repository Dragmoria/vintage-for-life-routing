package com.vintageforlife.service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "order_item")

public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ID"))
    @NotBlank(message = "Order id can not be null")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_ID"))
    @NotBlank(message = "Product id can not be null")
    private ProductEntity product;

    @Column(name = "retour", nullable = false)
    @NotBlank(message = "Retour can not be null")
    private Boolean retour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Boolean getRetour() {
        return retour;
    }

    public void setRetour(Boolean retour) {
        this.retour = retour;
    }
}
