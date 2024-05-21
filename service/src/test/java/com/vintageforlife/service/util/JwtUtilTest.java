package com.vintageforlife.service.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtUtilTest {
    private final JwtUtil jwtUtil;
    private UserDetails userDetails;

    @Autowired
    public JwtUtilTest(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @BeforeEach
    public void setUp() {
        userDetails = new User("testUser", "testPassword", Collections.emptyList());
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtil.generateToken(userDetails, new HashMap<>());
        assertNotNull(token);
    }

    @Test
    public void testExtractAllClaims() {
        String token = jwtUtil.generateToken(userDetails, new HashMap<>());
        Claims claims = jwtUtil.extractAllClaims(token);
        assertEquals(userDetails.getUsername(), claims.getSubject());
    }

    @Test
    public void testIsTokenExpired() {
        String token = jwtUtil.generateToken(userDetails, new HashMap<>());
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    public void testExtractUsername() {
        String token = jwtUtil.generateToken(userDetails, new HashMap<>());
        String username = jwtUtil.extractUsername(token);
        assertEquals(userDetails.getUsername(), username);
    }
}
