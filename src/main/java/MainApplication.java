
//import connection.Connection;
//import connection.LocalConnection;
//import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.davidmoten.rx.jdbc.ConnectionProvider;
import org.davidmoten.rx.jdbc.Database;

import javax.annotation.Nonnull;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApplication extends Application {

    private Database db;

    //Download javaFx scene builder 2.0 from oracle website and add path of exe to
    // File/Settings/Language&Framework/JavaFX in intellij
    @Override
    public void start(Stage stage) throws Exception {
        setupDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    public static void main(String ... args ){
        launch(args);

//        out.println("Welcome to the commandline interface of our client application!");
//        out.println("Initializing Database instance");
//        Connection con = new LocalConnection();
//        Database database = con.getDatabaseInstance();
//        out.println("Initialized database instance!");

    }

    private void setupDatabase(){

        ConnectionProvider  connectionProvider = new ConnectionProvider() {
            Connection conn;
            @Nonnull
            @Override
            public Connection get() {
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "root", "saurav@17");
                    return conn;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void close() {
                if(conn!=null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        db = Database.fromBlocking(connectionProvider);
        //Sample query to test connection with database
        db.select("SELECT * FROM test")
                .getAs(String.class,Integer.class)
                .blockingForEach(System.err::println);

    }
}
