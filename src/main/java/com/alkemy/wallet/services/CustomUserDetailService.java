package com.alkemy.wallet.services;

import com.alkemy.wallet.repository.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Servicio personalizado para cargar detalles de usuario para autenticación
 */
@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailService(UserRepository userRepo) {
        this.userRepo = Objects.requireNonNull(userRepo, "UserRepository no puede ser nulo");
    }

    //todo: loadUserByUsername deberia ser ByEmail, o podriamos crear un userName en User
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede ser nulo o vacío");
        }

        com.alkemy.wallet.models.user.User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciales inválidas"));

        List<GrantedAuthority> authorities = user.getUserRoles() != null ?
                user.getUserRoles().stream()
                        .filter(Objects::nonNull)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getRoleName()))
                        .collect(Collectors.toList()) :
                Collections.emptyList();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}