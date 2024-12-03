package com.camperfire.marketflow.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final AuthUser authUser;

    public UserPrincipal(AuthUser authUser) {
        this.authUser = authUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(authUser.getUserRole().name()));
    }

    public Long getId() {
        return authUser.getId();
    }

    @Override
    public String getPassword() {
        return authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return authUser.getUsername();
    }

    public String getEmail() { return authUser.getEmail(); }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return authUser.isEnabled();
    }
}
