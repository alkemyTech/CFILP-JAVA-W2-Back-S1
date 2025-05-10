package com.alkemy.wallet.services.impl;

import com.alkemy.wallet.models.Account;
import com.alkemy.wallet.models.Person;
import com.alkemy.wallet.models.User;
import com.alkemy.wallet.repository.PersonRepository;
import com.alkemy.wallet.services.PersonService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service

public class PersonServiceImpl implements com.alkemy.wallet.services.PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
    public Person editPerson(int id, Person newPersonData) {
        return personRepository.findById(id).map(person -> {
            if (newPersonData.getName() != null) {
                person.setName(newPersonData.getName());
            }
            if (newPersonData.getLastName() != null) {
                person.setLastName(newPersonData.getLastName());
            }
            if (newPersonData.getAddress() != null) {
                person.setAddress(newPersonData.getAddress());
            }
            if (newPersonData.getLocation() != null) {
                person.setLocation(newPersonData.getLocation());
            }
            if (newPersonData.getPhoneNumber() != null) {
                person.setPhoneNumber(newPersonData.getPhoneNumber());
            }
            if (newPersonData.getDateBirth() != null) {
                person.setDateBirth(newPersonData.getDateBirth());
            }
            return personRepository.save(person);
        }).orElseThrow(() -> new RuntimeException("Persona no encontrada con id " + id));
    }
    //eliminar persona
    
    public void  deletePersonById(int id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cuenta no encontrada con id " + id);
        }
        personRepository.deleteById(id);
    }
    //buscadores
    public Person findPersonById(int id) {
        return personRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con id " + id));
    }

    public Person findPersonByName(String name) {
        return personRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con nombre " + name));
    }
    public Person findPersonByLastName(String lastName) {
        return personRepository.findByLastName(lastName)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con apellido: " + lastName));
    }

    public Person findPersonByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con teléfono: " + phoneNumber));
    }
    public List<Person> findPersonsByLocation(String location) {
    return personRepository.findByLocationIgnoreCase(location);
    }
    public Person findPersonByAddress(String address) {
        return personRepository.findByAddress(address)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con dirección: " + address));
    }

    public Person findPersonByIdentityCard(int identityCard) {
        return personRepository.findByIdentityCard(identityCard)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con número de identificación: " + identityCard));
    }

    public Person findPersonByDateBirth(String dateBirth) {
        return personRepository.findByDateBirth(dateBirth)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada con fecha de nacimiento: " + dateBirth));
    }

}
