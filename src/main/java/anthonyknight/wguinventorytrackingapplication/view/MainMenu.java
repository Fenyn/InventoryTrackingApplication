package anthonyknight.wguinventorytrackingapplication.view;

import anthonyknight.wguinventorytrackingapplication.controller.MainMenuController;
import anthonyknight.wguinventorytrackingapplication.model.Part;
import anthonyknight.wguinventorytrackingapplication.model.InHouse;
import anthonyknight.wguinventorytrackingapplication.model.Inventory;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class MainMenu extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuController mainController = new MainMenuController();
        mainController.showStage();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
