package anthonyknight.wguinventorytrackingapplication.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainMenuController implements Initializable {
        
    @FXML
    private void PartAddButton(ActionEvent event) {
        System.out.println("Go ahead and smash that ADD button");
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
    private void ExitButton(ActionEvent event){
        System.out.println("YEET THAT EXIT BUTTON BRUH");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
