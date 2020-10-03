package com.byteprogramming.persistence;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

public class RethinkDBConnectionFactory {
    private String host;
    private String database;

    public RethinkDBConnectionFactory(String host, String database) {
        this.host = host;
        this.database = database;
    }

    public Connection createConnection() {
        Connection connection = RethinkDB.r.connection().hostname(host).connect();
        connection.use(database);
        return connection;
    }

    public String getDatabase() {
        return database;
    }
}
