package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.DistributionPlanType;
import com.victor.kochnev.domain.util.DateTimeUtils;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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

    /**
     * Проверяет можно ли использовать плагин
     *
     * @param when момент времени, в котором происходит проверка
     * @return true, если плагин можно использовать в момент времени when, иначе - false
     */
    public boolean canUse(ZonedDateTime when) {
        when = when.truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime startUsage = getCreateDate().truncatedTo(ChronoUnit.SECONDS);
        DistributionPlanType distributionType = distributionMethod.type();
        return (distributionType == DistributionPlanType.PURCHASE && DateTimeUtils.isDateAfterOrEqual(when, startUsage)
                || distributionType == DistributionPlanType.SUBSCRIBE && DateTimeUtils.isDateInRange(when, startUsage, startUsage.plus(distributionMethod.duration())));
    }
}
