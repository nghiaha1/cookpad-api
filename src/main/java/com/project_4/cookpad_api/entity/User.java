package com.project_4.cookpad_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project_4.cookpad_api.entity.base.BaseEntity;
import com.project_4.cookpad_api.entity.myenum.Status;
import lombok.*;

import javax.persistence.*;

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
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonManagedReference
    private Role role;
    @Enumerated(EnumType.ORDINAL)
    private Status status;
}
