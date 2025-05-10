package com.alkemy.wallet.repository;

import com.alkemy.wallet.models.Person;
import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional<Person> findByName(String name);
    Optional<Person> findByLastName(String lastName);
    Optional<Person> findByPhoneNumber(String phoneNumber);
    List<Person> findByLocationIgnoreCase(String location);
    Optional<Person> findByAddress(String address);
    Optional<Person> findByIdentityCard(int identityCard);
    Optional<Person> findByDateBirth(String dateBirth);
}
