package com.byteprogramming.repositories;

import com.byteprogramming.models.Rider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rethinkdb.net.Result;
import org.springframework.stereotype.Component;
import com.rethinkdb.RethinkDB;
import com.rethinkdb.net.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class RiderRepository implements IRiderRepository {

    public static final String TABLE_NAME = "riders";
    private static ObjectMapper mapper;
    private static Connection connection;
    private static final RethinkDB r = RethinkDB.r;

    public RiderRepository() {
        connection = r.connection().hostname("localhost").port(28015).connect();
        mapper = new ObjectMapper();
    }

    @Override
    public List<Rider> getRiders() {
        List<Rider> riders = new ArrayList<>();
        Result<Object> cursor = r.table(TABLE_NAME).run(connection);

        for (Object doc : cursor) {
            System.out.println(doc);
            riders.add(mapper.convertValue(doc, Rider.class));
        }

        return riders;
    }

    @Override
    public Rider updateRider(UUID riderId, Rider rider) {

        r.table(TABLE_NAME).get(riderId.toString())
                .update(r.hashMap()
                        .with("name", rider.getName())
                        .with("totalKms", rider.getTotalKms())
                        .with("avgSpeed", rider.getAvgSpeed())
                )
                .run(connection);
        Result<Object> cursor = r.table(TABLE_NAME).get(riderId.toString()).run(connection);
        Object doc = cursor.next();
        return mapper.convertValue(doc, Rider.class);
    }
}
