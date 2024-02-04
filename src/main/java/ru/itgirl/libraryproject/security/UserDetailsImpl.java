package ru.itgirl.libraryproject.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.itgirl.libraryproject.model.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String password, List<GrantedAuthority> authorityList) {
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorityList = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthorityType().name()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorityList);
    }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities () {
            return null;
        }

        @Override
        public String getPassword () {
            return null;
        }

        @Override
        public String getUsername () {
            return null;
        }

        @Override
        public boolean isAccountNonExpired () {
            return false;
        }

        @Override
        public boolean isAccountNonLocked () {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired () {
            return false;
        }

        @Override
        public boolean isEnabled () {
            return false;
        }
    }

