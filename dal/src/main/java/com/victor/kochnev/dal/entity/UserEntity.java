package com.victor.kochnev.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Data
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
