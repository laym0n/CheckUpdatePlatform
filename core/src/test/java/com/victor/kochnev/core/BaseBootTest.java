package com.victor.kochnev.core;

import com.victor.kochnev.core.configuration.CoreConfiguration;
import com.victor.kochnev.core.integration.MailClient;
import com.victor.kochnev.core.integration.PluginClient;
import com.victor.kochnev.core.integration.TelegramClient;
import com.victor.kochnev.core.repository.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = CoreConfiguration.class)
public abstract class BaseBootTest {
    @MockBean
    protected MailClient mailClient;
    @MockBean
    protected PluginClient pluginClient;
    @MockBean
    protected TelegramClient telegramClient;
    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected PluginRepository pluginRepository;
    @MockBean
    protected PluginUsageRepository pluginUsageRepository;
    @MockBean
    protected WebResourceRepository webResourceRepository;
    @MockBean
    protected WebResourceObservingRepository webResourceObservingRepository;
}
