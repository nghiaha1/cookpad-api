package com.project_4.cookpad_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project_4.cookpad_api.entity.base.BaseEntity;
import com.project_4.cookpad_api.entity.myenum.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private Boolean isVip;
    private Boolean isFamous;
    private int followNumber;
    private String email;
    private String detail;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    private Role role;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;


}
