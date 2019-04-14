package connection;

import database.Database;

public class LocalConnection implements Connection {
    @Override
    public Database getDatabaseInstance() {
        return null;
    }
}
