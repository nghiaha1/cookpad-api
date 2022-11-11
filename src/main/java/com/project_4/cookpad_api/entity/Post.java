package com.project_4.cookpad_api.entity;

import com.project_4.cookpad_api.entity.myenum.Status;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", referencedColumnName = "id", nullable = false)
    private Set<Category> category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private List<Ingredient> ingredient = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
    private List<Making> making = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_likes", referencedColumnName = "id", nullable = false)
    private Set<User> userIdLikes;

    private String name;
    private String description;
    private String thumbnails;
    private String detail;
    private int likes;
    private Status status;
}
