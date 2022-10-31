package com.project_4.cookpad_api.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue
    private int id;
    private String name;
}
