package com.boommotors.btp.security;

import com.boommotors.btp.user.dao.UserDAO;
import com.boommotors.btp.dto.User;
import com.boommotors.btp.exception.ResourceNotFoundException;
import com.boommotors.btp.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author rjanumpally
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    UserDAO userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String mobNo)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user;
        if (mobNo.contains("@")) {
            user = userRepository.fetchUserByEmailForConsumerApp(mobNo);
        }else  {
            user = userRepository.fetchByEmail(mobNo);
        }
        if (user == null) {
            throw new UsernameNotFoundException("User not found with or email : " + mobNo);
        }

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return UserPrincipal.create(user);
    }
}
