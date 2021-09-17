import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("aview/sample.fxml"));
        primaryStage.setTitle("Graham Scan - Get your graphical convex hull");
        primaryStage.setScene(new Scene(root, 800 , 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
