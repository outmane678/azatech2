package AzatechStore.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import AzatechStore.model.*;
import AzatechStore.repository.UserRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;  // Pas besoin de @Autowired avec @AllArgsConstructor

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(email); // Utilisation de findByEmail()

        if (user.isPresent()) {
            var userObj = user.get();
            return User.withUsername(userObj.getEmail()) // Utilisation de l'email
                .password(userObj.getPassword())
                .authorities("ROLE_" + userObj.getRole().name()) // ajout du role 
                .build(); // Pas de gestion de rôle ici
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
