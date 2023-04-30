package com.Homes2Rent.Homes2Rent.security;
import com.Homes2Rent.Homes2Rent.model.User;
import com.Homes2Rent.Homes2Rent.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;



    public class MyUserDetailsService implements UserDetailsService {

        private final UserRepository userRepos;

        public MyUserDetailsService(UserRepository repos) {
            this.userRepos = repos;
        }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<com.Homes2Rent.Homes2Rent.model.User> ou = userRepos.findById(username);
            if (ou.isPresent()) {
                User user = ou.get();
                return new MyUserDetails(user);
            }
            else {
                throw new UsernameNotFoundException(username);
            }
        }
    }


