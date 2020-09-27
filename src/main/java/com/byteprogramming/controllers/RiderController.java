package com.byteprogramming.controllers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import com.byteprogramming.models.Rider;
import com.byteprogramming.services.IRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RiderController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private IRiderService riderService;

    @GetMapping("/riders")
    public List<Rider> getRiders() {
        return riderService.getRiders();
    }

    @PutMapping("/riders/{riderId}")
    public Rider updateRider(@PathVariable UUID riderId, @RequestBody Rider rider) {
        return riderService.updateRider(riderId, rider);
    }
}
