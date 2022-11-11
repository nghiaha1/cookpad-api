package com.project_4.cookpad_api.entity;

import com.project_4.cookpad_api.entity.myenum.Status;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "cate_id", referencedColumnName = "id", nullable = false)
    private Category category;
    private int eatNumber;
    private long cookingTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "origin_id")
    private Origin origin;
    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", referencedColumnName = "id", nullable = false)
    private List<Ingredient> ingredient = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "making_id", referencedColumnName = "id", nullable = false)
    private List<Making> making = new ArrayList<>();
    private String name;
    private String thumbnails;
    private String detail;
    private int likes;
    private Status status;
}