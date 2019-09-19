package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Inventory;
import anthonyknight.wguinventorytrackingapplication.model.Part;
import anthonyknight.wguinventorytrackingapplication.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private final Stage thisStage;

    Inventory inventory;
    ObservableList<Part> allParts;
    ObservableList<Product> allProducts;

    @FXML
    private Button PartSearchButton;

    @FXML
    private TextField PartSearchBar;

    @FXML
    private Button PartAdd;

    @FXML
    private Button PartRemove;

    @FXML
    private Button PartModify;

    @FXML
    private Button ProductSearchButton;

    @FXML
    private TextField ProductSearchBar;

    @FXML
    private Button ProductAdd;

    @FXML
    private Button ProductRemove;

    @FXML
    private Button ProductModify;

    @FXML
    private Button Exit;

    @FXML
    private TableView<Part> PartTable;

    @FXML
    private TableView<Product> ProductTable;

    public MainMenuController() {
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainMenu.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/MainMenuStyle.css");

            thisStage.setTitle("Inventory Tracking Application");
            thisStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void PartAddButton(ActionEvent event) {
        AddPartController addPartController = new AddPartController(this);
        addPartController.showStage();
    }

    @FXML
    private void PartRemoveButton(ActionEvent event) {
        Part part = PartTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + part.getName() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            inventory.deletePart(part);
        }
        RefreshTables();

    }

    @FXML
    private void PartModifyButton(ActionEvent event) {
        Part part = PartTable.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(AlertType.ERROR, "You must select an item to modify from the table", ButtonType.OK);
            alert.showAndWait();
        } else {
            ModifyPartController modifyPartController = new ModifyPartController(this, part);
            modifyPartController.showStage();
        }
    }

    @FXML
    private void ExitButton(ActionEvent event) {
        thisStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inventory = new Inventory();
        allParts = inventory.getAllParts();
        allProducts = inventory.getAllProducts();

        PartTable.setItems(allParts);
        ProductTable.setItems(allProducts);

        PartTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        PartTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        PartTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Stock"));
        PartTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Price"));

        ProductTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        ProductTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        ProductTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Stock"));
        ProductTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Price"));

    }

    public void AddPart(Part part) {
        inventory.addPart(part);
        RefreshTables();
    }

    public void ModifyPart(Part part) {
        int index = inventory.GetIndexOfPartByID(part.getID());
        inventory.updatePart(index, part);
        RefreshTables();
    }

    public void AddProduct(Product prod) {
        inventory.addProduct(prod);
        RefreshTables();
    }

    public void ModifyProduct(Product prod) {
        int index = inventory.GetIndexOfProductByID(prod.getID());
        inventory.updateProduct(index, prod);
        RefreshTables();
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    @FXML
    void PartSearchButtonAction(ActionEvent event) {
        //the following search algorithm is a modified version of the one found
        //here: https://stackoverflow.com/questions/44317837/create-search-textfield-field-to-search-in-a-javafx-tableview
        FilteredList<Part> filteredData = null;
        try {
            filteredData = new FilteredList<>(allParts, p -> true);
        } catch (NullPointerException e) {

        }

        String searchText = PartSearchBar.getText();
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
            sortedData.comparatorProperty().bind(PartTable.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            PartTable.setItems(sortedData);
        }
        //end of modified algorithm
    }

    @FXML
    void ProductAddButton(ActionEvent event) {
        AddProductController addProductController = new AddProductController(this);
        addProductController.showStage();
    }

    @FXML
    void ProductModifyButton(ActionEvent event) {
        Product product = ProductTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(AlertType.ERROR, "You must select an item to modify from the table", ButtonType.OK);
            alert.showAndWait();
        } else {
            ModifyProductController modifyProductController = new ModifyProductController(this, product);
            modifyProductController.showStage();
        }
    }

    @FXML
    void ProductRemoveButton(ActionEvent event) {
        Product product = ProductTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(AlertType.ERROR, "You must select an item to delete from the table", ButtonType.OK);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + product.getName() + "?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                inventory.deleteProduct(product);
            }
        }
        RefreshTables();
    }

    @FXML
    void ProductSearchButtonAction(ActionEvent event) {
        //the following search algorithm is a modified version of the one found
        //here: https://stackoverflow.com/questions/44317837/create-search-textfield-field-to-search-in-a-javafx-tableview
        FilteredList<Product> filteredData = null;
        try {
            filteredData = new FilteredList<>(allProducts, p -> true);
        } catch (NullPointerException e) {

        }

        String searchText = ProductSearchBar.getText();
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
            SortedList<Product> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(ProductTable.comparatorProperty());
            // 5. Add sorted (and filtered) data to the table.
            ProductTable.setItems(sortedData);
        }
        //end of modified algorithm
    }

    public ObservableList<Part> GetAllParts() {
        return inventory.getAllParts();
    }

    private void RefreshTables() {
        allProducts = inventory.getAllProducts();
        allParts = inventory.getAllParts();
        PartTable.setItems(allParts);
        ProductTable.setItems(allProducts);
        PartTable.refresh();
        ProductTable.refresh();
    }

}
