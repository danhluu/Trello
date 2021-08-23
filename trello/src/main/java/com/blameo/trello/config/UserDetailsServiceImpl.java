package com.blameo.trello.config;

import com.blameo.trello.model.User;
import com.blameo.trello.repository.RoleRepository;
import com.blameo.trello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        String role = roleRepository.findbyRole(user.getRoleId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();


        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), //
                user.getPassword(), grantList);
        return userDetails;
    }


}
