package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.TaskDecision;
import com.victor.kochnev.domain.enums.TaskType;
import com.victor.kochnev.domain.value.object.PluginDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Task extends BaseEntity {
    /**
     * Обновляемое описание плагина
     */
    private PluginDescription description;
    /**
     * Тип запроса обновления
     */
    private TaskType type;
    /**
     * Принятое решение
     */
    private TaskDecision decision;
    /**
     * Комментарий сотрудника, принявшего решение
     */
    private String comment;
    /**
     * Плагин
     */
    private Plugin plugin;
}
