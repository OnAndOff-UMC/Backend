package com.onnoff.onnoff.auth.jwt.service;

public interface TokenProvider {
    boolean verifyToken(String token);
}
