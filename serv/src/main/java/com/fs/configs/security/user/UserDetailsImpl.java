package com.fs.configs.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fs.api.user.domain.User;
import com.fs.api.user.dto.UserDto;
import com.fs.api.user.dto.UserDtoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

    private String username;
    private String password;
    @Builder.Default
    private List<String> roles = new ArrayList<>();
    private UserDto.Simple userDto;

    public static UserDetailsImpl of(User user) {
        return UserDetailsImpl.builder()
                .username(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRoles())
                .userDto(UserDtoMapper.INSTANCE.getSimple(user))
                .build();
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
