import connection.Connection;
import connection.LocalConnection;
import database.Database;
import io.reactivex.*;

import static java.lang.System.out;

public class MainApplication {
    public static void main(String ... args ){
        out.println("Welcome to the commandline interface of our client application!");
        out.println("Initializing Database instance");
        Connection con = new LocalConnection();
        Database database = con.getDatabaseInstance();
        out.println("Initialized database instance!");

    }
}
