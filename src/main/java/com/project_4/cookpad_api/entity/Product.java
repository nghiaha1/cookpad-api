package com.project_4.cookpad_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project_4.cookpad_api.entity.base.BaseEntity;
import com.project_4.cookpad_api.entity.myenum.Status;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;
    private int quantity;
    private String image;
    private String description;
    @Lob
    private String detail; // text
    private String thumbnails;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_id")
    private Origin origin;
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "category")
    private Set<Category> categories;
}
