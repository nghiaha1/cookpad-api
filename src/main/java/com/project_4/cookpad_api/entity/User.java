package com.project_4.cookpad_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project_4.cookpad_api.entity.base.BaseEntity;
import com.project_4.cookpad_api.entity.myenum.Status;
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
    @Column(nullable = false)
    private String password;
    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private int followNumber;
    @Column(unique = true)
    private String email;
    private String detail;
    private LocalDateTime dob;
    private String gender;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
