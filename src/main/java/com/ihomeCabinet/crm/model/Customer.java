package com.ihomeCabinet.crm.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    private String source;
    // 1. san francisco   2. san jose   3. sacramento
    private int belongTo;

    // 1.Create   2.Measure   3.Designing   4.Producing   5.Installing   6.Complete
    private Integer status;
    private String measurer;
    private String houseImage;
    private String floorPlan;
    private String estimateValueRange;
    private String designer;
    private String DesignDrawing;
    private String designNote;
    private String SalePrice;
    private String OrderFile;
    private String OrderNote;
    private String Note;
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
