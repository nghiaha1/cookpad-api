package com.project_4.cookpad_api.entity;

import com.project_4.cookpad_api.entity.myenum.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "origins")
public class Origin implements Comparable<Origin>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String code;
    @Column(unique = true)
    private String country;
    private Status status;

    @Override
    public int compareTo(Origin o) {
        return this.country.compareTo(o.country);
    }
}
