package com.victor.kochnev.dal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @GeneratedValue(generator = "platform")
    @GenericGenerator(name = "platform",
            parameters = @Parameter(name = "prefix", value = "prod"),
            strategy = "com.victor.kochnev.dal.entity.identifier.IdGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @CreatedDate
    @Column(name = "create_date")
    private ZonedDateTime createDate;
    @Column(name = "last_change_date")
    private ZonedDateTime lastChangeDate;
    @Version
    @Column(name = "version")
    private Long version;

    @PreUpdate
    void onUpdate() {
        setLastChangeDate(ZonedDateTime.now());
    }

    @PrePersist
    void onCreate() {
        ZonedDateTime now = ZonedDateTime.now();
        setCreateDate(now);
        setLastChangeDate(now);
    }
}
