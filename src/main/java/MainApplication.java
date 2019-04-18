//import connection.Connection;
//import connection.LocalConnection;
//import database.Database;

import javafx.application.Application;
import javafx.stage.Stage;
import model.User;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;
import viewmodel.constant.Constant;

public class MainApplication extends Application {

    //Download javaFx scene builder 2.0 from oracle website and add path of exe to
    // File/Settings/Language&Framework/JavaFX in intellij
    @Override
    public void start(Stage stage) throws Exception {

        setupDatabase();
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setMainStage(stage);
        viewManager.setScene(Constant.Path.LOGIN_VIEW);
    }

    public static void main(String... args) {
        launch(args);
    }

    private void setupDatabase() {

        Database db = Database.from(Constant.Connection.DBMS_FULL_URL, 15);
        ViewManager.getInstance().setDatabase(db);
        //Sample query to test connection with database
        System.out.println("Database working fine");
        db.select("SELECT * FROM users")
                .autoMap(User.class)
                .blockingForEach(System.out::println);

    }
}
