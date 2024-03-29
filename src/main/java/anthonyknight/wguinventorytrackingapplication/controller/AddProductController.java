/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.Part;
import anthonyknight.wguinventorytrackingapplication.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AddProductController implements Initializable {

    private final MainMenuController mainController;
    private Stage thisStage;

    ObservableList<Part> allParts;
    ObservableList<Part> currentParts;

    @FXML
    private TextField NamePrompt;

    @FXML
    private TextField StockPrompt;

    @FXML
    private TextField PricePrompt;

    @FXML
    private TextField SearchField;

    @FXML
    private Button SearchButton;

    @FXML
    private TableView<Part> AllPartsTable;

    @FXML
    private Button AddPartButton;

    @FXML
    private TableView<Part> CurrentPartsTable;

    @FXML
    private Button DeletePartButton;

    @FXML
    private Button SubmitProductButton;

    @FXML
    private Button CancelButton;

    @FXML
    private TextField MaxPrompt;

    @FXML
    private TextField MinPrompt;

    @FXML
    void AddPartAction(ActionEvent event) {
        Part part = AllPartsTable.getSelectionModel().getSelectedItem();
        currentParts.addAll(part);
    }

    @FXML
    void CancelButtonAction(ActionEvent event) {
        thisStage.close();
    }

    @FXML
    void DeletePartAction(ActionEvent event) {
        Part part = CurrentPartsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + part.getName() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        currentParts.remove(part);
    }

    @FXML
    void SearchButtonAction(ActionEvent event) {
        //the following search algorithm is a modified version of the one found
        //here: https://stackoverflow.com/questions/44317837/create-search-textfield-field-to-search-in-a-javafx-tableview
        FilteredList<Part> filteredData = null;
        try {
            filteredData = new FilteredList<>(allParts, p -> true);
        } catch (NullPointerException e) {

        }

        String searchText = SearchField.getText();
        if (filteredData != null) {
            // 2. Set the filter Predicate whenever the filter changes.
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (searchText == null || searchText.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = searchText.toLowerCase();

                if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.

                }
                return false; // Does not match.
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Part> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(AllPartsTable.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            AllPartsTable.setItems(sortedData);
        }
        //end of modified algorithm
    }

    @FXML
    void SubmitButtonAction(ActionEvent event) {
        if (validateFunction()) {
            String name = NamePrompt.getText();
            double price = Double.parseDouble(PricePrompt.getText());
            int stock = Integer.parseInt(StockPrompt.getText());
            int min = Integer.parseInt(MinPrompt.getText());
            int max = Integer.parseInt(MaxPrompt.getText());
            
            Product prod = new Product(name, price, stock, min, max);
            for (Part part : currentParts){
                prod.addAssociatedPart(part);
            }
            mainController.AddProduct(prod);
            clearInputs();
            thisStage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AllPartsTable.setItems(allParts);

        AllPartsTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        AllPartsTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        AllPartsTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Stock"));
        AllPartsTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Price"));

        CurrentPartsTable.setItems(currentParts);
        CurrentPartsTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        CurrentPartsTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        CurrentPartsTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Stock"));
        CurrentPartsTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Price"));
    }

    public AddProductController(MainMenuController mainController) {
        this.mainController = mainController;
        this.allParts = mainController.GetAllParts();

        thisStage = new Stage();
        currentParts = FXCollections.observableArrayList();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddProduct.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/MainMenuStyle.css");

            thisStage.setTitle("Add Product Window");
            thisStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }
    
    //small wrapper to create user error alerts
    private void alertUser(String input) {
        Alert alert = new Alert(Alert.AlertType.ERROR, input, ButtonType.OK);
        alert.showAndWait();
    }

    private boolean validateFunction() {
        if (NamePrompt.getText().isEmpty()
                || PricePrompt.getText().isEmpty()
                || StockPrompt.getText().isEmpty()
                || MinPrompt.getText().isEmpty()
                || MaxPrompt.getText().isEmpty()) {
            alertUser("All fields must contain a value");

            return false;
        }

        //ensure Price field contains valid input
        try {
            Double.parseDouble(PricePrompt.getText());
        } catch (NumberFormatException e) {
            alertUser("Price contains invalid input");
            return false;
        }

        //ensure min field contains valid input
        try {
            Integer.parseInt(MinPrompt.getText());
        } catch (NumberFormatException e) {
            alertUser("Min must be an integer");
            return false;
        }

        //ensure max field contains valid input
        try {
            Integer.parseInt(MaxPrompt.getText());
        } catch (NumberFormatException e) {
            alertUser("Max must be an integer");
            return false;
        }

        //check that min is not greater than max value
        int min = Integer.parseInt(MinPrompt.getText());
        int max = Integer.parseInt(MaxPrompt.getText());

        if (max < min) {
            alertUser("Max value cannot be less than min value");
            return false;
        }

        //ensure stock field contains valid input
        try {
            int stock = Integer.parseInt(StockPrompt.getText());
            if (stock < min || stock > max) {
                alertUser("Stock must be within the min and max bounds");
                return false;
            }
            
        } catch (NumberFormatException e) {
            alertUser("Stock must be an integer");
            return false;
        }

        return true;
    }
    
    //empty all text prompts to reset the form
    private void clearInputs() {
        NamePrompt.clear();
        PricePrompt.clear();
        StockPrompt.clear();
        MinPrompt.clear();
        MaxPrompt.clear();
    }

}
