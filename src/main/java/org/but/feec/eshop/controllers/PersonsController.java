package org.but.feec.eshop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.but.feec.eshop.Login;
import org.but.feec.eshop.api.PersonBasicView;
import org.but.feec.eshop.api.PersonDetailView;
import org.but.feec.eshop.api.ProductView;
import org.but.feec.eshop.data.PersonRepository;
import org.but.feec.eshop.exceptions.ExceptionHandler;
import org.but.feec.eshop.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PersonsController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    @FXML
    public Button addPersonButton;
    @FXML
    public Button ProductButton;
    @FXML
    public Button refreshButton;
    @FXML
    private TableColumn<PersonBasicView, Long> personsID;
    @FXML
    private TableColumn<PersonBasicView, String> personsCity;
    @FXML
    private TableColumn<PersonBasicView, String> personsEmail;
    @FXML
    private TableColumn<PersonBasicView, String> personsFamilyName;
    @FXML
    private TableColumn<PersonBasicView, String> personsGivenName;
    @FXML
    private TableColumn<PersonBasicView, String> personsPhoneNumber;
    @FXML
    private TableView<PersonBasicView> systemPersonsTableView;


    private PersonService personService;
    private PersonRepository personRepository;

    public PersonsController() {
    }

    @FXML
    private void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);


        personsCity.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("city"));
        personsEmail.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("email"));
        personsFamilyName.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("last_name"));
        personsGivenName.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("first_name"));
        personsPhoneNumber.setCellValueFactory(new PropertyValueFactory<PersonBasicView, String>("phone_number"));


        ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);

        systemPersonsTableView.getSortOrder().add(personsFamilyName);

        initializeTableViewSelection();
        loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit person");
        MenuItem detailedView = new MenuItem("Detailed person view");
        edit.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Login.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("Edit Person");

                PersonsEditController controller = new PersonsEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            PersonBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Login.class.getResource("fxml/PersonsDetailView.fxml"));
                Stage stage = new Stage();

                String personsFamilyName = personView.getFamilyName();
                PersonDetailView personDetailView = personService.getPersonDetailView(personsFamilyName);

                stage.setUserData(personDetailView);
                stage.setTitle("BDS JavaFX Persons Detailed View");

                PersonsDetailViewController controller = new PersonsDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<PersonBasicView> initializePersonsData() {
        List<PersonBasicView> persons = personService.getPersonsBasicView();
        return FXCollections.observableArrayList(persons);
    }


    private void loadIcons() {
        Image vutLogoImage = new Image(Login.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddPersonButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Login.class.getResource("fxml/PersonsCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Person");
            stage.setScene(scene);


            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleProductButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Login.class.getResource("fxml/Products.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1050, 600);
            Stage stage = new Stage();
            stage.setTitle("E-shop Application");
            stage.setScene(scene);


            stage.getIcons().add(new Image(Login.class.getResourceAsStream("logos/eshop-logo.png")));

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }

    }



        public void handleRefreshButton (ActionEvent actionEvent){
            ObservableList<PersonBasicView> observablePersonsList = initializePersonsData();
            systemPersonsTableView.setItems(observablePersonsList);
            systemPersonsTableView.refresh();
            systemPersonsTableView.sort();
        }


    }

