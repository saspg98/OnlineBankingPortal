//import connection.Connection;
//import connection.LocalConnection;
//import database.Database;

import javafx.application.Application;
import javafx.stage.Stage;
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
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "saurav@17");
                    return conn;
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error");
                }
                return null;
            }

            @Override
            public void close() {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        Database db = Database.fromBlocking(connectionProvider);
        ViewManager.getInstance().setDatabase(db);
        //Sample query to test connection with database
        System.out.println("Database working fine");
        db.select("SELECT * FROM test")
                .getAs(String.class, Integer.class)
                .blockingForEach(System.out::println);

    }
}
