//import connection.Connection;
//import connection.LocalConnection;
//import database.Database;

import javafx.application.Application;
import javafx.stage.Stage;
import misc.debug.Debug;
import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;
import ui.ViewManager;
import viewmodel.constant.Constant;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        ConnectionProvider connectionProvider = new ConnectionProvider() {
            Connection conn;

            @Nonnull
            @Override
            public Connection get() {
                try {
                    conn = DriverManager.getConnection(Constant.Connection.DBMS_URL, Constant.Connection.DBMS_USER,
                            Constant.Connection.DBMS_PASS);
                    System.out.println("I have the shizz!! connection created");
                    Debug.printThread("Main");
                    return conn;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error in connecting to the database!");
                }
                return null;
            }

            @Override
            public void close() {
                if (conn != null) {
                    try {
                        conn.close();
                        System.out.println("Connection Closed!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Database db = Database.from(Constant.Connection.DBMS_FULL_URL,15);
        ViewManager.getInstance().setDatabase(db);
        //Sample query to test connection with database
        System.out.println("Database working fine");
        db.select("SELECT * FROM users")
                .getAs(String.class, Integer.class)
                .blockingForEach(System.out::println);

    }
}
