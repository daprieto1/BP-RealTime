package com.byteprogramming.repositories;

import com.byteprogramming.models.Rider;

import java.util.List;
import java.util.UUID;

public interface IRiderRepository {

    public List<Rider> getRiders();

    public Rider insertRider(Rider rider);

    public Rider updateRider(UUID riderId, Rider rider);
}
