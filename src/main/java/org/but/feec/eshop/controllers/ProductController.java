package org.but.feec.eshop.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.but.feec.eshop.services.PersonService;
import org.but.feec.eshop.data.PersonRepository;
import org.but.feec.eshop.api.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(PersonsDetailViewController.class);

    @FXML
    private TableColumn<ProductView,String> productBrand;

    @FXML
    private TableColumn<ProductView,String> productModel;

    @FXML
    private TableColumn<ProductView,String> productVersion;

    @FXML
    private TableColumn<ProductView,String> productType;

    @FXML
    private TableColumn<ProductView,String> productColor;

    @FXML
    private TableColumn<ProductView,String> productPrice;

    @FXML
    private TableColumn<ProductView,String> productStock;
    @FXML
    private TableView<ProductView> systemProductTableView;

    private PersonService personService;
    private PersonRepository personRepository;




    @FXML
    public void initializeProduct() {

        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);



        productBrand.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_brand_name"));
        productModel.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_model"));
        productVersion.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_version"));
        productType.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_type_description"));
        productColor.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_color"));
        productPrice.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_price"));
        productStock.setCellValueFactory(new PropertyValueFactory<ProductView, String>("product_in_stock"));

        ObservableList<ProductView> observableProductList = initializeProductData();
        systemProductTableView.setItems(observableProductList);

        systemProductTableView.getSortOrder().add(productBrand);


        logger.info("ProductController initialized");
    }
    private ObservableList<ProductView> initializeProductData() {
        List<ProductView> product = personService.getProductView();
        return FXCollections.observableArrayList(product);
    }
    }
