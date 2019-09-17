package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Inventory;
import anthonyknight.wguinventorytrackingapplication.model.Part;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private final Stage thisStage;

    Inventory inventory;
    ObservableList<Part> allparts;

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
    private TableView PartTable;

    @FXML
    private TableView ProductTable;

    @FXML
    private void PartAddButton(ActionEvent event) {
        AddPartController partController = new AddPartController(this);
        partController.showStage();
    }

    @FXML
    private void PartRemoveButton(ActionEvent event) {
        System.out.println("Go ahead and smash that REMOVE button");
    }

    @FXML
    private void PartModifyButton(ActionEvent event) {
        System.out.println("Go ahead and smash that MODIFY button");
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

        allparts = inventory.getAllParts();

//        for (Part part : allparts) {
//            System.out.println("ID " + part.getID());
//            System.out.println("Name " + part.getName());
//            System.out.println("Price " + part.getPrice());
//            System.out.println("Quantity " + part.getStock());
//
//        }

        PartTable.setItems(allparts);

        PartTable.getVisibleLeafColumn(0).setCellValueFactory(new PropertyValueFactory("ID"));
        PartTable.getVisibleLeafColumn(1).setCellValueFactory(new PropertyValueFactory("Name"));
        PartTable.getVisibleLeafColumn(2).setCellValueFactory(new PropertyValueFactory("Price"));
        PartTable.getVisibleLeafColumn(3).setCellValueFactory(new PropertyValueFactory("Stock"));

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

    public void showStage() {
        thisStage.showAndWait();
    }

}
