package anthonyknight.wguinventorytrackingapplication;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
        
        Inventory inventory = new Inventory();
        inventory.addPart(new InHouse(0, "axe", 2.00, 3, 0, 5, 1234));
        System.out.println("Created new Part");
        
        ObservableList<Part> allparts = inventory.getAllParts();
        for(Part part : allparts){
            System.out.println("ID " + part.getID());
            System.out.println("Name " + part.getName());
            System.out.println("Price " + part.getPrice());
            System.out.println("Quantity " + part.getStock());
            
        }
        
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
