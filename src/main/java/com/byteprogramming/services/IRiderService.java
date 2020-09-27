package com.byteprogramming.services;

import com.byteprogramming.models.Rider;

import java.util.List;
import java.util.UUID;

public interface IRiderService {

    public List<Rider> getRiders();
    public Rider updateRider(UUID riderId, Rider rider);

}
