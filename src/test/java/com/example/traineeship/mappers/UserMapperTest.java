package com.example.traineeship.mappers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import com.example.traineeship.domainmodel.Role;
import com.example.traineeship.domainmodel.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("findByUsername returns the correct user")
    void testFindByUsername() {
        User user = new User();
        user.setUsername("alice");
        user.setPassword("secret123");
        user.setRole(Role.STUDENT);
        userMapper.save(user);
        Optional<User> foundUser = userMapper.findByUsername("alice");
        assertTrue(foundUser.isPresent(), "User should be found");
        assertEquals("alice", foundUser.get().getUsername());
        assertEquals("secret123", foundUser.get().getPassword());
        assertEquals(Role.STUDENT, foundUser.get().getRole());
    }

    @Test
    @DisplayName("findByUsername returns empty when user does not exist")
    void testFindByUsernameNotFound() {
        Optional<User> result = userMapper.findByUsername("nonexistent");
        assertTrue(result.isEmpty(), "Result should be empty for non-existent user");
    }
}
