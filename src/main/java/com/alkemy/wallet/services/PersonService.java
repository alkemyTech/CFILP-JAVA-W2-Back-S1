package com.alkemy.wallet.services;

import com.alkemy.wallet.models.Person;
import java.util.List;


public interface PersonService {
    //TODO: metodos para agregar, editar, borrar y  buscar personas(por id, documento, apellido?)

    public Person savePerson(Person person);//guarda

    public Person editPerson(int id, Person person);//edita

    public void deletePersonById(int id);//elimina

    //todo:  buscar personas por apellido, dni.
    public Person findPersonById(int id);
    public Person findPersonByName(String name);
    public Person findPersonByLastName(String lastName);
    public Person findPersonByPhoneNumber(String phoneNumber);
    List<Person> findPersonsByLocation(String location);
    public Person findPersonByAddress(String address);
    public Person findPersonByIdentityCard(int identityCard);
    public Person findPersonByDateBirth(String dateBirth);
    
}
