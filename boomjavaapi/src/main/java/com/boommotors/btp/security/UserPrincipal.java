package com.boommotors.btp.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.boommotors.btp.dto.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author rjanumpally
 */
public class UserPrincipal implements UserDetails {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String moibleno;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Integer id, String firstName, String lastName, String email, String mobileno, String password) {
        this.id = id;
        this.username = firstName + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.moibleno = mobileno;
        this.password = password;

    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMobileNumber(),
                user.getPassword()
        );
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
