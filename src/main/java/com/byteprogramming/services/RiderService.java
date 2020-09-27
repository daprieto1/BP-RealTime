package com.byteprogramming.services;

import com.byteprogramming.models.Rider;
import com.byteprogramming.repositories.IRiderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RiderService implements IRiderService {

    @Autowired
    IRiderRepository repository;

    @Override
    public List<Rider> getRiders() {
        return repository.getRiders();
    }

    @Override
    public Rider updateRider(UUID riderId, Rider rider) {
        return repository.updateRider(riderId, rider);
    }
}
