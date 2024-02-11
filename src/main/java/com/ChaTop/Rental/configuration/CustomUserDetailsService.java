package com.ChaTop.Rental.configuration;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.ChaTop.Rental.entity.User;
import com.ChaTop.Rental.repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UsersRepository usersRepository;

    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = usersRepository.findByEmail(email);

        if (optionalUser.isPresent()) {

            User user = optionalUser.get();

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    new ArrayList<GrantedAuthority>());

        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

}
