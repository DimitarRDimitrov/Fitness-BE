package com.example.demo.security;

public class SecurityConstraints {
    public static final String SECRET = "mySecret";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 864_000_000L;
}
