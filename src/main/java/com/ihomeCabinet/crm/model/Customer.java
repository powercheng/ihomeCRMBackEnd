package com.ihomeCabinet.crm.model;

import com.ihomeCabinet.crm.tools.JsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

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
    @Convert(converter = JsonConverter.class)
    private List<CustomerFile> measureFiles;
    private String priceRange;
    private String designer;
    @Convert(converter = JsonConverter.class)
    private List<CustomerFile> designFiles;
    private String salePrice;
    @Convert(converter = JsonConverter.class)
    private List<CustomerFile> orderFiles;
    private String orderNote;
    private String note;
    @Convert(converter = JsonConverter.class)
    private List<CustomerFile> finalFiles;
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
