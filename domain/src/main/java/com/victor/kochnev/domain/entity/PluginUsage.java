package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.value.object.DistributionMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PluginUsage extends BaseEntity {
    /**
     * План распространения, по которому используется плагин
     */
    private DistributionMethod distributionMethod;
    /**
     * Пользователь, использующий плагин
     */
    private User user;
    /**
     * Плагин, котоорый используют
     */
    private Plugin plugin;
}
