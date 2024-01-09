package com.victor.kochnev.rest.presenters.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.kochnev.base.BaseIntegrationTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@AutoConfigureMockMvc(addFilters = false)
public abstract class BaseControllerTest extends BaseIntegrationTest {
    @Autowired
    private MockMvc mvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public MvcResult post(String uri, Object body) {
        var request = getRequest(uri, body, HttpMethod.POST);
        return mvc.perform(request).andReturn();
    }

    protected void assertHttpStatusOk(MvcResult mvcResult) {
        assertHttpStatus(mvcResult, HttpStatus.OK);
    }

    protected void assertHttpStatus(MvcResult mvcResult, HttpStatus httpStatus) {
        var response = mvcResult.getResponse();
        Assertions.assertEquals(httpStatus.value(), response.getStatus());
    }

    private MockHttpServletRequestBuilder getRequest(String uri, Object request, HttpMethod httpMethod) throws JsonProcessingException {
        return MockMvcRequestBuilders.request(httpMethod, uri)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
