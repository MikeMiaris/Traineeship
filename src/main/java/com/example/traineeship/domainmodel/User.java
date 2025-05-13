package com.example.traineeship.domainmodel;

import java.util.Collection;
import java.util.Collections;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity
@Table(name = "users")
public class User implements UserDetails{
    
	@Id    
    @Column(name = "username", unique =true)
	@NotBlank(message = "Username can not be empty.")
	private String username;
	
    @Column(name = "password")
    @NotBlank(message = "User's password can not be empty.")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;
    
    // D: constructor only to be called by Professors,Students,etc
    public User() {
    	super();
    }
    
    // D: Authorities only handled in User
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
	     return Collections.singletonList(authority);
	}
	
    @Override
	public boolean isAccountNonExpired() {
		return true;
	}
    
    @Override
	public boolean isAccountNonLocked() {
		return true;
	}
    
    @Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
    
    @Override
	public boolean isEnabled() {
		return true;
	}
    
    // E: Maybe serialize/de-serialize Users
    // Functions 
	@Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
}
