package com.alkemy.wallet.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.alkemy.wallet.models.Person;
import com.alkemy.wallet.services.PersonService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.RequestParam;


/*todo: implementar las rutas para person Controller*/

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;


    @PostMapping("/person")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        Person savedPerson = personService.savePerson(person); //Si hay algún error de validación, lo manejará el GlobalExceptionHandler
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson); // Si todo va bien devuelve un 201 Created
    }
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person newPersonData) {
        try {
            Person updatedPerson = personService.editPerson(id, newPersonData);
            return ResponseEntity.ok(updatedPerson);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/person/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) {
        try {
            personService.deletePersonById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //buscadores
    @GetMapping("/person/id/{id}")
    public ResponseEntity<Person> findPersonById(@PathVariable int id) {
        try {
            Person person = personService.findPersonById(id);
            if (person != null) {
                return ResponseEntity.ok(person);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/person/name/{name}")
    public  ResponseEntity<Person> findPersonByName(@PathVariable String name) {
        try {
            Person person = personService.findPersonByName(name);
            if (person != null) {
                return ResponseEntity.ok(person);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/person/lastName/{lastName}")
    public ResponseEntity<Person> findPersonByLastName(@PathVariable String lastName) {
        try {
            Person person = personService.findPersonByLastName(lastName);
            return ResponseEntity.ok(person);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/person/phoneNumber/{phoneNumber}")
    public ResponseEntity<Person> findPersonByPhoneNumber(@PathVariable String phoneNumber) {
        try {
            Person person = personService.findPersonByPhoneNumber(phoneNumber);
            return ResponseEntity.ok(person);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/persons/by-location")
    public ResponseEntity<List<Person>> getPersonsByLocation(@RequestParam String location) {
        List<Person> persons = personService.findPersonsByLocation(location);
        return ResponseEntity.ok(persons);
    }



    @GetMapping("/person/address/{address}")
    public ResponseEntity<Person> findPersonByAddress(@PathVariable String address) {
        try {
            Person person = personService.findPersonByAddress(address);
            return ResponseEntity.ok(person);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/person/identityCard/{identityCard}")
    public ResponseEntity<Person> findPersonByIdentityCard(@PathVariable int identityCard) {
        try {
            Person person = personService.findPersonByIdentityCard(identityCard);
            return ResponseEntity.ok(person);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/person/dateBirth/{dateBirth}")
    public ResponseEntity<Person> findPersonByDateBirth(@PathVariable String dateBirth) {
        try {
            Person person = personService.findPersonByDateBirth(dateBirth);
            return ResponseEntity.ok(person);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
