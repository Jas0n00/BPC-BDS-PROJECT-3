package org.but.feec.eshop.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.but.feec.eshop.api.*;
import org.but.feec.eshop.data.PersonRepository;

import java.util.List;


public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonDetailView getPersonDetailView(String LastName) {
        return personRepository.findPersonDetailedView(LastName);
    }

    public List<PersonBasicView> getPersonsBasicView() {
        return personRepository.getPersonsBasicView();
    }

    public List<ProductView> getProductView() {
        return personRepository.getProductView();
    }

    public void createPerson(PersonCreateView personCreateView) {

        char[] originalPassword = personCreateView.getPassword();
        char[] hashedPassword = hashPassword(originalPassword);
        personCreateView.setPassword(hashedPassword);

        personRepository.createPerson(personCreateView);
    }

    public void injectPerson(PersonCreateView personCreateView) {
        personRepository.injectPerson(personCreateView);
    }

    public void editPerson(PersonEditView personEditView) {

        char[] originalPassword = personEditView.getPassword();
        char[] hashedPassword = hashPassword(originalPassword);
        personEditView.setPassword(hashedPassword);

        personRepository.editPerson(personEditView);
    }

    private char[] hashPassword(char[] password) {
        return BCrypt.withDefaults().hashToChar(12, password);
    }

}