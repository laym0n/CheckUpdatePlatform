package com.victor.kochnev.integration.plugin.client.factory;

import com.victor.kochnev.integration.plugin.client.WebResourceClient;

public interface WebResourceClientFactory {
    WebResourceClient webResourceClient(String baseUrl);
}
