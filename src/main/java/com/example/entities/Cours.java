package com.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Cours
{
    @Id
    private int id;
    private String name;
    private String coursPrice;
    @Column(length = 500)
    private String description;
    @OneToMany(mappedBy = "cours")
    private List<OrderItem> orderItem;
}
