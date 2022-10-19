package com.project_4.cookpad_api.entity;

import lombok.*;

import javax.persistence.*;

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
    @GeneratedValue
    private int id;
    @ManyToOne
    @JoinColumn(name = "cate_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private String ingredient;
    private String making;
    private String name;
    private String description;
    private String thumbnails;
    private String detail;
    private int likes;
    private int status;
}
