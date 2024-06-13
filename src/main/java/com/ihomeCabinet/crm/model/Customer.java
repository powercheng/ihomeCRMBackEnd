package com.ihomeCabinet.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
//@Table(name = "customer")

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String contact;
    private String address;
    private String cac;
    private String salesRep;
    //Customer Acquisition Channels
    // 1. san francisco   2. san jose   3. sacramento
    private String salePlace;

    // 1.Created   2.Measured   3.Designed   4.Produced  5.Installed   6.Complete
    private Integer status;
    private String measurer;
    private String originalPhotos;
    private String floorPlan;
    private String refImage;
    private String priceRange;
    private String designer;
    private String DesignDrawing;
    private String designNote;
    private String SalePrice;
    private String OrderFile;
    private String OrderNote;
    private String Note;
    private String finalPhotos;
    private boolean finish;
    private LocalDateTime createdAt;
    private LocalDateTime measuredAt;
    private LocalDateTime designedAt;
    private LocalDateTime orderedAt;
    private LocalDateTime producedAt;
    private LocalDateTime finishedAt;
    private LocalDateTime updatedAt;
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
