package org.but.feec.eshop.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.eshop.api.PersonBasicView;
import org.but.feec.eshop.data.PersonRepository;

import java.util.List;


public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<PersonBasicView> getPersonsBasicView() {
        return personRepository.getPersonsBasicView();
    }

    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }

}