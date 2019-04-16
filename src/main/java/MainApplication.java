
import connection.Connection;
import connection.LocalConnection;
//import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class MainApplication extends Application {

    //Download javaFx scene builder 2.0 from oracle website and add path of exe to File/Settings/Language&Framework/JavaFX in intellij
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String ... args ){
        setupDatabase();
        launch(args);

//        out.println("Welcome to the commandline interface of our client application!");
//        out.println("Initializing Database instance");
//        Connection con = new LocalConnection();
//        Database database = con.getDatabaseInstance();
//        out.println("Initialized database instance!");

    }

    private static void setupDatabase() {
        //TODO: Implementation
    }
}
