package com.example.traineeship.domainmodel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    @Test
    @DisplayName("User getters and setters work correctly")
    void testUserGettersAndSetters() {
        User user = new User();
        user.setUsername("john_doe");
        user.setPassword("securePass123");
        user.setRole(Role.STUDENT);
        assertEquals("john_doe", user.getUsername());
        assertEquals("securePass123", user.getPassword());
        assertEquals(Role.STUDENT, user.getRole());
    }

    @Test
    @DisplayName("User returns correct granted authority based on role")
    void testGetAuthorities() {
        User user = new User();
        user.setRole(Role.PROFESSOR);
        Collection<?> authorities = user.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("PROFESSOR")));
    }

    @Test
    @DisplayName("Account status methods return true")
    void testAccountStatusMethods() {
        User user = new User();
        assertTrue(user.isAccountNonExpired(), "Account should be non-expired");
        assertTrue(user.isAccountNonLocked(), "Account should be non-locked");
        assertTrue(user.isCredentialsNonExpired(), "Credentials should be non-expired");
        assertTrue(user.isEnabled(), "Account should be enabled");
    }
}
