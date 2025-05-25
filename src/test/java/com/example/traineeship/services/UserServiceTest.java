package com.example.traineeship.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.traineeship.domainmodel.User;
import com.example.traineeship.mappers.UserMapper;

public class UserServiceTest {
	

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
	@Test
    @DisplayName("saveUser should encode password and save user")
    void testSaveUser() {
        User user = new User();
        user.setUsername("john");
        user.setPassword("raw123");

        when(passwordEncoder.encode("raw123")).thenReturn("encoded123");

        userService.saveUser(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDAO).save(captor.capture());

        User saved = captor.getValue();
        assertThat(saved.getUsername()).isEqualTo("john");
        assertThat(saved.getPassword()).isEqualTo("encoded123");
    }

    @Test
    @DisplayName("isUserPresent returns true when user exists")
    void testIsUserPresentTrue() {
        User user = new User();
        user.setUsername("john");

        when(userDAO.findByUsername("john")).thenReturn(Optional.of(user));

        boolean present = userService.isUserPresent(user);
        assertThat(present).isTrue();
    }

    @Test
    @DisplayName("isUserPresent returns false when user does not exist")
    void testIsUserPresentFalse() {
        User user = new User();
        user.setUsername("jane");

        when(userDAO.findByUsername("jane")).thenReturn(Optional.empty());

        boolean present = userService.isUserPresent(user);
        assertFalse(present);
    }

    @Test
    @DisplayName("findById returns user when found")
    void testFindByIdSuccess() {
        User user = new User();
        user.setUsername("admin");

        when(userDAO.findByUsername("admin")).thenReturn(Optional.of(user));

        User found = userService.findById("admin");
        assertThat(found).isEqualTo(user);
    }

    @Test
    @DisplayName("findById throws when user not found")
    void testFindByIdThrows() {
        when(userDAO.findByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById("ghost"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("USER_NOT_FOUND");
    }
}
