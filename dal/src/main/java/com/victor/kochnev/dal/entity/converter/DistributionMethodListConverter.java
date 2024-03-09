package com.victor.kochnev.dal.entity.converter;

import com.victor.kochnev.domain.value.object.DistributionMethod;

public class DistributionMethodListConverter extends ListConverter<DistributionMethod> {
    @Override
    protected Class<DistributionMethod> getInnerClass() {
        return DistributionMethod.class;
    }
}
