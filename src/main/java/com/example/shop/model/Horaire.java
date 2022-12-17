package com.example.shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "horaires")
public class Horaire extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int weekNum;

    @NotNull
    private int year;
    @NotNull
    private String day;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime beginTime;

    @NotNull
    private LocalTime endTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "shop_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Shop shop;

}
