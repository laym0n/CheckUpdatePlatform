package com.victor.kochnev.core.service.user;

public class PasswordCoderImpl implements PasswordCoder {
    @Override
    public String encode(String password) {
        return password + "1";
    }
}
