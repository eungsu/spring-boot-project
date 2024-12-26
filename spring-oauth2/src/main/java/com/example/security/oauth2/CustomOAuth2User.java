package com.example.security.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.model.ERole;
import com.example.model.User;
import com.example.security.LoginUser;

public class CustomOAuth2User extends LoginUser implements OAuth2User  {
    private Map<String, Object> attributes;
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(User user, Map<String, Object> attributes) {
        super(user.getId(), user.getNickname());
        this.attributes = attributes;
        this.name = user.getUsername();
        this.authorities = List.of(new SimpleGrantedAuthority(ERole.ROLE_USER.name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return name;
    }
    
}
