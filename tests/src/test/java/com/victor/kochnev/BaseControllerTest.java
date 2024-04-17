package com.victor.kochnev;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.dal.converter.EntityUserMapper;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.rest.presenters.security.service.JwtService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

@AutoConfigureMockMvc
public abstract class BaseControllerTest extends BaseBootTest {
    protected static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected EntityUserMapper entityUserMapper;
    @Autowired
    protected DomainUserMapper domainUserMapper;
    @Autowired
    protected JwtService jwtService;

    @SneakyThrows
    public MvcResult post(String uri, Object body) {
        var request = getRequest(uri, body, HttpMethod.POST, HttpHeaders.EMPTY);
        return mvc.perform(request).andReturn();
    }

    @SneakyThrows
    public MvcResult post(String uri, Object body, HttpHeaders httpHeaders) {
        var request = getRequest(uri, body, HttpMethod.POST, httpHeaders);
        return mvc.perform(request).andReturn();
    }

    @SneakyThrows
    public MvcResult get(String uri, Object body) {
        var request = getRequest(uri, body, HttpMethod.GET, HttpHeaders.EMPTY);
        return mvc.perform(request).andReturn();
    }

    @SneakyThrows
    public MvcResult get(String uri, Object body, HttpHeaders httpHeaders) {
        var request = getRequest(uri, body, HttpMethod.GET, httpHeaders);
        return mvc.perform(request).andReturn();
    }

    @SneakyThrows
    public MvcResult put(String uri, Object body) {
        var request = getRequest(uri, body, HttpMethod.PUT, HttpHeaders.EMPTY);
        return mvc.perform(request).andReturn();
    }

    @SneakyThrows
    public MvcResult put(String uri, Object body, HttpHeaders httpHeaders) {
        var request = getRequest(uri, body, HttpMethod.PUT, httpHeaders);
        return mvc.perform(request).andReturn();
    }

    protected void assertHttpStatusOk(MvcResult mvcResult) {
        assertHttpStatus(mvcResult, HttpStatus.OK);
    }

    protected void assertHttpStatus(MvcResult mvcResult, HttpStatus httpStatus) {
        var response = mvcResult.getResponse();
        Assertions.assertEquals(httpStatus.value(), response.getStatus());
    }

    private MockHttpServletRequestBuilder getRequest(String uri, Object request, HttpMethod httpMethod, HttpHeaders httpHeaders) throws JsonProcessingException {
        httpHeaders = httpHeaders == null ? HttpHeaders.EMPTY : httpHeaders;
        return MockMvcRequestBuilders.request(httpMethod, uri)
                .headers(httpHeaders)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
    }

    protected <T> T getResponseDto(MvcResult mvcResult, Class<T> clazz) {
        try {
            String content = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
            return objectMapper.readValue(content, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    protected HttpHeaders prepareSimpleUserHeaders(UserEntity userEntity) {
        User user = entityUserMapper.mapToDomain(userEntity);
        UserSecurity userSecurity = domainUserMapper.mapToSecurityUser(user);
        String token = jwtService.generateAccessToken(userSecurity);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        return httpHeaders;
    }

    protected HttpHeaders preparePluginHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Basic bmFtZTphY2Nlc3NUb2tlbg==");
        return httpHeaders;
    }
}
