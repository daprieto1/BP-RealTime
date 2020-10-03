package com.byteprogramming.repositories;

import com.byteprogramming.models.Rider;
import com.byteprogramming.persistence.RethinkDBConnectionFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rethinkdb.net.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RiderRepository implements IRiderRepository {

    private static Connection connection;
    private static ObjectMapper mapper;
    private static final RethinkDB r = RethinkDB.r;
    public static final String TABLE_NAME = "riders";
    protected final Logger log = LoggerFactory.getLogger(RiderRepository.class);

    @Autowired
    private RethinkDBConnectionFactory connectionFactory;

    public RiderRepository() {
        mapper = new ObjectMapper();
    }

    @Override
    public List<Rider> getRiders() {
        Cursor<Object> cursor = r
                .table(TABLE_NAME)
                .run(connectionFactory.createConnection());
        return cursor.toList().stream().map(o->mapper.convertValue(o, Rider.class)).collect(Collectors.toList());
    }

    @Override
    public Rider insertRider(Rider rider) {
        connection = connectionFactory.createConnection();

        HashMap result = r.table(TABLE_NAME)
                .insert(r.hashMap()
                        .with("name", rider.getName())
                        .with("totalKms", rider.getTotalKms())
                        .with("avgSpeed", rider.getAvgSpeed())
                )
                .run(connection);

        log.info("Insert {}", result);
        return rider;
    }

    @Override
    public Rider updateRider(UUID riderId, Rider rider) {
        connection = connectionFactory.createConnection();
        r.table(TABLE_NAME).get(riderId.toString())
                .update(r.hashMap()
                        .with("name", rider.getName())
                        .with("totalKms", rider.getTotalKms())
                        .with("avgSpeed", rider.getAvgSpeed())
                )
                .run(connection);

        Object obj = r.table(TABLE_NAME).get(riderId.toString()).run(connection);
        return mapper.convertValue(obj, Rider.class);
    }
}
