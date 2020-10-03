package com.byteprogramming.persistence;

import com.byteprogramming.models.Rider;
import com.byteprogramming.repositories.IRiderRepository;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.List;

public class DbInitializer implements InitializingBean {

    private static String DB_NAME;
    private static final String TABLE_NAME = "riders";

    @Autowired
    private Environment env;

    @Autowired
    private RethinkDBConnectionFactory connectionFactory;

    @Autowired
    private IRiderRepository riderRepository;

    private static final RethinkDB r = RethinkDB.r;

    @Override
    public void afterPropertiesSet() {
        DB_NAME = connectionFactory.getDatabase();
        createDb();
    }

    private void createDb() {
        Connection connection = connectionFactory.createConnection();
        List<String> dbList = r.dbList().run(connection);
        if (!dbList.contains(DB_NAME)) {
            r.dbCreate(DB_NAME).run(connection);
        }
        List<String> tables = r.db(DB_NAME).tableList().run(connection);
        if (!tables.contains(TABLE_NAME)) {
            r.db(DB_NAME).tableCreate(TABLE_NAME).run(connection);
            riderRepository.insertRider(new Rider("Nairo", 0, 0));
            riderRepository.insertRider(new Rider("Egan", 0, 0));
            riderRepository.insertRider(new Rider("Rigo", 0, 0));
            riderRepository.insertRider(new Rider("Anacona", 0, 0));
        }

    }
}
