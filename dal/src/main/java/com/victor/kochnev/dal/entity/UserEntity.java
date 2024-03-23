package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.entity.converter.UserRoleListConverter;
import com.victor.kochnev.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserEntity extends BaseDalEntity {
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "telegram_info")
    private String telegramInfo;
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;
    @Column(name = "roles", nullable = false)
    @Convert(converter = UserRoleListConverter.class)
    private List<UserRole> roles;
}
