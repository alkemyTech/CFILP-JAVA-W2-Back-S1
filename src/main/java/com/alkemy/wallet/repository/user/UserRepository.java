package com.alkemy.wallet.repository.user;



import org.springframework.data.jpa.repository.JpaRepository;

import com.alkemy.wallet.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUserRoles_Role_RoleName(String role); // 


    List<User> findByUserRoles_Role_Id(int id);

    Optional<User> findByEmail(String email);
}
