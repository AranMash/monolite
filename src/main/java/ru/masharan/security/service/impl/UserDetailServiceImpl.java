package ru.masharan.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.masharan.model.entity.Role;
import ru.masharan.model.entity.User;
import ru.masharan.security.service.DaoUserService;
import ru.masharan.model.repo.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@DaoUserService
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByName(username);
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        Set<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), grantedAuthorities);
    }
}
