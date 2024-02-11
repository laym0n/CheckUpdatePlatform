package com.victor.kochnev.core.base;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.converter.DomainUserMapperImpl;
import com.victor.kochnev.core.converter.DomainWebResourceMapper;
import com.victor.kochnev.core.converter.DomainWebResourceMapperImpl;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.core.repository.WebResourceRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseCoreTest {
    @Mock
    protected PluginRepository pluginRepository;
    @Mock
    protected PluginUsageRepository pluginUsageRepository;
    @Mock
    protected UserRepository userRepository;
    @Mock
    protected WebResourceRepository webResourceRepository;
    @Spy
    protected DomainUserMapper domainUserMapper = new DomainUserMapperImpl();
    @Spy
    protected DomainWebResourceMapper domainWebResourceMapper = new DomainWebResourceMapperImpl();
}
