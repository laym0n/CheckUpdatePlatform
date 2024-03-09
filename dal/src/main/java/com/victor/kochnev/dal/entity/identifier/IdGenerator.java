package com.victor.kochnev.dal.entity.identifier;

import com.victor.kochnev.dal.entity.BaseDalEntity;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Objects;
import java.util.UUID;

public class IdGenerator implements IdentifierGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
        BaseDalEntity entity = (BaseDalEntity) o;
        if (Objects.isNull(entity.getId())) {
            return UUID.randomUUID();
        } else {
            return entity.getId();
        }
    }
}
