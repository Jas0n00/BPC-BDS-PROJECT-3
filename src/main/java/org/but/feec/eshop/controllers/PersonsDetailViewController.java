package org.but.feec.eshop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.but.feec.eshop.api.PersonDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonsDetailViewController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TextField accountTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField PhoneNumberTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField houseNumberTextField;

    @FXML
    private TextField streetTextField;


    public Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        accountTextField.setEditable(false);
        emailTextField.setEditable(false);
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        PhoneNumberTextField.setEditable(false);
        cityTextField.setEditable(false);
        houseNumberTextField.setEditable(false);
        streetTextField.setEditable(false);

        loadPersonsData();

        logger.info("PersonsDetailViewController initialized");
    }

    private void loadPersonsData() {
        Stage stage = this.stage;
        if (stage.getUserData() instanceof PersonDetailView) {
            PersonDetailView personBasicView = (PersonDetailView) stage.getUserData();
            accountTextField.setText(String.valueOf(personBasicView.getAccount()));
            emailTextField.setText(personBasicView.getEmail());
            firstNameTextField.setText(personBasicView.getGivenName());
            lastNameTextField.setText(personBasicView.getFamilyName());
            PhoneNumberTextField.setText(personBasicView.getPhoneNumber());
            cityTextField.setText(personBasicView.getCity());
            houseNumberTextField.setText(personBasicView.gethouseNumber());
            streetTextField.setText(personBasicView.getStreet());
        }
    }

}
