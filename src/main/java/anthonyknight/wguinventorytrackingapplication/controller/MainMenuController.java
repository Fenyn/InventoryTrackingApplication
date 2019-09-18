package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Inventory;
import anthonyknight.wguinventorytrackingapplication.model.Part;
import anthonyknight.wguinventorytrackingapplication.model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private final Stage thisStage;

    Inventory inventory;
    ObservableList<Part> allParts;
    ObservableList<Product> allProducts;

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
    private TableView<Part> PartTable;

    @FXML
    private TableView ProductTable;

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
        inventory.addPart(new InHouse("axe", 2.00, 3, 0, 5, 1234));
        inventory.addPart(new InHouse("sword", 5.00, 3, 0, 5, 1234));
        inventory.addPart(new InHouse("hammer", 5.00, 3, 0, 5, 1234));
        inventory.addPart(new InHouse("basketball", 5.00, 3, 0, 5, 1234));

        allParts = inventory.getAllParts();

        PartTable.setItems(allParts);

        PartTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        PartTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        PartTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Stock"));
        PartTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Price"));

//        ProductTable.setItems();
//        
//        ProductTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
//        ProductTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
//        ProductTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Price"));
//        ProductTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Stock"));
    }

    public void AddPart(Part part) {
        inventory.addPart(part);
    }
    
    public void ModifyPart(Part part){
        int index = inventory.GetIndexOfPartByID(part.getID());
        inventory.updatePart(index, part);
    }

    public void showStage() {
        thisStage.showAndWait();
    }

}
