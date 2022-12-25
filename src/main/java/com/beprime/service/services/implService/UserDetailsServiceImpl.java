package com.beprime.service.services.implService;

import com.beprime.persistance.entities.User;
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
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=this.userService.findUserByName(username);
        if (user == null) throw new UsernameNotFoundException(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getGroups().forEach(group -> grantedAuthorities.add(new SimpleGrantedAuthority(group.getPrivileged())));
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
    }
}

