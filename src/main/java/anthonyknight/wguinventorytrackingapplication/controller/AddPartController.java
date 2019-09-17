package anthonyknight.wguinventorytrackingapplication.controller;

import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Outsourced;
import anthonyknight.wguinventorytrackingapplication.model.Part;
import anthonyknight.wguinventorytrackingapplication.view.MainMenu;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

enum State {
    INHOUSE, OUTSOURCED
}

public class AddPartController implements Initializable {

    private final MainMenuController mainController;
    private Stage thisStage;

    private State state;

    @FXML
    TextField NamePrompt;

    @FXML
    TextField StockPrompt;

    @FXML
    TextField PricePrompt;

    @FXML
    TextField MaxPrompt;

    @FXML
    TextField MinPrompt;

    @FXML
    TextField VariablePrompt;

    @FXML
    Text VariableText;

    public AddPartController(MainMenuController mainController) {
        this.mainController = mainController;

        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddPartInHouse.fxml"));
            loader.setController(this);
            Parent root = loader.load();

            state = State.INHOUSE;

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/MainMenuStyle.css");

            thisStage.setTitle("Inventory Tracking Application");
            thisStage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddPartButtonAction(ActionEvent event) {
        if (validateFunction()) {

            String name = NamePrompt.getText();
            double price = Double.parseDouble(PricePrompt.getText());
            int stock = Integer.parseInt(StockPrompt.getText());
            int min = Integer.parseInt(MinPrompt.getText());
            int max = Integer.parseInt(MaxPrompt.getText());

            if (null == state) {
                clearInputs();
            } else {
                switch (state) {
                    case INHOUSE: {
                        int machID = Integer.parseInt(VariablePrompt.getText());
                        Part part;
                        part = new InHouse(name, price, stock, min, max, machID);
                        mainController.AddPart(part);
                        clearInputs();
                        break;
                    }
                    case OUTSOURCED: {
                        String companyName = VariablePrompt.getText();
                        Part part;
                        part = new Outsourced(name, price, stock, min, max, companyName);
                        mainController.AddPart(part);
                        clearInputs();
                        break;
                    }
                    default:
                        clearInputs();
                        break;
                }
            }
            thisStage.close();
        }

    }

    @FXML
    private void CloseButtonAction(ActionEvent event) {
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    private boolean validateFunction() {
        if (NamePrompt.getText().isEmpty()
                || PricePrompt.getText().isEmpty()
                || StockPrompt.getText().isEmpty()
                || MinPrompt.getText().isEmpty()
                || MaxPrompt.getText().isEmpty()
                || VariablePrompt.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR, "All fields must contain values", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //ensure Price field contains valid input
        try {
            Double.parseDouble(PricePrompt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Price contains invalid input", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //ensure stock field contains valid input
        try {
            Integer.parseInt(StockPrompt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Stock must be an integer", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //ensure min field contains valid input
        try {
            Integer.parseInt(MinPrompt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Min must be an integer", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //ensure max field contains valid input
        try {
            Integer.parseInt(MaxPrompt.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR, "Max must be an integer", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //check that min is not greater than max value
        int min = Integer.parseInt(MinPrompt.getText());
        int max = Integer.parseInt(MaxPrompt.getText());

        if (max < min) {
            Alert alert = new Alert(AlertType.ERROR, "Max value cannot be less than min value", ButtonType.OK);
            alert.showAndWait();

            return false;
        }

        //check that machine ID is a valid input, if it is expected
        if (state == State.INHOUSE) {
            try {
                Integer.parseInt(VariablePrompt.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR, "Machine ID must be an integer", ButtonType.OK);
                alert.showAndWait();

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

}
