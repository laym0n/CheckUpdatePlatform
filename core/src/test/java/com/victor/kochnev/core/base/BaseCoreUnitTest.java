package com.victor.kochnev.core.base;

import com.victor.kochnev.core.converter.*;
import com.victor.kochnev.core.repository.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseCoreUnitTest {
    @Mock
    protected PluginRepository pluginRepository;
    @Mock
    protected PluginUsageRepository pluginUsageRepository;
    @Mock
    protected UserRepository userRepository;
    @Mock
    protected WebResourceRepository webResourceRepository;
    @Mock
    protected WebResourceObservingRepository webResourceObservingRepository;
    @Spy
    protected DomainUserMapper domainUserMapper = new DomainUserMapperImpl();
    @Spy
    protected DomainWebResourceMapper domainWebResourceMapper = new DomainWebResourceMapperImpl();
    @Spy
    protected DomainWebResourceObservingMapper domainWebResourceObservingMapper = new DomainWebResourceObservingMapperImpl(domainWebResourceMapper);
}
