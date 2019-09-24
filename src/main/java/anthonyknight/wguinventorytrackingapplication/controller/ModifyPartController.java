package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Outsourced;
import anthonyknight.wguinventorytrackingapplication.model.Part;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ModifyPartController {

    private final MainMenuController mainController;
    private Stage thisStage;
    private Part activePart;

    private State state;

    @FXML
    private TextField IDField;

    @FXML
    private TextField NamePrompt;

    @FXML
    private TextField StockPrompt;

    @FXML
    private TextField PricePrompt;

    @FXML
    private TextField MaxPrompt;

    @FXML
    private TextField MinPrompt;

    @FXML
    private Text VariableText;

    @FXML
    private TextField VariablePrompt;

    @FXML
    private ToggleGroup PartToggle;

    @FXML
    private RadioButton OutsourcedToggleButton;

    @FXML
    void CloseButtonAction(ActionEvent event) {
        thisStage.close();
    }

    @FXML
    void LoadInHouse(ActionEvent event) {
        state = State.INHOUSE;
        VariableText.setText("Machine ID");
        VariablePrompt.setPromptText("Mach ID");
    }

    @FXML
    void LoadOutsourced(ActionEvent event) {
        state = State.OUTSOURCED;
        VariableText.setText("Company Name");
        VariablePrompt.setPromptText("Company Name");
    }

    @FXML
    void ModifyPartButtonAction(ActionEvent event) {
        if (validateFunction()) {

            String name = NamePrompt.getText();
            double price = Double.parseDouble(PricePrompt.getText());
            int stock = Integer.parseInt(StockPrompt.getText());
            int min = Integer.parseInt(MinPrompt.getText());
            int max = Integer.parseInt(MaxPrompt.getText());
            String variable = VariablePrompt.getText();

            Part newPart;
            if (state == State.INHOUSE) {
                newPart = new InHouse(name, price, stock, min, max, Integer.valueOf(variable));
                newPart.setID(activePart.getID());
            } else {
                newPart = new Outsourced(name, price, stock, min, max, variable);
                newPart.setID(activePart.getID());
            }

            mainController.ModifyPart(newPart);

            thisStage.close();
        }

    }

    public ModifyPartController(MainMenuController mainController, Part part) {
        this.mainController = mainController;
        this.activePart = part;

        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifyPart.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/MainMenuStyle.css");

            thisStage.setTitle("Modify Part");
            thisStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (InHouse.class.isInstance(activePart)) {
            state = State.INHOUSE;
        } else {
            state = State.OUTSOURCED;
            PartToggle.selectToggle(OutsourcedToggleButton);
        }

        PopulateWindow();

    }

    public void showStage() {
        thisStage.showAndWait();
    }

    private void PopulateWindow() {
        String ID = String.valueOf(activePart.getID());
        String Name = String.valueOf(activePart.getName());
        String Price = String.valueOf(activePart.getPrice());
        String Stock = String.valueOf(activePart.getStock());
        String Min = String.valueOf(activePart.getMin());
        String Max = String.valueOf(activePart.getMax());
        String Variable = String.valueOf(activePart.getVariable());

        IDField.setText(ID);
        NamePrompt.setText(Name);
        PricePrompt.setText(Price);
        StockPrompt.setText(Stock);
        MinPrompt.setText(Min);
        MaxPrompt.setText(Max);
        VariablePrompt.setText(Variable);
        
        if (state == State.INHOUSE){
            LoadInHouse(new ActionEvent());
        } else {
            LoadOutsourced(new ActionEvent());
        }

    }

    private boolean validateFunction() {
        if (NamePrompt.getText().isEmpty()
                || PricePrompt.getText().isEmpty()
                || StockPrompt.getText().isEmpty()
                || MinPrompt.getText().isEmpty()
                || MaxPrompt.getText().isEmpty()
                || VariablePrompt.getText().isEmpty()) {
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

        //check that machine ID is a valid input, if it is expected
        if (state == State.INHOUSE) {
            try {
                Integer.parseInt(VariablePrompt.getText());
            } catch (NumberFormatException e) {
                alertUser("Machine ID must be an integer");
                return false;
            }
        }

        return true;
    }

    private void clearInputs() {
        NamePrompt.clear();
        PricePrompt.clear();
        StockPrompt.clear();
        MinPrompt.clear();
        MaxPrompt.clear();
        VariablePrompt.clear();

    }

    private void alertUser(String input) {
        Alert alert = new Alert(Alert.AlertType.ERROR, input, ButtonType.OK);
        alert.showAndWait();
    }

}
